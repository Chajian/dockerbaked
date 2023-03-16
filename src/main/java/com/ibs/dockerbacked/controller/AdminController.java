package com.ibs.dockerbacked.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.service.UserSerivce;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


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
    @Autowired
    private UserSerivce userSerivce;


    /***
     *@descript 操纵容器
     *@param  *
     *@return 更新后的容器信息
     *@author chen  /ibs/api/admin/containers/{id}/{status}
     *@version 1.0
     */
    @GetMapping("/{id}/{status}")
    public Result operateContainer(@PathVariable("id") String containerId, @PathVariable("status") String status) {
        return containerService.operateContainer(containerId, status);
    }

    /** 新增容器
     *  author chen
     * @param addContainer
     * @return 成功为200 失败为500
     */
    @PostMapping("/create")
    public Result create(@RequestBody AddContainer addContainer) {
        containerService.createContainer(addContainer);
        return Result.success(200, "创建成功", null);
    }

    /***
     *@descript 查询容器
     *@param  * containerParam 是前端传递过来的参数
     *@return 符合条件的容器集合
     *@author chen /ibs/api/admin/containers/
     *@version 1.0
     */
    @PostMapping
    public Result<List<Container>> getContainers(@RequestBody(required = false) ContainerParam containerParam,
                                                 @RequestParam(required = false,value = "page",defaultValue = "1")Integer page,
                                                 @RequestParam(required = false,value = "pageSize",defaultValue = "5") Integer pageSize) {
        return containerService.getContainers(containerParam, page, pageSize, null);
    }

    /***
     *@descript 批量生产账号，未完成
     *@param count
     *@param token
     *@return bool
     *@author sn
     *@version 1.0
     */
    @GetMapping("/batch/{count}/{token}")
    public Boolean batchGenerationUser(@PathVariable("count") int count,
                                       @PathVariable String token) {
        boolean isSuccess = userSerivce.batchGenerationUser(count, token);
        return isSuccess;
    }



}

