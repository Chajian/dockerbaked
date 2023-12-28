package com.ibs.dockerbacked.connection;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.api.model.SearchItem;

import com.ibs.dockerbacked.task.event.BaseDriver;
import com.ibs.dockerbacked.task.event.PullImageEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.Duration;
import java.util.List;

/**
 * image服务模块
 * Image事件触发器
 * @author Chajian
 */
@Slf4j
public class ImageModel extends BaseDriver {

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
    public void pullImage(String imageName,String tag) {
        //PullImage事件
        log.info("pull:"+Thread.currentThread().getId());
        PullImageEvent pullImageEvent = new PullImageEvent();
        var parent = this;
        try {
            ResultCallback resultCallback =  dockerClient.pullImageCmd(imageName)
                   .withTag(tag)
                   .exec(new ResultCallback.Adapter<>(){
                       @Override
                       public void onComplete() {
                           //触发事件
                           com.ibs.dockerbacked.entity.Image image = new com.ibs.dockerbacked.entity.Image();
                           image.setTag(tag);
                           image.setName(imageName+":"+tag);
                           pullImageEvent.setT(image);
                           parent.Triger(pullImageEvent);
                           log.info("pull完成:"+Thread.currentThread().getId());
                           super.onComplete();
                       }

                       @Override
                       public void onNext(PullResponseItem object) {
                           pullImageEvent.setStatus("complete");
                           log.info("pull过程中:"+Thread.currentThread().getId());
                           super.onNext(object);
                       }

                       @Override
                       public void onError(Throwable throwable) {
                           pullImageEvent.setStatus("error");
                           pullImageEvent.setDesc(throwable.getMessage());
                           super.onError(throwable);
                       }
                   })
                    .awaitCompletion();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 搜索镜像
     * @param imageName
     * @param size 一次性搜索几个
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
        return dockerClient.listImagesCmd().exec();
    }

    /**
     * buidImage通过Dockerfile
     * @param file dockerfile
     */
    public void buildImage(File file){
        if(file!=null) {
            dockerClient.buildImageCmd()
                    .withDockerfile(file)
                    .exec(new BuildImageResultCallback() {
                        @Override
                        public void onComplete() {
                            super.onComplete();
                        }
                    });
        }
    }

    /**
     * build镜像
     * @param dockerFile
     * @param buildDir
     */
    public void buildIamge(File  dockerFile,File buildDir){
        dockerClient.buildImageCmd(buildDir)
                .withDockerfile(dockerFile)
                .start().awaitImageId();
    }

}
