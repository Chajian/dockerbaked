package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.PageParam;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.service.UserSerivce;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RequiresRoles("admin")
public class AdminController {
    @Autowired
    private ContainerService containerService;

    @Autowired
    private UserSerivce userSerivce;

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

    //    批量生产账号
    @GetMapping("/batch/{count}/{token}")
    public Boolean batchGenerationUser(@PathVariable("count") int count,
                                       @PathVariable String token) {
        boolean isSuccess = userSerivce.batchGenerationUser(count, token);
        return isSuccess;
    }

}
