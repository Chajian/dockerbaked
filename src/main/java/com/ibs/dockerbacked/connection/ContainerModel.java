package com.ibs.dockerbacked.connection;

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

/**
 * container服务工具
 * @author Chajian
 */
@Slf4j
public class ContainerModel {
    DockerClient dockerClient = null;


    public ContainerModel(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public void inspectContainer(String containerId){
        log.info(dockerClient.inspectContainerCmd(containerId).exec().toString());
    }

    public synchronized void createContainer(String containerName,String imageName, List<PortBinding> ports,List<String> envs){
        HostConfig hostConfig = HostConfig.newHostConfig();
        hostConfig.withPortBindings(ports);
        dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(hostConfig)
                .withEnv(envs)
                .exec();
    }

    public synchronized void createContainer(long cpu,long memory,long disk,int network,String containerName,String imageName, List<PortBinding> ports){
        HostConfig hostConfig = HostConfig.newHostConfig();
        hostConfig.withCpuCount(cpu)
                .withMemory(1048576*memory)
                .withDiskQuota(disk*1048576*1024)
                .withNetworkMode("bridge")
                .withPortBindings(ports);
        dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(hostConfig)
                .exec();
    }

    public void deleteContainer(String containerId){
        dockerClient.removeContainerCmd(containerId).exec();
    }

    public void startContainer(String containerId){
        dockerClient.startContainerCmd(containerId).exec();
    }

    public void restartContainer(String containerId){
        dockerClient.restartContainerCmd(containerId).exec();
    }

    public void pauseContainer(String containerId){
        dockerClient.pauseContainerCmd(containerId).exec();
    }

    public void stopContainer(String containerId){
        dockerClient.stopContainerCmd(containerId).exec();
    }

    public void renameContainer(String newName){
        dockerClient.renameContainerCmd(newName);
    }

}
