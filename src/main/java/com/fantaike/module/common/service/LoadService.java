package com.fantaike.module.common.service;

import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.Result;
import com.fantaike.common.util.SpringContextUtil;
import com.fantaike.framework.execute.ExecutorChain;
import com.fantaike.framework.parser.AbstractCaseParser;
import com.fantaike.module.testmanagmt.entity.ProjectCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoadService {
    
    private static final Logger logger = LoggerFactory.getLogger(LoadService.class);

    /**
     * 从缓存获取/创建用例
     * @param projectCase 用例
     * @return 用例
     */
    public Result getProjectCase(ProjectCase projectCase) {
        logger.info("加载测试用例[ {} ]", projectCase.getCaseName());
        
        // 获取用例类型  HTTP/HTTPS...
        String caseType = projectCase.getCaseType();
        // 从上下文获取用例解析类
        String parserName = caseType.toLowerCase() + Constant.PARSER_CASE_SUFFIX;
        AbstractCaseParser<ProjectCase, ExecutorChain> caseParser;
        try {
            caseParser = SpringContextUtil.getBean(parserName, AbstractCaseParser.class);
        } catch (Exception e) {
            logger.error("获取解析对象失败"+parserName, e);
            return new Result(Constant.fail, "不支持的用例类型:"+caseType);
        }

        // http请求执行链
        try {
            ExecutorChain chain = caseParser.parse(projectCase);
            if (chain != null)
                return new Result(Constant.success, projectCase.getLoadVersion() == null ? "0" : projectCase.getLoadVersion().toString(), chain);
            return null;
        } catch (Exception e) {
            logger.error("用例组装失败", e);
            return null;
        }
    }

    /**
     * 根据传入step的order,查询之前的步骤
     * @param projectCase 用例
     * @return 用例
     */
    public Result getCaseStepFromOrder(ProjectCase projectCase) {
        logger.info("加载测试用例[ {} ]", projectCase.getCaseName());

        // 获取用例类型  HTTP/HTTPS...
        String caseType = projectCase.getCaseType();
        // 从上下文获取用例解析类
        String parserName = caseType.toLowerCase() + Constant.PARSER_CASE_SUFFIX;
        AbstractCaseParser<ProjectCase, ExecutorChain> caseParser;
        try {
            caseParser = SpringContextUtil.getBean(parserName, AbstractCaseParser.class);
        } catch (Exception e) {
            logger.error("获取解析对象失败"+parserName, e);
            return new Result(Constant.fail, "不支持的用例类型:"+caseType);
        }

        // http请求执行链
        try {
            ExecutorChain chain = caseParser.parse(projectCase);
            if (chain != null)
                return new Result(Constant.success, projectCase.getLoadVersion() == null ? "0" : projectCase.getLoadVersion().toString(), chain);
            return null;
        } catch (Exception e) {
            logger.error("用例组装失败", e);
            return null;
        }
    }
}
