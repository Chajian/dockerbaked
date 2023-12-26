package com.ibs.dockerbacked.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.entity.dto.HardwareDto;
import com.ibs.dockerbacked.service.PacketService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 套餐模块
 */
@RestController
@RequestMapping("ibs/api/packet")
public class PacketController {
    @Autowired
    private PacketService packetService;

    /**
     * author sn
     * param 套餐的信息
     *
     * @return true or false
     */
    @RequiresRoles("admin")
    @PostMapping("/create")
    public Result<HardwareDto> createPacket(@RequestBody @Validated HardwareDto hardware, boolean ifFree) {
        Result<HardwareDto> packet = packetService.createPacket(hardware, ifFree);
        return packet;
    }

    /**
     * 获取套餐信息
     * @return
     */
    @GetMapping
    public Result<List<Packet>> getPackets(@Param("page") int page, @Param("pageSize") int size){
        List<Packet> list = packetService.getPackets(page,size);
        return Result.success(Constants.CODE_200,list);

    }
}
