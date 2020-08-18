package com.fantaike.framework.execute;

import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.Result;
import com.fantaike.common.util.OkHttpUtil;
import com.fantaike.common.util.SpringContextUtil;
import com.fantaike.framework.asserts.http.HttpAssert;
import com.fantaike.framework.lang.*;
import com.fantaike.framework.parser.AbstractBodyParser;
import com.fantaike.framework.parser.AbstractReturnParser;
import com.fantaike.framework.parser.http.HttpCaseStepTemp;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HttpExecutor extends Executor implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(HttpExecutor.class);
    
    private HttpCaseStepTemp stepTemp;
    
    @Override
    public void init() {
        
    }

    @Override
    public Result doExecute(Object... param) {
        // 用例执行
        logger.info("开始执行HTTP请求测试用例[ {} ]", stepTemp.getStepName());

        OkHttpUtil okHttpUtil = new OkHttpUtil();
        
        StringBuilder url = new StringBuilder();
        Section urlSection = stepTemp.getUrlSection();
        while(urlSection.hasNext()) {
            urlSection = urlSection.getNext();
            url.append(urlSection.getValue());
        }

        // URL 参数
        List<Entry<Section, Section>> urlParamSections = stepTemp.getUrlParamSections();
        if (urlParamSections != null && !urlParamSections.isEmpty()) {
            List<Entry<String, String>> keyValueEntry = getKeyValueEntry(urlParamSections);
            if (keyValueEntry == null) {
                // 找不到返回值
                return new Result(Constant.fail, "表达式找不到对应值");
            }
            boolean firstFlag = true;
            for (Entry<String, String> entry : keyValueEntry) {
                if (firstFlag) {
                    url.append("?").append(entry.getKey()).append("=").append(entry.getValue());
                } else {
                    url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
        }
        logger.info("替换后URL为{}", url.toString());
        try {
            okHttpUtil.setURL(url.toString());
        }catch (Exception e) {
            logger.error("URL格式错误");
            return new Result(Constant.fail, "URL格式错误", url.toString());
        }
        
        
        // header
        // header参数
        List<Entry<Section, Section>> headerSections = stepTemp.getHeaderSections();
        if (headerSections != null && !headerSections.isEmpty()) {
            List<Entry<String, String>> keyValueEntry = getKeyValueEntry(headerSections);
            if (keyValueEntry == null) {
                // 找不到返回值
                return new Result(Constant.fail, "表达式找不到对应值");
            }
            for (Entry<String, String> entry : keyValueEntry) {
                okHttpUtil.addHeader(entry.getKey(), entry.getValue());
            }
        }
        
        // body, 1.0只有formData和json
        if ("GET".equals(stepTemp.getMethod())) {
            okHttpUtil.setBody(null, "GET");
        } else {
            String contentType = stepTemp.getContentType();
            String parserName = contentType.toLowerCase() + "BodyParser";
            AbstractBodyParser bodyParser = null;
            RequestBody body = null;
            try {
                bodyParser = SpringContextUtil.getBean(parserName, AbstractBodyParser.class);
                body = stepTemp.getBodyValue(bodyParser);
            } catch (Exception e) {
                logger.error("不支持的body解析器:"+parserName, e);
                body = stepTemp.getBodyValue(null);
            }
            okHttpUtil.setBody(body, stepTemp.getMethod());
        }
        
        // 开始执行
        HttpResponse response = null;
        try {
            response = okHttpUtil.execNewCall();
            if (response == null) {
                // 失败
                logger.error("执行Http失败");
                return new Result(Constant.fail, stepTemp.getStepName()+"["+url+"]"+":连接失败");
            } else {
                // 成功,状态码不一定
                int code = response.getCode();
                logger.info("执行完成,status={}", code);
                if (HttpStatus.OK.value() == code) {
                    // 封装返回
                    String mediaType = response.getMediaType();
                    String bodyString = response.getBody();
                    AbstractReturnParser parser = SpringContextUtil.getBean(mediaType + "ReturnParser", AbstractReturnParser.class);
                    ReturnProxy returnProxy = parser.getReturnProxy(bodyString);
                    stepTemp.getReturnHolder().setReturnProxy(returnProxy);
                    
                    // 判断断言
                    List<HttpAssert> assertList = stepTemp.getAssertList();
                    if (assertList != null && !assertList.isEmpty()) {
                        for (HttpAssert httpAssert : assertList) {
                            Result result = httpAssert.assertValue(response);
                            if (!Constant.success.equals(result.getCode())) {
                                logger.error("断言失败,[ {}.{}.{} ]", httpAssert.getLocation(), httpAssert.getExpress(), httpAssert.getType());
                                return new Result(Constant.fail, result.getMessage());
                            }
                        }
                    }
                } else {
                    // 封装失败
                    logger.error("执行Http失败,status={}", code);
                    return new Result(code+"", "["+stepTemp.getStepName()+"]请求失败:"+code, response.getBody());
                }
            }
            
            if (this.next != null) {
                return stepTemp.getAction().doExecute(this.next, response);
            }
        } catch (Exception e) {
            logger.error("执行Http失败", e);
            return new Result(Constant.fail, "执行HTTP请求失败");
        }
        return new Result("0000", "执行成功", response.getBody());
    }

    @Override
    public void setNext(Executor next) {
        this.next = next;
    }

    public HttpCaseStepTemp getStepTemp() {
        return stepTemp;
    }

    public void setStepTemp(HttpCaseStepTemp stepTemp) {
        this.stepTemp = stepTemp;
    }

    private List<Entry<String,String>> getKeyValueEntry(List<Entry<Section, Section>> list) {
        List<Entry<String,String>> entries = new ArrayList<>();
        for (Entry<Section, Section> entry : list) {
            StringBuilder key = new StringBuilder();
            Section keySection = entry.getKey();
            while(keySection.hasNext()) {
                keySection = keySection.getNext();
                String value = keySection.getValue();
                if (value == null) {
                    ParamSection nullSection = ((ParamSection)keySection);
                    logger.error("[ {}.{} ]找不到返回值", nullSection.getKey(), nullSection.getExpress());
                    return null;
                }
                key.append(keySection.getValue());
            }
            StringBuilder value = new StringBuilder();
            Section valueSection = entry.getValue();
            while (valueSection.hasNext()) {
                valueSection = valueSection.getNext();
                value.append(valueSection.getValue());
            }
            // 拼接返回
            Entry<String,String> map = new Entry<>(key.toString(), value.toString());
            entries.add(map);
        }
        return entries;
    }
}
