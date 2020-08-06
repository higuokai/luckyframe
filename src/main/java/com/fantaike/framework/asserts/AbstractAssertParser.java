package com.fantaike.framework.asserts;

import com.fantaike.common.entity.Result;
import com.fantaike.framework.parser.Parser;

public abstract class AbstractAssertParser<T> implements Parser {
    
    public abstract Result parse(T model);
    
}
