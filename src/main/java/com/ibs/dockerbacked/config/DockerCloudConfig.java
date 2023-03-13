package com.ibs.dockerbacked.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ToString
@ConfigurationProperties(prefix = "docker-cloud")
public class DockerCloudConfig {

    private String userName;

    private String passWord;

    private String email;

    private String host;

    private String url;
}
