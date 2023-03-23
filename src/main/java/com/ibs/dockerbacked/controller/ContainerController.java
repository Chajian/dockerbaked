package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * @author chen
 * @version 1.0
 * @descript 普通用户的容器接口
 * @date 2023/3/4 21:34
 */
@RestController
@RequestMapping("/ibs/api/containers")
public class ContainerController {

    @Autowired
    private ContainerService containerService;

    /***
     *@descript 获取容器列表
     *@param containerParam page 页数，pagesize页大小，
     * @param token token
     *@return 返回符合条件的容器
     *@author chen
     *@version 1.0
     */
    @PostMapping
    public Result getContainers(@RequestBody ContainerParam containerParam,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = JwtUtil.getUserId(token);
        return containerService.getContainers(containerParam, page, pageSize, userId);
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

    /**
     * 创建订单
     *
     * @param token
     * @param order
     * @return
     */
    @PostMapping("/order")
    public Result<Long> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                            @RequestBody Order order) throws ParseException {

        long userId = JwtUtil.getUserId(token);
        if (userId == -1) throw new CustomExpection(500, "用户不存在或者未登录");

        return Result.success(200,"创建成功",containerService.createOrder(userId, order));
    }

}
