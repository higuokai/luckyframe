package com.fantaike.module.testmanagmt.service;

import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.PageEntity;
import com.fantaike.common.entity.Result;
import com.fantaike.module.testmanagmt.entity.ProjectCase;
import com.fantaike.module.testmanagmt.mapper.ProjectCaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProjectCaseService {
    
    private static final Logger logger = LoggerFactory.getLogger(ProjectCaseService.class);
    
    @Resource
    private ProjectCaseMapper projectCaseMapper;
    
    public Result list(ProjectCase model, PageEntity pageEntity) {

        PageHelper.startPage(pageEntity.getPageNo(), pageEntity.getPageSize());
        try {
            List<ProjectCase> list = projectCaseMapper.list(model);
            PageInfo<ProjectCase> pageInfo = new PageInfo<>(list);
            pageEntity.setData(list);
            pageEntity.setTotal(pageInfo.getTotal());
            return new Result(Constant.success, "", pageEntity);
        } catch (Exception e) {
            logger.error("用例查询失败", e);
            return new Result(Constant.fail);
        }
    }
    
    @Transactional(rollbackFor = RuntimeException.class)
    public Result saveOrUpdate(ProjectCase model) {
        Long caseId = model.getCaseId();
        if (caseId == null) {
            // 新增
            model.setCreateTime(new Date());
            try {
                projectCaseMapper.save(model);
            } catch (Exception e) {
                logger.error("新增用例失败", e);
                throw new RuntimeException("新增用例失败");
            }
        } else {
            // 修改
            try {
                projectCaseMapper.update(model);
            } catch (Exception e) {
                logger.error("修改用例失败", e);
                throw new RuntimeException("修改用例失败");
            }
        }
        return new Result();
    }
    
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteByIds(List<Long> caseIds) {
        try {
            projectCaseMapper.deleteByIds(caseIds);
        }catch (Exception e) {
            logger.error("删除用例失败", e);
            throw new RuntimeException("删除用例失败");
        }
        return new Result();
    }
    
}
