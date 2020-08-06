package com.fantaike.framework.execute;

import com.fantaike.common.entity.Result;

public abstract class Executor {

    protected Executor next;

    public abstract void init();

    public abstract Result doExecute(Object...param);

    public Executor getNext() {
        return next;
    }

    public abstract void setNext(Executor next);
    
}
