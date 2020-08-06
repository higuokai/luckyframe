package com.fantaike.framework.parser;


/**
 * 用例解析
 * @param <T>
 */
public abstract class AbstractCaseParser<T, R> implements Parser<T>{

    /**
     * 解析
     * @param model 要解析的对象
     * @return R
     */
    public abstract R parse(T model);
    
}
