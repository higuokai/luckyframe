package com.fantaike.framework.execute;

import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.Result;
import com.fantaike.framework.lang.HttpResponse;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 暂停动作,并返回结果
 */
public class SuspendAction implements Action, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 后续步骤
     * @param executor  下一执行器
     * @param prevResult 前置结果,如果需要指定该action的动作,内容在这里添加
     * @return Result
     */
    @Override
    public Result doExecute(Executor executor, HttpResponse prevResult) {
        if (prevResult == null) {
            // 前置失败
            return new Result(Constant.fail, "前置任务执行失败");
        }
        
        // HTTP code
        int code = prevResult.getCode();
        // 非200
        if (code != HttpStatus.OK.value()) {
            // 记录日志
            return new Result(code+"", code+"", "HTTP请求执行失败,status="+code);
        }
        
        // 继续执行
        return executor.doExecute();
    }
}
