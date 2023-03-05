package com.ibs.dockerbacked.connection;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.SearchItem;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

/**
 * image服务模块
 * @author Chajian
 */
@Slf4j
public class ImageModel {

    DockerClient dockerClient = null;




    public ImageModel(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }


    /**
     * 查看镜像信息
     * @param imageName
     */
    public InspectImageResponse inspectImage(String imageName){
        InspectImageResponse image = dockerClient.inspectImageCmd(imageName).exec();
        return image;
    }

    /**
     * 拉取镜像
     * @param imageName
     * @param tag
     * @throws InterruptedException
     */
    public void pullImage(String imageName,String tag) throws InterruptedException {
        dockerClient.pullImageCmd(imageName)
                .withTag(tag)
                .start().awaitCompletion();
    }

    /**
     * 搜索镜像
     * @param imageName
     * @param size
     * @return
     */
    public List<SearchItem> searchImage(String imageName, int size){
        return dockerClient.searchImagesCmd(imageName).withLimit(size).exec();
    }

    /**
     * 获取镜像
     * @param imageName
     * @return
     */
    public List<Image> getImages(String imageName){
        return dockerClient.listImagesCmd().withImageNameFilter(imageName).exec();
    }

}
