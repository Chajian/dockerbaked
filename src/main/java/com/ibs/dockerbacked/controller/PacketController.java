package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.entity.dto.HardwareDto;
import com.ibs.dockerbacked.service.PacketService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐模块
 */
@RestController
@RequestMapping("ibs/api/admin/packet")
@RequiresRoles("admin")
public class PacketController {
    @Autowired
    private PacketService packetService;

    /**
     * author sn
     * param 套餐的信息
     *
     * @return true or false
     */
    @PostMapping("/create")
    public Result<HardwareDto> createPacket(@RequestBody @Validated HardwareDto hardware, boolean ifFree) {
        Result<HardwareDto> packet = packetService.createPacket(hardware, ifFree);
        return packet;
    }

}
