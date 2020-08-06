package com.fantaike.framework.parser.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fantaike.framework.lang.Entry;
import com.fantaike.framework.lang.ParamSection;
import com.fantaike.framework.lang.Section;
import com.fantaike.framework.parser.AbstractBodyParser;
import com.fantaike.framework.parser.ParamExpressParser;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 表单形式请求体
 */
@Component("formDataBodyParser")
public class HttpBodyFromDataParser extends AbstractBodyParser<Section , Entry<Section, List<ParamSection>>> {
    
    private static final Logger logger = LoggerFactory.getLogger(HttpBodyFromDataParser.class);

    /**
     * 解析multipart/form-data格式
     * @param model 报文内容
     * @return Result
     *
     * [{
     *     "key":"xxx",
     *     "value":"xxx",
     *     "path":"/home/xxx.txt"  // 文件
     * },
     * {
     *     "key":"xxx",
     *     "value":"xxx"  //参数
     * }]
     */
    @Override
    public Entry<Section, List<ParamSection>> parse(String model) {
        // 传入的是纯文本,直接解析即可
        return new ParamExpressParser().parse(model);
    }

    @Override
    public RequestBody getValue(Section model) {
        StringBuilder sb = new StringBuilder("");
        if (model != null) {
            // 没有
            while(model.hasNext()) {
                model = model.getNext();
                sb.append(model.getValue());
            }
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        try {
            JSONObject jsonObject = JSON.parseObject(sb.toString());
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
        } catch (Exception e) {
            logger.error("添加表单失败", e);
        }
        
        return builder.build();
    }
}
