package com.fantaike.framework.execute;

import com.fantaike.common.entity.Result;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;

public class ExecutorChain implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private final Builder builder;

    public ExecutorChain(Builder builder) {
        this.builder = builder;
    }

    public static class Builder implements Serializable {
        private static final long serialVersionUID = 1L;
        private final LinkedList<Executor> executors = new LinkedList<>();
        private Executor header;
        public void add(Executor executor) {
            executors.add(executor);
        }

        public ExecutorChain build() {
            if (executors.isEmpty()) {
                return null;
            } else if (executors.size() == 1) {
                header = executors.get(0);
            } else {
                header = executors.getFirst();
                Executor c = header;
                for(int i=1; i<executors.size(); i++) {
                    c.setNext(executors.get(i));
                    c = c.next;
                }
            }
            return new ExecutorChain(this);
        }

        public Result doExecute(Object...args) {
            if (header != null) {
                return header.doExecute(args);
            }
            return null;
        }

    }

    // 参数,有时候需要设置参数
    private Map<Object, Object> args;

    public Result doExecute(Object...args) {
        return builder.doExecute(args);
    }

    public Map<Object, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<Object, Object> args) {
        this.args = args;
    }
    
}
