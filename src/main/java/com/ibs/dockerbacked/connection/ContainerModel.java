package com.ibs.dockerbacked.connection;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.InspectContainerCmdImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

/**
 * container服务模块
 * @author Chajian
 */
@Slf4j
public class ContainerModel {
    DockerClient dockerClient = null;


    public ContainerModel(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    /**
     * 查看container信息
     * @param containerId
     */
    public InspectContainerResponse inspectContainer(String containerId){
        InspectContainerResponse containerCmd =  dockerClient.inspectContainerCmd(containerId).exec();
        return containerCmd;
    }

    /**
     * 创建容器
     * @param containerName 容器名
     * @param imageName 镜像id
     * @param ports 端口
     * @param envs 环境
     */
    public synchronized CreateContainerResponse createContainer(String containerName,String imageName, List<PortBinding> ports,List<String> envs){
        HostConfig hostConfig = HostConfig.newHostConfig();
        hostConfig.withPortBindings(ports);
        CreateContainerResponse createContainerResponse =  dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(hostConfig)
                .withEnv(envs)
                .exec();
        return createContainerResponse;
    }


    /**
     * 创建容器
     * @param cpu 内核数量
     * @param memory 内存-单位m
     * @param disk 硬盘空间-单位G
     * @param network 网络带宽速度
     * @param containerName 容器名
     * @param imageName 镜像名
     * @param ports 端口映射
     */
    public synchronized CreateContainerResponse createContainer(long cpu,long memory,long disk,int network,String containerName,String imageName, List<PortBinding> ports,List<String> env){
        HostConfig hostConfig = HostConfig.newHostConfig();
        hostConfig.withCpuCount(cpu)
                .withMemory(1048576*memory)
                .withDiskQuota(disk*1048576*1024)
                .withNetworkMode("bridge")
                .withPortBindings(ports);
        CreateContainerResponse createContainerResponse = dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(hostConfig)
                .withEnv(env)
                .exec();
        return createContainerResponse;
    }

    /**
     * 删除容器
     * @param containerId
     */
    public void deleteContaqqiner(String containerId){
        dockerClient.removeContainerCmd(containerId).exec();
    }

    /**
     * 运行容器
     * @param containerId
     */
    public void startContainer(String containerId){
        dockerClient.startContainerCmd(containerId).exec();
    }

    /**
     * 重启容器
     * @param containerId
     */
    public void restartContainer(String containerId){
        dockerClient.restartContainerCmd(containerId).exec();
    }

    /**
     * 暂停容器
     * @param containerId
     */
    public void pauseContainer(String containerId){
        dockerClient.pauseContainerCmd(containerId).exec();
    }

    /**
     * 停止容器
     * @param containerId
     */
    public void stopContainer(String containerId){
        dockerClient.stopContainerCmd(containerId).exec();
    }

    /**
     * 重命名容器
     * @param newName
     */
    public void renameContainer(String newName){
        dockerClient.renameContainerCmd(newName);
    }

}
