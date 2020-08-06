package com.fantaike.module.common.service;

import com.fantaike.module.testexecute.service.CaseExecuteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SechdulerService {

    private static final Logger logger = LoggerFactory.getLogger(SechdulerService.class);
    
    @Resource
    private CaseExecuteService caseExecuteService;

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void execute() {
        try {
            caseExecuteService.caseExecute(1L);
        } catch (Exception e) {
            logger.error("定时执行失败", e);
        }
    }
    
}
