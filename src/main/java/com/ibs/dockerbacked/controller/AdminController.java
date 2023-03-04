package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.PageParam;
import com.ibs.dockerbacked.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author chen
 * @version 1.0
 * @descript 管理员接口
 * @date 2023/3/2 19:04
 */
@RestController
@RequestMapping("ibs/admin/containers")
public class AdminController {
    @Autowired
    private ContainerService containerService;

    /***
    *@descript 操纵容器
    *@param  *
    *@return
    *@author  chen  /ibs/api/admin/containers/{id}/{status}
    *@version 1.0
    */
    @GetMapping("/{id}/{status}")
    public Container operateContainer(@PathVariable("id") Long containerId,@PathVariable("status")String status){
        return containerService.getContainersByIdOrStatus(containerId,status);
    }
    /***
     *@descript 创建容器
     *@param *
     *@return
     *@author chen   /ibs/api/admin/containers/create
     *@version 1.0
     */
    @PostMapping("/create")
    public void create(@RequestBody AddContainer addContainer) {
        containerService.createContainer(addContainer);
    }

    /***
     *@descript 查询容器
     *@param  *
     *@return  容器集合
     *@author  chen /ibs/api/admin/containers/
     *@version 1.0
     */
    @PostMapping
    public List<Container> getContainers(@RequestBody ContainerParam containerParam) {
        return containerService.getContainers(containerParam, null);
    }


}
