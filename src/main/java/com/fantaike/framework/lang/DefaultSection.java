package com.fantaike.framework.lang;

/**
 * 默认值,非参数片段
 */
public class DefaultSection extends Section{
    private static final long serialVersionUID = 1L;

    private String value;
    
    @Override
    public String getValue() {
        return value;
    }

    public DefaultSection(String value) {
        this.value = value;
    }
}
