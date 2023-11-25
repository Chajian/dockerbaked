package com.ibs.dockerbacked.connection;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.command.SearchImagesCmd;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.SearchItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class TestImages {
    //    @Autowired
    private DockerConnection dockerConnection;
    //    @Autowired
    private ImageModel imageModel;

    private String containerId;

    @Before
    public void init(){
        dockerConnection = new DockerConnection("dockerxylyjy","docker@123789","xylyjy@gmail.com","tcp://localhost:2375","https://index.docker.io/v1/");
        imageModel = new ImageModel(dockerConnection.connect());
    }

    @Test
    public void inspectImage(){
        InspectImageResponse imageResponse =  imageModel.inspectImage("mysql:latest");
        log.info(imageResponse.toString());
    }

    /**
     * 拉取镜像
     * @throws InterruptedException
     */
    @Test
    public void pullImage() throws InterruptedException {
        imageModel.pullImage("mysql","latest");
    }

    /**
     * 搜索镜像
     */
    @Test
    public void searchImage(){
        List<SearchItem> list = imageModel.searchImage("mysql",10);
        SearchItem item = list.get(0);
        item.getName();
        item.getDescription();
        item.isOfficial();
        item.isTrusted();
        log.info(Arrays.toString(list.toArray()));
    }

    @Test
    public void buildIamge() throws IOException {
        DockerClient dockerClient = dockerConnection.connect();
        File file = new File("builddir/Dockerfile");
        File dir = new File("builddir");
        System.out.println(Arrays.toString(dir.list()));
        String id = dockerClient.buildImageCmd(dir)
                .withDockerfile(file)
                .start().awaitImageId();
        System.out.println(id);
    }
}
