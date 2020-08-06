package com.fantaike;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.fantaike.module", annotationClass = Mapper.class)
@EnableScheduling
public class LuckyFrameApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuckyFrameApplication.class, args);
    }
    
}
