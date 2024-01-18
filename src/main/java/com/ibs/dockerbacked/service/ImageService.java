package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.dockerjava.api.model.DockerObject;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Image;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ImageService {
    List<? extends DockerObject> getImages(ImagesParam imagesParam, long userId);

    Result pull(String imageName,String tag);

    /**
     * build镜像通过镜像空间的DOCKERFILE
     * @param imageName
     * @param account
     * @return
     */
    Result build(String imageName,String account);

    List<Image> dockerObjectToImage(List<? extends DockerObject> objects);

    //开辟用户镜像空间
    boolean createUserSpace(String account);

    /**
     * 创建镜像空间
     * @param account
     * @param imageName
     * @return
     */
    boolean createImageSpace(String account,String imageName);

    /**
     * 上传文件到镜像空间
     * @param multipartFile
     * @param imageName
     * @param account
     * @return
     */
    boolean updateImageFile(MultipartFile multipartFile,String imageName,String account);

    /**
     * 获取用户空间地址
     * @param account
     * @return
     */
    String getUserSpacePath(String account);

    /**
     * 获取用户镜像空间
     * @param account
     * @param image
     * @return
     */
    String getImageSpacePath(String account,String image);

}
