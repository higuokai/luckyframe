package com.fantaike.module.testexecute.controller;

import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.Result;
import com.fantaike.module.testexecute.service.CaseExecuteService;
import com.fantaike.module.testexecute.service.FileCaseExecuteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;

/**
 * 用例执行web
 */
@RestController
@RequestMapping("/caseExecute")
public class CaseExecuteController {
    
    private static final Logger logger = LoggerFactory.getLogger(CaseExecuteController.class);

    @Resource
    private CaseExecuteService caseExecuteService;
    
    @Resource
    private FileCaseExecuteService fileCaseExecuteService;
    
    /**
     * 用例执行
     * @param caseId 用例ID
     * @return Result执行结果
     */
    @GetMapping("/execute/{caseId}")
    public Result caseExecute(@PathVariable Long caseId) {
        logger.info("用例执行[{}]", caseId);
        return caseExecuteService.caseExecute(caseId);
    }
    
    @PostMapping("/caseExecuteFromFile")
    public Result caseExecuteFromFile(MultipartRequest request) {
        logger.info("从文件执行用例");

        MultipartFile file = request.getFile("file");
        if (file == null) {
            logger.info("文件不存在");
            return new Result(Constant.fail, "文件不存在");
        }
        try {
            return fileCaseExecuteService.execute(file.getInputStream());
        } catch (Exception e) {
            logger.error("获取文件流失败", e);
            return new Result(Constant.fail, "获取文件流失败");
        }
    }
    
    @PostMapping("/caseDataExecuteFromFile")
    public Result caseDataExecuteFromFile(MultipartRequest request) {
        logger.info("数据驱动用例文件执行");
        MultipartFile file = request.getFile("file");
        if (file == null) {
            logger.info("文件不存在");
            return new Result(Constant.fail, "文件不存在");
        }
        try {
            return fileCaseExecuteService.ddExecute(file.getInputStream());
        } catch (Exception e) {
            logger.error("获取文件流失败", e);
            return new Result(Constant.fail, "获取文件流失败");
        }
    }

    /**
     * 单个执行测试用例
     * @param stepId 步骤ID
     * @return string
     */
    @GetMapping("/executeOne/{stepId}")
    public Result executeOne(@PathVariable Long stepId) {
        logger.info("用例执行[{}]", stepId);
        return caseExecuteService.executeOne(stepId);
    }
    
}
