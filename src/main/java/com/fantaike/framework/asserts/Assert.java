package com.fantaike.framework.asserts;

import com.fantaike.common.entity.Result;

public abstract class Assert<T> {

    public abstract Result assertValue(T model);
}
