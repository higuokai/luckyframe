package com.fantaike.framework.parser.http;

import com.fantaike.framework.lang.JsonProxy;
import com.fantaike.framework.lang.ReturnProxy;
import com.fantaike.framework.parser.AbstractReturnParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("jsonReturnParser")
public class JsonReturnParser extends AbstractReturnParser<String> {

    private static final Logger logger = LoggerFactory.getLogger(JsonReturnParser.class);
    
    @Override
    public ReturnProxy getReturnProxy(String model) {
        try {
            return new JsonProxy(model);
        } catch (Exception e) {
            logger.error("json格式异常");
        }
        return null;
    }
}
