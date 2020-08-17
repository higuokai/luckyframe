package com.fantaike.module.testmanagmt.mapper;

import com.fantaike.framework.parser.http.HttpCaseStep;
import com.fantaike.module.testmanagmt.entity.ProjectCase;
import com.fantaike.module.testmanagmt.entity.ProjectCaseStep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectCaseStepMapper {
    
    public List<HttpCaseStep> queryHttpSteps(@Param("model") ProjectCase projectCase);
    
    public List<HttpCaseStep> queryHttpStepList(@Param("model") HttpCaseStep model);
    
    void insert(@Param("model")ProjectCaseStep model);
    
    void insertHttpParam(@Param("model")HttpCaseStep model);
    
    void deleteByIds(@Param("ids") List<Long> ids);
    
    ProjectCaseStep selectById(@Param("id") Long id);
    
    void deleteStepByCaseId(@Param("id") Long id);
    
}
