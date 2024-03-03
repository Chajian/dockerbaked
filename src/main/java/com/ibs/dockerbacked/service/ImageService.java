package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.dockerjava.api.model.DockerObject;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Image;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ImageService extends IService<Image>{
    Image getImageByName(String imageName);
    DockerObject getImageObjectByName(String imageName);

    List<? extends DockerObject> getImages(ImagesParam imagesParam, long userId);
    List<Image> getImagesByDatabase(ImagesParam imagesParam, long userId);

    Result pull(String imageName,String tag);

    /**
     * build镜像通过镜像空间的DOCKERFILE
     * @param imageName
     * @param account
     * @return
     */
    Result build(String imageName,String account);

    List<Image> dockerObjectToImage(List<? extends DockerObject> objects);


    /**
     * 上传文件到镜像空间
     * @param multipartFile
     * @param imageName
     * @param account
     * @return
     */
    boolean updateImageFile(MultipartFile multipartFile,String imageName,String account);

}
