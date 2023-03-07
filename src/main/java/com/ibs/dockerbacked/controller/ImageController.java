package com.ibs.dockerbacked.controller;

import com.github.dockerjava.api.model.Image;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ibs/api/images")
public class ImageController {
    @Autowired
    private ContainerService containerService;

    /**
     * @param imagesParam
     * @return
     * @descript 查询镜像
     * @author
     */
    @GetMapping("/ibs/api/images")
    public Result<List<Image>> getImages(@RequestBody ImagesParam imagesParam) {

        containerService.getImages(imagesParam);

        return null;
    }
}
