package com.ibs.dockerbacked.connection;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.SearchItem;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

/**
 * image服务工具
 * @author Chajian
 */
@Slf4j
public class ImageModel {

    DockerClient dockerClient = null;


    public ImageModel(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }


    public void inspectImage(String imageName){
        log.info(dockerClient.inspectImageCmd(imageName).exec().toString());
    }


    public void pullImage(String imageName,String tag) throws InterruptedException {
        dockerClient.pullImageCmd(imageName)
                .withTag(tag)
                .start().awaitCompletion();
    }

    public List<SearchItem> searchImage(String imageName, int size){
        return dockerClient.searchImagesCmd(imageName).withLimit(size).exec();
    }

    public List<Image> getImages(String imageName){
        return dockerClient.listImagesCmd().withImageNameFilter(imageName).exec();
    }

}
