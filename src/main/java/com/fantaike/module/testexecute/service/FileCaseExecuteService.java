package com.fantaike.module.testexecute.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.Result;
import com.fantaike.framework.execute.ExecutorChain;
import com.fantaike.framework.parser.http.HttpCaseParser;
import com.fantaike.framework.parser.http.HttpCaseStep;
import com.fantaike.framework.parser.http.HttpCaseStepParser;
import com.fantaike.module.testexecute.entity.HttpCaseStepEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileCaseExecuteService {
    
    private static final Logger logger = LoggerFactory.getLogger(FileCaseExecuteService.class);

    @Resource
    private HttpCaseParser httpCaseParser;
    
    /**
     * 执行文件定义的用例步骤
     * @param inputStream 文件流
     * @return Result
     */
    public Result execute(InputStream inputStream) {
        // 文件解析为对象
        ImportParams params = new ImportParams();
        
        try {
            List<HttpCaseStepEntity> stepEntities = ExcelImportUtil.importExcel(inputStream, HttpCaseStepEntity.class, params);
            if (stepEntities != null && !stepEntities.isEmpty()) {
                String caseName = stepEntities.get(0).getModule();

                // 组装成step
                List<HttpCaseStep> steps = new ArrayList<>();
                for (HttpCaseStepEntity stepEntity : stepEntities) {
                    HttpCaseStep step = new HttpCaseStep();
                    step.setBodyParam(stepEntity.getBody());
                    step.setContentType(stepEntity.getContentType());
                    step.setHeaderParam(stepEntity.getHeader());
                    step.setMethod(stepEntity.getMethod());
                    step.setUrl(stepEntity.getUrl());
                    steps.add(step);
                }
                // 
                ExecutorChain chain = httpCaseParser.loadChain(steps, caseName);
                return chain.doExecute();
            }
            return new Result(Constant.fail, "用例为空");
        } catch (Exception e) {
            logger.error("解析文件失败", e);
            return new Result(Constant.fail, "解析文件失败");
        }
    }
    
}
