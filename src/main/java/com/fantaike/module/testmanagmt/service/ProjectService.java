package com.fantaike.module.testmanagmt.service;

import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.PageEntity;
import com.fantaike.common.entity.Result;
import com.fantaike.module.testmanagmt.entity.Project;
import com.fantaike.module.testmanagmt.entity.ProjectCase;
import com.fantaike.module.testmanagmt.mapper.ProjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectService {
    
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    
    @Resource
    private ProjectMapper projectMapper;
    
    public Result list(Project model, PageEntity pageEntity) {
        logger.info("查询项目列表");
        PageHelper.startPage(pageEntity.getPageNo(), pageEntity.getPageSize());
        try {
            List<Project> list = projectMapper.list(model);
            PageInfo<Project> pageInfo = new PageInfo<>(list);
            pageEntity.setData(list);
            pageEntity.setTotal(pageInfo.getTotal());
            return new Result(Constant.success, "", pageEntity);
        } catch (Exception e) {
            logger.error("用例查询失败", e);
            return new Result(Constant.fail);
        }
    }
    
}
