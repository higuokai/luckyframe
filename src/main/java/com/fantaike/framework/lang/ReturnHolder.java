package com.fantaike.framework.lang;

import java.io.Serializable;

/**
 * 返回值持有类
 */
public class ReturnHolder implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ReturnProxy returnProxy;

    public ReturnProxy getReturnProxy() {
        return returnProxy;
    }

    public void setReturnProxy(ReturnProxy returnProxy) {
        this.returnProxy = returnProxy;
    }

    public ReturnHolder(ReturnProxy returnProxy) {
        this.returnProxy = returnProxy;
    }

    public ReturnHolder() {
    }
}
