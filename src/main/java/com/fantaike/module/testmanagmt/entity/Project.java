package com.fantaike.module.testmanagmt.entity;

import java.io.Serializable;

/**
 * 项目
 */
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long projectId;
    
    private String projectName;
    
    private String module;
    
    private String remark;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
