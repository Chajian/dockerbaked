package com.ibs.dockerbacked;

import com.github.dockerjava.api.DockerClient;
import com.ibs.dockerbacked.config.DockerCloudConfig;
import com.ibs.dockerbacked.connection.ContainerModel;
import com.ibs.dockerbacked.connection.DockerConnection;
import com.ibs.dockerbacked.connection.ImageModel;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = {"com.ibs.dockerbacked.mapper"})
@EnableConfigurationProperties({DockerCloudConfig.class})
public class DockerCloudBoot {
    public static void main(String[] args) {

        SpringApplication.run(DockerCloudBoot.class, args);
    }

    @Bean
    public DockerClient dockerClient(DockerCloudConfig dockerCloudConfig){
        DockerConnection dockerConnection = new DockerConnection(dockerCloudConfig.getUserName(),dockerCloudConfig.getPassWord(),
                dockerCloudConfig.getEmail(),dockerCloudConfig.getHost(),dockerCloudConfig.getUrl());
        return dockerConnection.connect();
    }

    @Bean
    public ContainerModel containerModel(DockerClient dockerClient) {
        return new ContainerModel(dockerClient);
    }
    @Bean
    public ImageModel imageModel(DockerClient dockerClient){
        return new ImageModel(dockerClient);
    }
}
