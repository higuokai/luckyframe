package com.fantaike.framework.asserts.http;

import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.Result;
import com.fantaike.common.util.SpringContextUtil;
import com.fantaike.framework.asserts.Assert;
import com.fantaike.framework.lang.HttpResponse;
import com.fantaike.framework.lang.ReturnHolder;
import com.fantaike.framework.lang.ReturnProxy;
import com.fantaike.framework.parser.AbstractBodyParser;
import okhttp3.Headers;
import org.springframework.util.StringUtils;

public class HttpAssert extends Assert<HttpResponse> {
    
    private ReturnHolder returnHolder; 
    
    @Override
    public Result assertValue(HttpResponse response) {
        boolean flag = false;
        if ("header".equals(location.toLowerCase())) {
            flag = assertHeader(response.getHeaders());
        } else if ("body".equals(location.toLowerCase())) {
            flag = assertBody(response.getBody());
        }
        if (!flag) {
            return new Result(Constant.fail, printInfo(location, express, type, "( " + value + " )", "--与预期结果不符"));
        }
        return new Result(Constant.success);
    }
    
    private String location;
    
    private String express;
    
    private String type;
    
    private String value;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private boolean assertHeader(Headers headers){
        if (headers == null) {
            return false;
        }
        
        // 解析express,为请求头
        String headerValue = headers.get(express);
        if (headerValue == null) {
            return false;
        }
        
        // type contains/equals
        if ("contains".equals(type.toLowerCase())) {
            return headerValue.contains(value);
        } else if ("equals".equals(type.toLowerCase())) {
            return headerValue.equals(value);
        }
        
        return false;
    }
    
    private boolean assertBody(String body) {
        String proxyValue = returnHolder.getReturnProxy().getValue(express);
        if (StringUtils.isEmpty(proxyValue)) {
            return false;
        }
        // type contains/equals
        if ("contains".equals(type.toLowerCase())) {
            return proxyValue.contains(value);
        } else if ("equals".equals(type.toLowerCase())) {
            return proxyValue.equals(value);
        }
        return false;
    }

    public ReturnHolder getReturnHolder() {
        return returnHolder;
    }

    public void setReturnHolder(ReturnHolder returnHolder) {
        this.returnHolder = returnHolder;
    }
    
    private String printInfo(String...args) {
        if (args == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(".");
        }
        if (sb.toString().endsWith("."))
            sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
