package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Image;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.entity.dto.PullImages;
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
        return imageService.pull(pullImages.getName(),pullImages.getTag());
    }

    /**
     * Dockerfile文件上传
     * @param dockerFile
     * @return
     */
    @PostMapping("/build")
    public Result build(MultipartFile dockerFile) throws IOException {
        File file = FileUtils.bytesToFile(dockerFile.getBytes(),dockerFile.getName());
        return imageService.build(file);
    }
}
