package com.fantaike.module.testmanagmt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

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
    
    private String projectName;
    
    /** 用例编号 **/
    private String caseSign;
    
    private String remark;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCaseSign() {
        return caseSign;
    }

    public void setCaseSign(String caseSign) {
        this.caseSign = caseSign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
