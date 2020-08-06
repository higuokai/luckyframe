package com.fantaike.framework.parser;

import com.fantaike.framework.lang.ReturnProxy;
import okhttp3.RequestBody;

public abstract class AbstractBodyParser<T, R> implements Parser<T> {
    
    public abstract R parse(String model);
    
    public abstract RequestBody getValue(T object);
    
}
