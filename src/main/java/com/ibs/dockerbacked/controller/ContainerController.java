package com.ibs.dockerbacked.controller;

import com.github.dockerjava.api.model.Image;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
     * @param token token
     *@return
     *@author chen
     *@version 1.0
     */
    @PostMapping
    public List<Container> getContainers(@RequestBody ContainerParam containerParam,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = JwtUtil.getUserId(token);
        return containerService.getContainers(containerParam, userId);
    }

    /***
     *@descript 创建容器
     *@param  *
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
     *@param  *
     *@return
     *@author chen  /ibs/api/containers/{id}/{action}
     *@version 1.0
     */
    @GetMapping("/{id}/{status}")
    public Container operateContainer(@PathVariable("id") String containerId, @PathVariable("status") String status) {
        return containerService.getContainersByIdOrStatus(containerId, status);
    }



}
