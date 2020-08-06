package com.fantaike.module.testexecute.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

public class HttpCaseStepEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Excel(name = "测试用例ID")
    private String caseId;
    
    @Excel(name = "模块")
    private String module;
    
    @Excel(name = "接口名称")
    private String apiName;

    @Excel(name = "请求地址")
    private String url;

    @Excel(name = "请求头")
    private String header;

    @Excel(name = "请求体")
    private String body;

    @Excel(name = "接口方法")
    private String method;

    @Excel(name = "参数类型")
    private String contentType;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
