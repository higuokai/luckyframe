package com.fantaike.framework.lang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonProxy implements ReturnProxy{

    private static final Logger logger = LoggerFactory.getLogger(JsonProxy.class);

    // 解析后的json对象
    private Object jsonObject;
    
    // 返回的参数
    private String returnBody;
    
    @Override
    public String getValue(String express) {
        if (express == null) {
            // 返回整个
            return jsonObject.toString();
        }
        String[] split = express.split("\\.");
        Object object = jsonObject;
        for (String s : split) {
            try {
                // 有就代表还有值,只需要继续替换即可
                object = ((JSONObject) object).get(s);
            } catch (Exception e) {
                logger.error("取值失败,请查看表达式是否正确,result={}, express={}", jsonObject, express);
            }
        }
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    public Object getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(Object jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getReturnBody() {
        return returnBody;
    }

    public void setReturnBody(String returnBody) {
        this.returnBody = returnBody;
    }

    public JsonProxy(String returnBody) {
        this.returnBody = returnBody;
        try {
            jsonObject = JSON.parse(returnBody);
        } catch (Exception e) {
            logger.error("返回值不是json格式", e);
        }
    }
}
