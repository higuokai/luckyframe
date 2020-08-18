package com.fantaike.module.testmanagmt.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.PageEntity;
import com.fantaike.common.entity.Result;
import com.fantaike.common.util.SnowflakeIdWorker;
import com.fantaike.framework.parser.http.HttpCaseStep;
import com.fantaike.module.testmanagmt.entity.ProjectCaseStep;
import com.fantaike.module.testmanagmt.mapper.ProjectCaseStepMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectCaseStepService {
    
    private static final Logger logger = LoggerFactory.getLogger(ProjectCaseService.class);

    SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
    
    @Resource
    private ProjectCaseStepMapper projectCaseStepMapper;
    
    public Result list(HttpCaseStep model, PageEntity pageEntity){
        logger.info("查询测HTTP试用例步骤列表");
        PageHelper.startPage(pageEntity.getPageNo(), pageEntity.getPageSize());
        try {
            List<HttpCaseStep> steps = projectCaseStepMapper.queryHttpStepList(model);
            PageInfo<HttpCaseStep> pageInfo = new PageInfo<>(steps);
            pageEntity.setData(steps);
            pageEntity.setTotal(pageInfo.getTotal());
            return new Result(Constant.success, "", pageEntity);
        } catch (Exception e) {
            logger.error("用例查询失败", e);
            return new Result(Constant.fail);
        }
    }
    
    @Transactional(rollbackFor = RuntimeException.class)
    public Result save(JSONObject object) {
        logger.info("新增用例步骤");

        Long caseId = object.getLong("caseId");
        
        try {
            // 根据caseId删除
            projectCaseStepMapper.deleteStepByCaseId(caseId);
//            List<Long> ids = new ArrayList<>();
//            for (HttpCaseStep httpCaseStep : list) {
//                if (httpCaseStep.getStepId() != null)
//                    ids.add(httpCaseStep.getStepId());
//            }
//            if (!ids.isEmpty()) {
//                projectCaseStepMapper.deleteByIds(ids);
//            }
            JSONArray array = object.getJSONArray("list");
            if (array == null) {
                return Result.success();
            }
            List<HttpCaseStep> list = array.toJavaList(HttpCaseStep.class);
            
            List<ProjectCaseStep> projectCases = new ArrayList<>();
            List<HttpCaseStep> httpCaseSteps = new ArrayList<>();
            int index = 1;
            for (HttpCaseStep step : list) {
                ProjectCaseStep caseStep = new ProjectCaseStep();
                caseStep.setOrder(index);
                index++;
                BeanUtils.copyProperties(step, caseStep);
                caseStep.setId(idWorker.nextId());
                caseStep.setCaseId(caseId);
                projectCases.add(caseStep);
                // 保存集合
                step.setStepId(caseStep.getId());
                httpCaseSteps.add(step);
            }
            // 新增projectCase
            for (ProjectCaseStep projectCase : projectCases) {
                projectCaseStepMapper.insert(projectCase);
            }
            // 新增http参数
            for (HttpCaseStep httpCaseStep : httpCaseSteps) {
                projectCaseStepMapper.insertHttpParam(httpCaseStep);
            }
        } catch (Exception e) {
            logger.error("新增用例步骤失败", e);
            return new Result(Constant.fail, "新增用例步骤失败");
        }
        return Result.success();
    }
    
}
