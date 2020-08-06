package com.fantaike.module.testmanagmt.mapper;

import com.fantaike.framework.parser.http.HttpCaseStep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectCaseStepMapper {
    
    public List<HttpCaseStep> queryHttpSteps(@Param("caseId")Long caseId);
    
}
