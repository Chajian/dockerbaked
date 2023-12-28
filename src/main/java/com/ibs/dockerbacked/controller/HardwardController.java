package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.service.HardwareService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 硬件接口
 * @author Yanglin
 */
@RestController
@RequestMapping("/ibs/api/hardware")
public class HardwardController {

    @Autowired
    private HardwareService hardwareService;
    /**
     * 获取套餐信息
     * @return
     */
    @GetMapping
    public Result getHardwares(@Param("hardwareId") int hardwareId){
        Hardware hardware = hardwareService.getHardwareById(hardwareId);
        if(hardware!=null)
            return Result.success(Constants.CODE_200,hardware);
        return Result.error(Constants.CODE_401);
    }
}
