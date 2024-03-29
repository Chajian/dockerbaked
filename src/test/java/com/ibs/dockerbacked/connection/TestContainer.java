package com.ibs.dockerbacked.connection;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.command.ExecCreateCmdImpl;
import com.github.dockerjava.core.exec.ExecCreateCmdExec;
import com.ibs.dockerbacked.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@SpringBootTest
public class TestContainer {
//    @Autowired
    private DockerConnection dockerConnection;
//    @Autowired
    private ContainerModel containerModel;

    private String containerId;

    @Before
    public void init(){
        dockerConnection = new DockerConnection("dockerxylyjy","docker@123789","xylyjy@gmail.com","tcp://8.130.92.152:2375","https://index.docker.io/v1/");
        containerModel = new ContainerModel(dockerConnection.connect());
    }


    @Test
    public void getInfo(){
        EntityUtils.print(containerModel.inspectContainer("57eae25089a1"));
    }

    @Test
    public void create(){

        List list = new ArrayList<>();
        list.add(new PortBinding(new Ports.Binding("0.0.0.0","3306"),new ExposedPort(3306)));

        List<String> envs = new ArrayList<>();
        envs.add("MYSQL_ROOT_PASSWORD=Qq123456789");

        CreateContainerResponse createContainerResponse = containerModel.createContainer(2,1024,10,100,"test","mysql:latest",list,envs);
        containerId = createContainerResponse.getId();
        log.info(createContainerResponse.toString());
    }

    @Test
    public void start(){
        containerModel.startContainer("889c0d99121b43618df8e5f711afa9e10cc17d7627e82f8aaf05b700dae34f8c");
    }

    @Test
    public void stop(){
        containerModel.stopContainer("889c0d99121b43618df8e5f711afa9e10cc17d7627e82f8aaf05b700dae34f8c");
    }

    @Test
    public void rm(){
        containerModel.deleteContaqqiner("889c0d99121b43618df8e5f711afa9e10cc17d7627e82f8aaf05b700dae34f8c");
    }

    @Test
    public void exec(){
        DockerClient dockerClient = dockerConnection.connect();
        ExecCreateCmdResponse response = dockerClient.execCreateCmd("2be0a12433a6")
                .withCmd("ls")
                .withUser("root")
                .exec();
        log.info(dockerClient.inspectExecCmd(response.getId()).exec().toString());

    }


}
