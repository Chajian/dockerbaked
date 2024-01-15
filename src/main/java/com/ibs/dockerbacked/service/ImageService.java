package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.dockerjava.api.model.DockerObject;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Image;
import com.ibs.dockerbacked.entity.dto.ImagesParam;

import java.io.File;
import java.util.List;

public interface ImageService {
    List<? extends DockerObject> getImages(ImagesParam imagesParam, long userId);

    Result pull(String imageName,String tag);

    Result build(File dockerFile);

    List<Image> dockerObjectToImage(List<? extends DockerObject> objects);

    //开辟镜像空间
    Result createImageSpace();

}
