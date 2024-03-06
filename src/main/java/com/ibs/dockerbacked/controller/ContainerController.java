package com.ibs.dockerbacked.controller;

import cn.hutool.core.io.FileUtil;
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
import com.ibs.dockerbacked.service.FileService;
import com.ibs.dockerbacked.service.SpaceService;
import com.ibs.dockerbacked.util.JwtUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private FileService fileService;

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
     *@retur
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
     * 更新文件到容器
     * @param tagetPath 目标存放地址
     * @return
     */
    @PostMapping("/upload")
    public Result uploadFileToContainer(@RequestParam("file") MultipartFile multipartFile, @RequestHeader(HttpHeaders.AUTHORIZATION) String token,String containerId,String tagetPath){
        String account = JwtUtil.getUserAccount(token);
        String savePath = spaceService.getContainerSpace(account,containerId);
        try {
            if(!FileUtil.exist(savePath)){
                spaceService.createContainerSpace(account,containerId);
            }
            fileService.saveFile(multipartFile.getInputStream(),multipartFile.getOriginalFilename(),savePath);
        } catch (IOException e) {
            throw new CustomExpection(Constants.FILE_WRITE_FAIL);
        }
        containerService.uploadFileToContainer(containerId,tagetPath,savePath+File.separator+multipartFile.getOriginalFilename());
        return Result.success(Constants.CODE_200,"文件成功！");
    }

    /**
     * 下载文件从容器
     * @param token
     * @param containerId
     * @param targetPath 目标地址
     * @return
     */
    @PostMapping("/download")
    public Result downloadFileFromContainer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Param("containerId") String containerId, @Param("targetPath") String targetPath){

        int lastIndex = targetPath.lastIndexOf('/');
        String fileName = targetPath.substring(lastIndex+1,targetPath.length());
        String account = JwtUtil.getUserAccount(token);
        String savePath = spaceService.getContainerSpace(account,containerId);

        byte[] context =  containerService.downloadFileFromContainer(containerId,targetPath);
        try {
            if(!FileUtil.exist(savePath)){
                spaceService.createContainerSpace(account,containerId);
            }
            fileService.saveFile(context,fileName,savePath);
        } catch (FileNotFoundException e) {
            throw new CustomExpection(Constants.FILE_WRITE_FAIL);
        }
        return Result.success(Constants.CODE_200,"文件成功！");
    }


}
