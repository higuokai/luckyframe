package com.fantaike.module.testmanagmt.entity;

import java.io.Serializable;

/**
 * 项目用例
 */
@SuppressWarnings("unused")
public class ProjectCase implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** 用例ID **/
    private Long caseId;
    /** 用例名称 **/
    private String caseName;
    /** 所属项目ID **/
    private Long projectId;
    /** 排序 **/
    private Integer order;
    /** 加载版本 **/
    private Integer loadVersion;
    /** 用例类型 **/
    private String caseType;

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public Integer getLoadVersion() {
        return loadVersion;
    }

    public void setLoadVersion(Integer loadVersion) {
        this.loadVersion = loadVersion;
    }
    
}
