package com.ibs.dockerbacked.connection;

import com.ibs.dockerbacked.util.EntityUtils;
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
        EntityUtils.print(containerModel.inspectContainer("869a991df097"));
    }

    @Test
    public void stop(){
        containerModel.stopContainer("d24447f03f10b");
        containerModel.inspectContainer("");
    }


}
