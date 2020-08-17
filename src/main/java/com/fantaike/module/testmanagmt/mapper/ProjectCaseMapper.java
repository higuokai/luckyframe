package com.fantaike.module.testmanagmt.mapper;

import com.fantaike.module.testmanagmt.entity.ProjectCase;
import com.fantaike.module.testmanagmt.entity.ProjectCaseParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectCaseMapper {
    
    ProjectCase selectById(@Param("id") Long id);

    public List<ProjectCaseParam> selectParamByProjectId(@Param("id") Long id);
    
    public List<ProjectCase> list(@Param("model") ProjectCase model);
    
    void save(@Param("model") ProjectCase model);
    
    void update(@Param("model") ProjectCase model);
    
    void updateStatusByIds(@Param("ids")List<Long> ids, @Param("status") int status);
    
    void deleteByIds(@Param("ids")List<Long> ids);
    
}
