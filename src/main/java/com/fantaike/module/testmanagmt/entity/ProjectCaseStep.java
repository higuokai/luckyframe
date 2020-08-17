package com.fantaike.module.testmanagmt.entity;

import java.io.Serializable;

public class ProjectCaseStep implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** id **/
    private Long id;
    /** 用例ID **/
    private Long caseId;
    /** 步骤名称 **/
    private String stepName;
    /** 排序 **/
    private Integer order;
    /** 失败动作 **/
    private String errorAction;
    /** 预期结果 **/
    private String expectedResult;
    /** 用例类型 **/
    private String caseType;
    
    private String caseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getErrorAction() {
        return errorAction;
    }

    public void setErrorAction(String errorAction) {
        this.errorAction = errorAction;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
}
