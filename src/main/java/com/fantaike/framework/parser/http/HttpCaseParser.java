package com.fantaike.framework.parser.http;

import com.fantaike.framework.execute.ExecutorChain;
import com.fantaike.framework.execute.HttpExecutor;
import com.fantaike.framework.lang.LIFOMap;
import com.fantaike.framework.lang.ParamSection;
import com.fantaike.framework.lang.ReturnHolder;
import com.fantaike.framework.lang.ReturnSection;
import com.fantaike.framework.parser.AbstractCaseParser;
import com.fantaike.module.testmanagmt.entity.ProjectCase;
import com.fantaike.module.testmanagmt.mapper.ProjectCaseStepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * HTTP用例解析
 */
@Component("httpCaseParser")
public class HttpCaseParser extends AbstractCaseParser<ProjectCase, ExecutorChain> {
    
    private static final Logger logger = LoggerFactory.getLogger(HttpCaseParser.class);

    @Resource
    private ProjectCaseStepMapper projectCaseStepMapper;
    
    private final HttpCaseStepParser stepParser = new HttpCaseStepParser();
    
    @Override
    public ExecutorChain parse(ProjectCase model) {
        
        // 查询下属HTTP用例步骤
        List<HttpCaseStep> httpCaseSteps = projectCaseStepMapper.queryHttpSteps(model.getCaseId());
        if (httpCaseSteps == null || httpCaseSteps.isEmpty()) {
            logger.warn("[ {} ]用例步骤为空", model.getCaseName());
            return null;
        }

        logger.info("开始初始化[ {} ]请求用例步骤参数......", model.getCaseName());
        
        return loadChain(httpCaseSteps, model.getCaseName());
    }
    
    public ExecutorChain loadChain(List<HttpCaseStep> httpCaseSteps, String caseName) {
        List<HttpCaseStepTemp> lists = new LinkedList<>();
        for (HttpCaseStep httpCaseStep : httpCaseSteps) {
            // 循环解析,并取得结果
            HttpCaseStepTemp stepTemp = stepParser.parse(httpCaseStep);
            if (stepTemp == null) {
                // 解析失败
                logger.error("用例解析失败[ {} ]", caseName);
                return null;
            }
            lists.add(stepTemp);
        }

        ExecutorChain.Builder builder = new ExecutorChain.Builder();

        logger.info("[ {} ]测试用例开始替换参数...", caseName);
        LIFOMap<String , ReturnHolder> returnMap = new LIFOMap<>();
        List<ParamSection> sections = new ArrayList<>();
        for (HttpCaseStepTemp stepTemp : lists) {
            logger.info("=====用例步骤[ {} ]开始替换参数=====", stepTemp.getStepName());

            List<ParamSection> paramSections = stepTemp.getParamSections();
            for (ParamSection paramSection : paramSections) {
                if (returnMap.containsKey(paramSection.getKey())) {
                    logger.info("用例步骤[ {} ]替换参数[ {} ]", stepTemp.getStepName(), paramSection.getKey());
                    paramSection.setReturnHolder(returnMap.get(paramSection.getKey()));
                } else {
                    // 没有找到参数
                    sections.add(paramSection);
                }
            }
            logger.info("=====用例步骤[ {} ]开始设置返回值=====", stepTemp.getStepName());

            List<ReturnSection> returnSections = stepTemp.getReturnSections();
            if (returnSections != null && !returnSections.isEmpty()) {
                for (ReturnSection returnSection : returnSections) {
                    logger.info("用例步骤[ {} ]返回值[ {} ]添加", stepTemp.getStepName(), returnSection.getName());
                    returnMap.put(returnSection.getName(), stepTemp.getReturnHolder());
                }
            }

            // 封装
            HttpExecutor executor = new HttpExecutor();
            executor.setStepTemp(stepTemp);
            builder.add(executor);
        }
        logger.info("HTTP请求用例[ {} ]解析完成", caseName);
        ExecutorChain chain = builder.build();
        Map params = new HashMap<>(3);
        params.put("param", sections);
        chain.setArgs(params);
        return chain;
    }
    
}
