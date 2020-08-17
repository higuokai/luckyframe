package com.fantaike.module.testmanagmt.controller;

import com.fantaike.common.entity.PageEntity;
import com.fantaike.common.entity.Result;
import com.fantaike.module.testmanagmt.entity.Project;
import com.fantaike.module.testmanagmt.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/project")
public class ProjectController {
    
    private  static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    
    @Resource
    private ProjectService projectService;
    
    @GetMapping("/list")
    public Result list(Project model, PageEntity pageEntity) {
        return projectService.list(model, pageEntity);
    }
    
}
