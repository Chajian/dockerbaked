package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.dockerjava.transport.DockerHttpClient;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Order;

/**
 * @author sn
 */
public interface OrderService extends IService<Order> {

    String createOrder();
}
