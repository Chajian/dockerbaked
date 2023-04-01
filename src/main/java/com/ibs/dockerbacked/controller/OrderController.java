package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.HardwareMapper;
import com.ibs.dockerbacked.mapper.PacketMapper;
import com.ibs.dockerbacked.service.OrderService;
import com.ibs.dockerbacked.util.JwtUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * 订单接口
 * @author Yanglin
 */
@RestController
@RequestMapping("/ibs/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    PacketMapper packetMapper;

    @Autowired
    HardwareMapper hardwareMapper;

    /**
     * 创建订单
     *
     * @param token
     * @return
     */
    @PostMapping("/order")
    public Result<Long> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                    @RequestBody int packetId,int hardId) throws ParseException {
        Packet packet = packetMapper.selectById(packetId);
        Hardware hardware = hardwareMapper.selectById(hardId);
        if(packet==null||hardware==null)
            return Result.error(Constants.CODE_401,"packet or hardware have problems!");
        return null;
//        return Result.success(200,"创建成功",orderService.createOrder());
    }

}
