package com.fantaike.module.testmanagmt.controller;

import com.alibaba.fastjson.JSONArray;
import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.ModelEntity;
import com.fantaike.common.entity.PageEntity;
import com.fantaike.common.entity.Result;
import com.fantaike.module.testmanagmt.entity.ProjectCase;
import com.fantaike.module.testmanagmt.service.ProjectCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/projectCase")
public class ProjectCaseController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProjectCaseController.class);
    
    @Resource
    private ProjectCaseService projectCaseService;
    
    @RequestMapping("/list")
    public Result list(ProjectCase model, PageEntity page) {
        logger.info("测试用例列表查询");
        return projectCaseService.list(model, page);
    }
    
    @PostMapping("/action")
    public Result insertOrUpdate(@RequestBody ProjectCase model) {
        Long caseId = model.getCaseId();
        if (caseId == null) {
            logger.info("新增测试用例:{}", model);
        } else {
            logger.info("修改测试用例:{}", model);
        }
        
        try {
            return projectCaseService.saveOrUpdate(model);
        } catch (Exception e) {
            logger.error("新增/修改测试用例失败");
            return new Result(Constant.fail, "修改测试用例失败");
        }
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody ModelEntity<Long> modelEntity) {
//        if (caseIds == null || caseIds.length == 0) {
//            return new Result();
//        }
        List<Long> caseIds = modelEntity.getCaseIds();
        if (caseIds == null) {
            return Result.success();
        }
//        List<Long> caseIds = array.toJavaList(Long.class);
        try {
//            List<Long> ids = Arrays.asList(caseIds);
            return projectCaseService.deleteByIds(caseIds);
        }catch (Exception e) {
            logger.error("删除失败");
            return new Result(Constant.fail, "删除失败");
        }
    }
    
}
