package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.service.PacketService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐模块
 */
@RestController
@RequestMapping("ibs/admin/packet")
@RequiresRoles("admin")
public class PacketController {
    @Autowired
    private PacketService packetService;

    /** author chen
     * param 套餐的信息
     * @return true or false
     */
    @PostMapping("/create")
    public Result<Boolean> createPacket(@RequestBody Hardware hardware, boolean ifFree){
        return packetService.createPacket(hardware,ifFree);
    }
}
