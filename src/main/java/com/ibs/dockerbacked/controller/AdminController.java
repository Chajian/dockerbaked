package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author chen
 * @version 1.0
 * @descript 管理员接口
 * @date 2023/3/2 19:04
 */
@RestController
@RequestMapping("ibs/admin")
public class AdminController {
    @Autowired
    private ContainerService containerService;

    /***
     *@descript 获取容器
     *@param
     *@return
     *@author chen
     *@version 1.0
     */
    @GetMapping("/containers")
    public List<Container> containersAll(@RequestParam(value = "id", required = false) Integer containerId, @RequestParam("status") List<String> status) {

        return containerService.getContainersByStatus(containerId, status);
    }
}
