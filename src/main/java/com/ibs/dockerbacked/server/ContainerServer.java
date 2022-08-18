package com.ibs.dockerbacked.server;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class ContainerServer {
    DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost("npipe:////./pipe/docker_engine")
            .withDockerTlsVerify(false)
            .withRegistryUrl("https://index.docker.io/v1/")
            .withRegistryEmail("xylyjy@gmail.com")
            .withRegistryUsername("dockerxylyjy")
            .withRegistryPassword("docker@123789")
            .build();

    DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
            .dockerHost(config.getDockerHost())
            .sslConfig(config.getSSLConfig())
            .maxConnections(100)
            .connectionTimeout(Duration.ofSeconds(30))
            .responseTimeout(Duration.ofSeconds(45))
            .build();

    DockerClient dockerClient = DockerClientImpl.getInstance(config,httpClient);

    public void inspectContainer(String containerId){
//        DockerClient dockerClient = DockerClientImpl.getInstance(config,httpClient);
        log.info(dockerClient.inspectContainerCmd(containerId).exec().toString());
    }

    public void createContainer(String containerName,String imageName, PortBinding ... portBinding){
//        DockerClient dockerClient = DockerClientImpl.getInstance(config,httpClient);
        HostConfig hostConfig = HostConfig.newHostConfig();
        List<PortBinding> ports = List.of(portBinding);
        hostConfig.withPortBindings(ports);
        dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(hostConfig)
                .exec();
    }

    public void deleteContainer(String containerId){
//        DockerClient dockerClient = DockerClientImpl.getInstance(config,httpClient);
        dockerClient.removeContainerCmd(containerId).exec();
    }

    public void startContainer(String containerId){
//        DockerClient dockerClient = DockerClientImpl.getInstance(config,httpClient);
        dockerClient.startContainerCmd(containerId).exec();
    }

    public void pauseContainer(String containerId){
//        DockerClient dockerClient = DockerClientImpl.getInstance(config,httpClient);
        dockerClient.pauseContainerCmd(containerId).exec();
    }

    public void stopContainer(String containerId){
//        DockerClient dockerClient = DockerClientImpl.getInstance(config,httpClient);
        dockerClient.stopContainerCmd(containerId).exec();
    }

    public void renameContainer(String newName){
//        DockerClient dockerClient = DockerClientImpl.getInstance(config,httpClient);
        dockerClient.renameContainerCmd(newName);
    }

}
