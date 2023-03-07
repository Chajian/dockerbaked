package com.ibs.dockerbacked.connection;

import org.junit.Before;
import org.junit.Test;



public class TestContainer {
    private DockerConnection dockerConnection;

    private ContainerModel containerModel;

    @Before
    public void init(){
        dockerConnection = new DockerConnection("dockerxylyjy","docker@123789","xylyjy@gmail.com","npipe:////./pipe/docker_engine","https://index.docker.io/v1/");
        containerModel = new ContainerModel(dockerConnection.connect());
    }


    @Test
    public void getInfo(){
        containerModel.inspectContainer("");
    }


}
