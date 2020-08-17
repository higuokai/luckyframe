package com.fantaike.module.testmanagmt.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.ModelEntity;
import com.fantaike.common.entity.PageEntity;
import com.fantaike.common.entity.Result;
import com.fantaike.framework.parser.http.HttpCaseStep;
import com.fantaike.module.testmanagmt.entity.ProjectCaseStep;
import com.fantaike.module.testmanagmt.service.ProjectCaseStepService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/caseStep")
public class ProjectCaseStepController {
    
    @Resource
    private ProjectCaseStepService projectCaseStepService;
    
    @RequestMapping("/list")
    @ResponseBody
    public Result list(HttpCaseStep model, PageEntity pageEntity) {
        return projectCaseStepService.list(model, pageEntity);
    }
    
    @PostMapping("/save")
    public Result add(@RequestBody JSONObject object) {
        try {
            if (object == null) {
                return new Result(Constant.fail, "用例步骤为空");
            }
            return projectCaseStepService.save(object);
        }catch (Exception e) {
            return new Result(Constant.fail, "新增用例步骤失败");
        }
    }

}
