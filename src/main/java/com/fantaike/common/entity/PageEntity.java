package com.fantaike.common.entity;

import java.io.Serializable;

public class PageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int pageNo = 1;
    
    private int pageSize = 10;
    
    private long total;
    
    private Object data;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
