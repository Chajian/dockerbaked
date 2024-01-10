package com.ibs.dockerbacked.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.AddOrder;
import com.ibs.dockerbacked.mapper.ContainerMapper;
import com.ibs.dockerbacked.mapper.HardwareMapper;
import com.ibs.dockerbacked.mapper.PacketMapper;
import com.ibs.dockerbacked.service.OrderService;
import com.ibs.dockerbacked.task.BaseTask;
import com.ibs.dockerbacked.task.TaskThreadPool;
import com.ibs.dockerbacked.task.event.BaseListener;
import com.ibs.dockerbacked.task.event.DelayDriver;
import com.ibs.dockerbacked.task.event.Event;
import com.ibs.dockerbacked.task.event.Listener;
import com.ibs.dockerbacked.util.JwtUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    TaskThreadPool taskThreadPool;

    @Autowired
    ContainerMapper containerMapper;

    /**
     * 创建订单
     *
     * @param token
     * @return
     */
    @Transactional
    @PostMapping("/create")
    public Result<Order> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam("id") int packetId,@RequestBody AddContainer addContainer) throws ParseException {
        Packet packet = packetMapper.selectById(packetId);
        Hardware hard = hardwareMapper.selectById(packet.getHardwareId());
        if(packet==null||hard==null)
            return Result.error(Constants.NULL);
        //容器重命检测
        String name=JwtUtil.getUserId(token)+"-"+addContainer.getContainerName();
        boolean isRepeat = containerMapper.exists(new QueryWrapper<Container>().eq("name_c",name));
        if(isRepeat)
            return Result.error(Constants.CONTAINER_REPEAT_NAME);
        //发送消息
        AddOrder addOrder = new AddOrder(packetId,JwtUtil.getUserId(token),addContainer,100);
        Order result = orderService.sendMessage(addOrder);

        return Result.success(Constants.CODE_200.getCode(),"success",result);
    }

    /**
     *
     * @return
     */
    @Transactional
    @PutMapping("/pay/{id}")
    public Result payOrder(@PathVariable("id") int orderId){
        Order order = orderService.getById(orderId);
        if(orderService.paied(order))
            return Result.success(Constants.CODE_200.getCode(),"支付成功!", null);
        return Result.error(Constants.PAY_FAIL);
    }


    /**
     * 获取用户的订单
     * @param token
     * @return
     */
    @GetMapping("/get/{page}/{pageSize}")
    public Result getOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable(value = "page") Integer page,
                            @PathVariable(value = "pageSize") Integer pageSize){
        return Result.success(Constants.CODE_200,orderService.getAllOrderByUser(JwtUtil.getUserId(token)));
    }




}
