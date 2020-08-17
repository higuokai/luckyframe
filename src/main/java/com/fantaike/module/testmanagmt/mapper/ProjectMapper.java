package com.fantaike.module.testmanagmt.mapper;

import com.fantaike.module.testmanagmt.entity.Project;
import com.fantaike.module.testmanagmt.entity.ProjectCase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectMapper {
    
    public List<Project> list(@Param("model") Project model);
    
}
