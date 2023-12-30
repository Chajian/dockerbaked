package com.ibs.dockerbacked.controller;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.ExecParam;
import com.ibs.dockerbacked.entity.vo.Dashboard;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chen
 * @version 1.0
 * @descript 普通用户的容器接口
 * @date 2023/3/4 21:34
 */
@RestController
@RequestMapping("/ibs/api/containers")
public class ContainerController {
    Map<Integer,String> containerMap = new HashMap<>();


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
    @GetMapping("/get/{page}/{pageSize}")
    public Result getContainers(@RequestBody(required = false) ContainerParam containerParam,
                                @PathVariable(value = "page") Integer page,
                                @PathVariable(value = "pageSize") Integer pageSize,
                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        int userId = JwtUtil.getUserId(token);
        return containerService.getContainers( page, pageSize, userId);
    }

    /***
     *@descript 创建容器
     *@param  *
     *@return  /ibs/api/containers/create
     *@author sn
     *@version 1.0
     */
    @PostMapping("/create")
    public Result createContainer(@RequestBody AddContainer addContainer, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        int userId = JwtUtil.getUserId(token);
        String containerId = containerService.createContainer(addContainer, userId,null);
        return Result.success(200,"success",containerId);
    }

    /***
     *@descript 操作容器
     *@param  *
     *@return
     *@author chen  /ibs/api/containers/{id}/{action}
     *@version 1.0
     */
    @PostMapping("/{id}/{status}")
    public Result operateContainer(@PathVariable("id") String containerId, @PathVariable("status") String status,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if(!containerService.hasContainer(containerId, JwtUtil.getUserId(token)))
            return Result.error(Constants.CODE_400);

        return containerService.operateContainer(containerId, status);
    }

    /**
     * 执行sh语句
     */
    @PostMapping("/{id}/exec")
    public Result execContainer(@PathVariable("id") String containerId,@RequestBody() ExecParam exec,@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        if(!containerService.hasContainer(containerId, JwtUtil.getUserId(token)))
            return Result.error(Constants.CODE_400);


        String result = containerService.execCommand(containerId,exec.getCommand(), exec.getLoc());
        if(result==null)
            return Result.error(Constants.EXEC_ERROR);
        return Result.success(Constants.CODE_200,result);
    }

    /**
     * dashboard面板数据
     * @return
     */
    @GetMapping("/dashboard/{id}")
    public Result dashBoard(@PathVariable("id") String containerId,@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        if(!containerService.hasContainer(containerId, JwtUtil.getUserId(token)))
            return Result.error(Constants.CODE_400);
        Dashboard dashboard = new Dashboard();
        dashboard.setContainer(containerService.getContainerById(containerId));



        return Result.success(Constants.CODE_200,dashboard);
    }



}
