package com.ibs.dockerbacked.service.serviceImpl;

import cn.hutool.json.JSON;
import com.ibs.dockerbacked.connection.DockerConnection;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.entity.dto.PageParam;
import com.ibs.dockerbacked.service.ImageService;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ImageServiceImplTest {
    @Autowired
    private ImageService imageService;

    @Test
    void getImages() {
        ImagesParam imagesParam = new ImagesParam();
        PageParam param = new PageParam();
        param.setPage(1);
        param.setPageSize(20);
        imagesParam.setPageParam(param);
//        imagesParam.setId("sha256:3218b38490cec8d31976a40b92e09d61377359eab878db49f025e5d464367f3b");
        imagesParam.setId("86ac548fcc06");
        System.out.println(imageService.getImages(imagesParam,1234l).getData());

    }

    @Test
    void pull() {
        imageService.pull("mysql","latest");
    }
}