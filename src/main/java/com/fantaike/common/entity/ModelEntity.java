package com.fantaike.common.entity;

import java.io.Serializable;
import java.util.List;

public class ModelEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<T> caseIds;

    public List<T> getCaseIds() {
        return caseIds;
    }

    public void setCaseIds(List<T> caseIds) {
        this.caseIds = caseIds;
    }
}
