package com.fantaike.framework.parser;

import com.fantaike.framework.lang.ReturnProxy;

public abstract class AbstractReturnParser<T> implements Parser {

    public abstract ReturnProxy getReturnProxy(T model);
    
}
