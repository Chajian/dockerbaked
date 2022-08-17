package com.ibs.dockerbacked;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ibs.dockerbacked.mapper")
public class DockerbackedApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerbackedApplication.class, args);
    }

}
