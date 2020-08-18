package com.fantaike.framework.parser.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fantaike.common.entity.Result;
import com.fantaike.common.util.SpringContextUtil;
import com.fantaike.framework.asserts.Assert;
import com.fantaike.framework.asserts.http.HttpAssert;
import com.fantaike.framework.asserts.http.HttpAssertParser;
import com.fantaike.framework.execute.SuspendAction;
import com.fantaike.framework.lang.Entry;
import com.fantaike.framework.lang.ParamSection;
import com.fantaike.framework.lang.ReturnSection;
import com.fantaike.framework.lang.Section;
import com.fantaike.framework.parser.AbstractBodyParser;
import com.fantaike.framework.parser.AbstractCaseParser;
import com.fantaike.framework.parser.ParamExpressParser;
import com.fantaike.framework.parser.ReturnExpressParser;
import com.fantaike.module.testmanagmt.entity.ProjectCaseStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpCaseStepParser extends AbstractCaseParser<HttpCaseStep, HttpCaseStepTemp> {
    
    private static final Logger logger = LoggerFactory.getLogger(HttpCaseStepParser.class);
    
    private final ParamExpressParser expressParser = new ParamExpressParser();
    
    private final ReturnExpressParser returnExpressParser = new ReturnExpressParser();
    
    private HttpAssertParser httpAssertParser = new HttpAssertParser();
    
    @Override
    public HttpCaseStepTemp parse(HttpCaseStep model) {
        String stepName = model.getStepName();
        logger.info("开始解析HTTP请求用例步骤[ {} ]", stepName);

        String url = model.getUrl();
        if (StringUtils.isEmpty(url)) {
            logger.warn("[ {} ]用例URL为空", stepName);
            return null;
        }
        
        logger.info("[ {} ]用例原始URL为[ {} ]", stepName, url);
        
        List<ParamSection> sections;
        
        HttpCaseStepTemp temp = new HttpCaseStepTemp();
        temp.setStepName(stepName);
        temp.setContentType(model.getContentType());
        temp.setMethod(model.getMethod());
        
        // 解析url
        Entry<Section, List<ParamSection>> urlEntry = expressParser.parse(url);
        temp.setUrlSection(urlEntry.getKey());
        // 参数节点
        sections = new ArrayList<>(urlEntry.getValue());
        
        // 解析url参数
        Entry<List<Entry<Section, Section>>, List<ParamSection>> urlParamEntry = keyValueParse(model.getUrlParam());
        if (urlParamEntry != null) {
            // url参数集合
            temp.setUrlParamSections(urlParamEntry.getKey());
            sections.addAll(urlParamEntry.getValue());
        }
        
        // 解析请求头
        Entry<List<Entry<Section, Section>>, List<ParamSection>> headerParamEntry = keyValueParse(model.getHeaderParam());
        if (headerParamEntry != null) {
            // url参数集合
            temp.setHeaderSections(headerParamEntry.getKey());
            sections.addAll(headerParamEntry.getValue());
        }
        
        // 解析请求体
        if (!StringUtils.isEmpty(model.getBodyParam())) {
            String contentType = model.getContentType();
            logger.info("[ {} ]用例步骤请求体类型为[ {} ]",stepName, contentType);
            // 获取请求体解析类
            String bodyParserName = contentType.toLowerCase() + "BodyParser";
            try {
                AbstractBodyParser<String, Entry<Section, List<ParamSection>>> bodyParser = SpringContextUtil.getBean(bodyParserName, AbstractBodyParser.class);
                Entry<Section, List<ParamSection>> headerEntry = bodyParser.parse(model.getBodyParam());
                temp.setBodySection(headerEntry.getKey());
                sections.addAll(headerEntry.getValue());
            } catch (Exception e) {
                logger.error("不支持的Body类型["+bodyParserName+"]", e);
                return null;
            }
        }
        
        // 解析失败步骤,1.0只封装停止
        temp.setAction(new SuspendAction());
        
        // 解析断言  data.header.contains  data.body.contains .equals
        String expectedResult = model.getExpectedResult();
        // 获取http断言表达式
        Result expectResult = httpAssertParser.parse(expectedResult);
        if (expectResult != null) {
            List<HttpAssert> assertList = (List<HttpAssert>) expectResult.getData();
            for (HttpAssert httpAssert : assertList) {
                httpAssert.setReturnHolder(temp.getReturnHolder());
            }
            temp.setAssertList(assertList);
        }

        // 返回值
        List<ReturnSection> returnSections = returnExpressParser.parse(model.getReturnParam());
        temp.setReturnSections(returnSections);
        
        // 所有需替换的参数
        temp.setParamSections(sections);
        return temp;
    }

    /**
     * key-value形式参数解析
     * {
     *     "{{$name.data,aa}}":"bb",
     *     "dd":"ee"
     * }
     * @param input
     */
    private Entry<List<Entry<Section, Section>>, List<ParamSection>> keyValueParse(String input) {
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(input);
        } catch (Exception e) {
            logger.error("解析参数{}失败,不是json格式", input);
            return null;
        }
        
        List<Entry<Section, Section>> list = new ArrayList<>();
        List<ParamSection> sections = new ArrayList<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            Entry<Section, List<ParamSection>> keyEntry = expressParser.parse(entry.getKey());
            Entry<Section, List<ParamSection>> valueEntry = expressParser.parse(entry.getValue().toString());
            list.add(new Entry<>(keyEntry.getKey(), valueEntry.getKey()));
            sections.addAll(keyEntry.getValue());
            sections.addAll(valueEntry.getValue());
        }
        return new Entry<>(list, sections);
    }
}
