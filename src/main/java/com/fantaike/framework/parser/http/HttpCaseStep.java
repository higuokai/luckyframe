package com.fantaike.framework.parser.http;

import com.fantaike.module.testmanagmt.entity.ProjectCaseStep;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class HttpCaseStep extends ProjectCaseStep {
    private static final long serialVersionUID = 1L;

    private Long caseId;
    
    private Long id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long stepId;
    
    /** uri **/
    private String url;
    /** uri参数 **/
    private String urlParam;
    /** header参数 **/
    private String headerParam;
    /** body参数 **/
    private String bodyParam;
    /** 请求方法 **/
    private String method;
    /** content-type **/
    private String contentType;
    /** 定义的返回值 **/
    private String returnParam;
    /** 返回值类型 **/
    private String resultType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public String getHeaderParam() {
        return headerParam;
    }

    public void setHeaderParam(String headerParam) {
        this.headerParam = headerParam;
    }

    public String getBodyParam() {
        return bodyParam;
    }

    public void setBodyParam(String bodyParam) {
        this.bodyParam = bodyParam;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getReturnParam() {
        return returnParam;
    }

    public void setReturnParam(String returnParam) {
        this.returnParam = returnParam;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Override
    public Long getCaseId() {
        return caseId;
    }

    @Override
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }
}
