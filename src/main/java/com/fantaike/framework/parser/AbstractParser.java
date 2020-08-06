package com.fantaike.framework.parser;

/**
 * 解析
 * @param <T>
 * @param <R>
 */
public abstract class AbstractParser<T, R> implements Parser<T>{
    
    public abstract R parse(T model);
    
}
