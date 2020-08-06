package com.fantaike.framework.parser.http;

import com.fantaike.common.entity.Result;
import com.fantaike.framework.asserts.http.HttpAssert;
import com.fantaike.framework.execute.Action;
import com.fantaike.framework.lang.*;
import com.fantaike.framework.parser.AbstractBodyParser;
import okhttp3.RequestBody;

import java.io.Serializable;
import java.util.List;

/**
 * 用例步骤临时记录
 */
@SuppressWarnings("unused")
public class HttpCaseStepTemp implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** 用例步骤名称 **/
    private String stepName;
    /** URL **/
    private Section urlSection;
    /** URL参数集合 **/
    private List<Entry<Section, Section>> urlParamSections;
    /** header参数集合 **/
    private List<Entry<Section, Section>> headerSections;
    /** 返回值类型 **/
    private String resultType;
    /** 请求体 **/
    private Section bodySection;
    
    private Action action;
    
    private List<ParamSection> paramSections;
    
    private List<ReturnSection> returnSections;

    private String contentType;
    
    private ReturnHolder returnHolder = new ReturnHolder();
    
    private Object bodyValue;

    private String method;
    
    private List<HttpAssert> assertList;
    
    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Section getUrlSection() {
        return urlSection;
    }

    public void setUrlSection(Section urlSection) {
        this.urlSection = urlSection;
    }

    public List<Entry<Section, Section>> getUrlParamSections() {
        return urlParamSections;
    }

    public void setUrlParamSections(List<Entry<Section, Section>> urlParamSections) {
        this.urlParamSections = urlParamSections;
    }

    public List<Entry<Section, Section>> getHeaderSections() {
        return headerSections;
    }

    public void setHeaderSections(List<Entry<Section, Section>> headerSections) {
        this.headerSections = headerSections;
    }

    public Section getBodySection() {
        return bodySection;
    }

    public void setBodySection(Section bodySection) {
        this.bodySection = bodySection;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public List<ParamSection> getParamSections() {
        return paramSections;
    }

    public void setParamSections(List<ParamSection> paramSections) {
        this.paramSections = paramSections;
    }

    public List<ReturnSection> getReturnSections() {
        return returnSections;
    }

    public void setReturnSections(List<ReturnSection> returnSections) {
        this.returnSections = returnSections;
    }

    public ReturnHolder getReturnHolder() {
        return returnHolder;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public RequestBody getBodyValue(AbstractBodyParser parser) {
        if (parser == null) {
            return null;
        }
        // 从解析器获取对应值
        RequestBody requestBody = parser.getValue(bodySection);
        return requestBody;
    }

    public void setBodyValue(Object bodyValue) {
        this.bodyValue = bodyValue;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<HttpAssert> getAssertList() {
        return assertList;
    }

    public void setAssertList(List<HttpAssert> assertList) {
        this.assertList = assertList;
    }
}
