package com.fantaike.common.entity;

import com.fantaike.common.constant.Constant;

import java.io.Serializable;

public class Result implements Serializable {
    private static final long serialVersionUID = -8756499778416947043L;

    private String code;

    private String message;

    private Object data;

    public Result(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(String code) {
        this(code, null, null);
    }

    public Result(String code, String message) {
        this(code, message, null);
    }

    public Result() {
        this(Constant.success, null, null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    public static Result success(){
        return new Result();
    }
    public static Result faild() {
        return new Result(Constant.fail);
    }
}
