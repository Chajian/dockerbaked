package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.mapper.HardwareMapper;
import com.ibs.dockerbacked.service.HardwareService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 硬件接口
 * @author Yanglin
 */
@RestController
@RequestMapping("/ibs/api/hardware")
public class HardwardController {

    @Autowired
    private HardwareService hardwareService;

    @Autowired
    private HardwareMapper hardwareMapper;

    /**
     * 获取套餐信息
     * @return
     */
    @GetMapping
    public Result getHardware(@Param("hardwareId") int hardwareId){
        Hardware hardware = hardwareService.getHardwareById(hardwareId);
        if(hardware!=null)
            return Result.success(Constants.CODE_200,hardware);
        return Result.error(Constants.HARDWARE_NULL);
    }

    /**
     * 获取套餐信息
     * @return
     */
    @GetMapping("/{page}/{pageSize}")
    public Result getHardwares(@PathVariable("page")int page,@PathVariable("pageSize") int pageSize){
        List<Hardware> hardwares = hardwareService.getHardwares(page,pageSize);
        if(hardwares!=null)
            return Result.success(Constants.CODE_200,hardwares);
        return Result.error(Constants.HARDWARE_NULL);
    }

    /**
     * 获取硬件信息通过容器Id
     * @return
     */
    @GetMapping("/get/{containerId}")
    public Result getHardByContainer(@PathVariable("containerId")String containerId){
        Hardware hardware = hardwareMapper.getHardwareByContainer("023cd63d9b97d603cb45539c5391d5b5654a7464bfd2be1f8c3c5c925564899c");
        return Result.success(Constants.CODE_200,hardware);
    }
}