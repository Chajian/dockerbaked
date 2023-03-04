package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chen
 * @version 1.0
 * @descript 普通用户的容器接口
 * @date 2023/3/4 21:34
 */
@RestController
@RequestMapping(" /ibs/api/containers")
public class ContainerController {

    @Autowired
    private ContainerService containerService;

    /***
     *@descript 获取容器列表
     *@param containerParam *
     *@return
     *@author chen
     *@version 1.0
     */
    @PostMapping
    public List<Container> getContainers(@RequestBody ContainerParam containerParam) {
        Long userId = 1L; //测试的用户Id
        return containerService.getContainers(containerParam, userId);
    }

    /***
     *@descript 创建容器
     *@param null *
     *@return  /ibs/api/containers/create
     *@author sn
     *@version 1.0
     */
    @PostMapping("/create")
    public void createContainer(@RequestBody AddContainer addContainer) {
        containerService.createContainer(addContainer);
    }

    /***
     *@descript 操作容器
     *@param null *
     *@return
     *@author chen  /ibs/api/containers/{id}/{action}
     *@version 1.0
     */
    @GetMapping("/{id}/{status}")
    public Container operateContainer(@PathVariable("id") Long containerId, @PathVariable("status") String status) {
        return containerService.getContainersByIdOrStatus(containerId, status);
    }

    /**
     *没写完
     * @param imagesParam
     * @return
     * @descript 查询镜像
     * @author
     */
    @GetMapping("/ibs/api/images")
    public Result<List<Container>> getImages(@RequestBody ImagesParam imagesParam) {

        containerService.getImages(imagesParam);
        return null;
    }
}
