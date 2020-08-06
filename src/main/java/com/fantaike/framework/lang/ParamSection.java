package com.fantaike.framework.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamSection extends Section{
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(ParamSection.class);
    
    /** 参数key, a=xx -> a **/
    private String key;
    /** 默认值 **/
    private String defaultValue;
    /** 取值表达式 **/
    private String express;
    /** 引用的返回值,代表的是该参数对应的值 **/
    private ReturnHolder returnHolder = new ReturnHolder();
    
    @Override
    public String getValue() {
        if (returnHolder == null || returnHolder.getReturnProxy() == null) {
            logger.info("[ {}.{} ]未取到值,使用默认值[ {} ]", key, express == null ? "":express, defaultValue);
            return defaultValue;
        }
        Object string = returnHolder.getReturnProxy().getValue(express);
        if (string == null) {
            logger.warn("[ {}.{} ]取到值为null,使用默认值[ {} ]", key, express, defaultValue);
            return defaultValue;
        }
        logger.info("[ {}.{} ]取到值,替换为[ {} ]", key, express, string);
        return string.toString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public ReturnHolder getReturnHolder() {
        return returnHolder;
    }

    public void setReturnHolder(ReturnHolder returnHolder) {
        this.returnHolder = returnHolder;
    }
}
