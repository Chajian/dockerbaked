package com.ibs.dockerbacked.controller;

import com.github.dockerjava.api.command.InspectImageResponse;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Image;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.entity.dto.PullImages;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.ImageService;
import com.ibs.dockerbacked.util.FileUtils;
import com.ibs.dockerbacked.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/ibs/api/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    /**
     * 获取镜像列表
     * author chen
     * @return 镜像列表
     */
    @PostMapping
    public Result getImages(@RequestBody(required = false) ImagesParam imagesParam,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Image> images = imageService.dockerObjectToImage(imageService.getImages(imagesParam, JwtUtil.getUserId(token)));

        return Result.success(Constants.CODE_200,images);
    }

    /** 拉取镜像
     * author chen
     * @return code 200 msg success
     */
    @PostMapping("/pull")
    public Result pull(@RequestBody PullImages pullImages){
        String fullName = pullImages.getName()+pullImages.getTag();
        //检测数据库中是否存在iamge
        Image image = imageService.getImageByName(fullName);
        if(image !=null)
            throw new CustomExpection(Constants.IMAGE_ALEARY_EXIST);
        //检测image仓库中是否存在image
        InspectImageResponse inspectImageResponse = (InspectImageResponse) imageService.getImageObjectByName(fullName);
        if(inspectImageResponse !=null) {
            image = new Image();
            image.setName(fullName);
            image.setTag(pullImages.getTag());
            image.setAuthor(inspectImageResponse.getAuthor());
            image.setSize(inspectImageResponse.getSize());
            imageService.save(image);
            throw new CustomExpection(Constants.IMAGE_ALEARY_EXIST);
        }

        return imageService.pull(pullImages.getName(),pullImages.getTag());
    }

    /**
     * build镜像通过Dockerfile文件
     * @param imageName
     * @return
     */
    @PostMapping("/build")
    public Result build(String imageName,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws IOException {
        String account = JwtUtil.getUserAccount(token);
        return imageService.build(imageName,account);
    }

}
