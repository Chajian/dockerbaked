package com.ibs.dockerbacked.service.serviceImpl;

import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.connection.ImageModel;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.PageParam;
import com.ibs.dockerbacked.entity.dto.PullImages;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.ContainerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ContainerServiceImplTest {
    @Autowired
    private ContainerService containerService;

    @Autowired
    private ImageModel imageModel;

    //查询容器
    @Test
    void getContainersByStatus() {
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
//        System.out.println(containerService.getContainersByStatus(null, list));

    }

    @Test
    void getContainers() {
        ContainerParam containerParam = new ContainerParam();
//        containerParam
//        System.out.println(containerService.getContainers(containerParam, 1234L));
    }

    @Test
    void createContainer() {
    }

    @Test
    void getContainersByIdOrStatus() {
    }

    @Test
    void getImages() {
    }


    @Test
    void testPullImages() {
        PullImages pullImages = new PullImages();
        pullImages.setName("javaweb");
        pullImages.setTag("1.0");
        //通过指定的标签获取镜像,默认
        try {
            imageModel.pullImage(pullImages.getName(), pullImages.getTag());
        } catch (InterruptedException e) {
//            throw new CustomExpection(Constants.Internal_Server_Error, "拉取失败");
            log.info("拉取失败");
        }
    }

    @Test
    void testGetContainers() {

        String account = "10002";
        ContainerParam containerParam = new ContainerParam();
        containerParam.setAccount(account);
//         containerParam.setContainerId(0L);
//        containerParam.setStatus(List.of("1"));
        System.out.println(containerService.getContainers(containerParam, 1,5,null));
    }

    @Test
    void testCreateContainer() {
    }

    @Test
    void testGetContainersByIdOrStatus() {
    }

    @Test
    void testGetImages() {
    }

    @Test
    void pullImages() {
    }
}
