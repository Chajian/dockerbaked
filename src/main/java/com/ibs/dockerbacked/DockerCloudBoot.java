package com.ibs.dockerbacked;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.ibs.dockerbacked.mapper"})
public class DockerCloudBoot {
    public static void main(String[] args) {
        SpringApplication.run(DockerCloudBoot.class, args);
    }
}
