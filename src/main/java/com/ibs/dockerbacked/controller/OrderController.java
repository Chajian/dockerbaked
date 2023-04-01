package com.ibs.dockerbacked.controller;

import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.OrderService;
import com.ibs.dockerbacked.util.JwtUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * 订单接口
 * @author Yanglin
 */
@RestController
public class OrderController {

    OrderService orderService;

    /**
     * 创建订单
     *
     * @param token
     * @return
     */
    @PostMapping("/order")
    public Result<Long> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                    @RequestBody int packageId,int hardId) throws ParseException {




        return null;
//        return Result.success(200,"创建成功",orderService.createOrder());
    }

}
