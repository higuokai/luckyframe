package com.fantaike.framework.execute;

import com.fantaike.common.entity.Result;
import com.fantaike.framework.lang.HttpResponse;

public interface Action {
    
    public Result doExecute(Executor executor, HttpResponse prevResult);
    
}
