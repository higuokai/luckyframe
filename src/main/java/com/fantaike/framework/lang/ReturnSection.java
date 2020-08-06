package com.fantaike.framework.lang;

public class ReturnSection extends Section{

    private String express;

    private String name;
    
    @Override
    public String getValue() {
        return null;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
