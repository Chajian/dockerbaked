package com.ibs.dockerbacked.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.entity.dto.HardwareDto;
import com.ibs.dockerbacked.entity.vo.PacketVo;
import com.ibs.dockerbacked.service.HardwareService;
import com.ibs.dockerbacked.service.PacketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 套餐接口
 */
@Api(tags = "套餐接口")
@RestController
@RequestMapping("ibs/api/packet")
public class PacketController {
    @Autowired
    private PacketService packetService;

    @Autowired
    private HardwareService hardwareService;

    /**
     * author sn
     * param 套餐的信息
     *
     * @return true or false
     */
    @ApiOperation("创建套餐信息")
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
    @ApiOperation("获取套餐信息")
    @GetMapping
    public Result<List<PacketVo>> getPackets(@Param("page") int page, @Param("pageSize") int pageSize){
        List<Packet> list = packetService.getPackets(page,pageSize);
        List<PacketVo> result = new ArrayList<>();
        for(int i = 0 ; i < list.size();i++){
            result.add(packetService.ToPacketVo(list.get(i)));
        }
        return Result.success(Constants.CODE_200,result);

    }

    /**
     * 修改套餐信息
     * @return
     */
    @ApiOperation("修改套餐信息")
    @RequiresRoles("admin")
    @PostMapping("/update")
    public Result updatePakcet(@RequestBody @Validated PacketVo packetVo){
        Packet packet = packetVo.toPacket();
        packetService.updateById(packet);
        Hardware hardware = packetVo.getHardware();
        hardwareService.updateById(hardware);
        return Result.success(Constants.CODE_200);
    }




}
