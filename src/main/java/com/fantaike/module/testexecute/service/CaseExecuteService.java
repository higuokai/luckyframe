package com.fantaike.module.testexecute.service;

import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.Result;
import com.fantaike.framework.execute.ExecutorChain;
import com.fantaike.framework.lang.ParamSection;
import com.fantaike.framework.lang.ReturnProxy;
import com.fantaike.module.common.service.LoadService;
import com.fantaike.module.testmanagmt.entity.ProjectCase;
import com.fantaike.module.testmanagmt.entity.ProjectCaseParam;
import com.fantaike.module.testmanagmt.entity.ProjectCaseStep;
import com.fantaike.module.testmanagmt.mapper.ProjectCaseMapper;
import com.fantaike.module.testmanagmt.mapper.ProjectCaseStepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CaseExecuteService {
    
    private static final Logger logger = LoggerFactory.getLogger(CaseExecuteService.class);

    @Resource
    private ProjectCaseMapper projectCaseMapper;
    
    @Resource
    private LoadService cacheService;
    
    @Resource
    private ProjectCaseStepMapper projectCaseStepMapper;
    
    /**
     * 用例执行
     * @param caseId 用例ID
     * @return Result
     */
    public Result caseExecute(Long caseId) {
        // 从数据库查询用例
        ProjectCase projectCase = projectCaseMapper.selectById(caseId);
        if (projectCase == null) {
            return new Result(Constant.fail, "测试用例不存在", "");
        }
        projectCase.setOrder(null);
        // 从缓存获取对象
        Result result = cacheService.getProjectCase(projectCase);
        if (result == null || !Constant.success.equals(result.getCode())) {
            // 失败
            logger.error("获取用例失败");
            return new Result(Constant.fail, "获取用例失败");
        }
        
        // 查询公共参数,替换参数
        ExecutorChain chain = (ExecutorChain) result.getData();
        
        Map<Object, Object> args = chain.getArgs();
        List<ParamSection> sections = (List<ParamSection>) args.get("param");
        if (sections != null && !sections.isEmpty()) {
            // 查询公共参数
            List<ProjectCaseParam> projectCaseParams = projectCaseMapper.selectParamByProjectId(projectCase.getProjectId());
            Map<String, String> collect = projectCaseParams.stream().collect(Collectors.toMap(ProjectCaseParam::getName, ProjectCaseParam::getValue));
            for (ParamSection section : sections) {
                if (collect.containsKey(section.getKey())) {
                    // 有值
                    section.getReturnHolder().setReturnProxy(new ReturnProxy() {
                        @Override
                        public String getValue(String express) {
                            // 设置默认值
                            return collect.get(section.getKey());
                        }

                        @Override
                        public String toString() {
                            return "[公共参数默认值]";
                        }
                    });
                }
            }
        }
        // 用例执行
        return chain.doExecute();
    }

    /**
     * 单个执行用例
     * @param stepId
     * @return
     */
    public Result executeOne(Long stepId) {
        ProjectCaseStep caseStep = projectCaseStepMapper.selectById(stepId);
        if (caseStep == null) {
            return new Result(Constant.fail, "测试用例步骤不存在", "");
        }
        // 从数据库查询用例
        ProjectCase projectCase = projectCaseMapper.selectById(caseStep.getCaseId());
        if (projectCase == null) {
            return new Result(Constant.fail, "测试用例不存在", "");
        }
        projectCase.setOrder(caseStep.getOrder());
        // 从缓存获取对象
        Result result = cacheService.getProjectCase(projectCase);
        if (result == null || !Constant.success.equals(result.getCode())) {
            // 失败
            logger.error("获取用例失败");
            return new Result(Constant.fail, "获取用例失败");
        }

        // 查询公共参数,替换参数
        ExecutorChain chain = (ExecutorChain) result.getData();

        Map<Object, Object> args = chain.getArgs();
        List<ParamSection> sections = (List<ParamSection>) args.get("param");
        if (sections != null && !sections.isEmpty()) {
            // 查询公共参数
            List<ProjectCaseParam> projectCaseParams = projectCaseMapper.selectParamByProjectId(projectCase.getProjectId());
            Map<String, String> collect = projectCaseParams.stream().collect(Collectors.toMap(ProjectCaseParam::getName, ProjectCaseParam::getValue));
            for (ParamSection section : sections) {
                if (collect.containsKey(section.getKey())) {
                    // 有值
                    section.getReturnHolder().setReturnProxy(new ReturnProxy() {
                        @Override
                        public String getValue(String express) {
                            // 设置默认值
                            return collect.get(section.getKey());
                        }

                        @Override
                        public String toString() {
                            return "[公共参数默认值]";
                        }
                    });
                }
            }
        }
        // 用例执行
        return chain.doExecute();
    }
    
}
