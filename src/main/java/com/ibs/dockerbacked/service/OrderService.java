package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.dockerjava.transport.DockerHttpClient;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Order;

/**
 * @author sn
 */
public interface OrderService extends IService<Order> {

    String createOrder(int packetId,long userId);

    /**
     * create qrCode
     * @param order
     * @return qrCodeUrl
     */
    String createQrCode(Order order);

    /**
     * paied method
     * @param order orderInfo
     */
    void Paied(Order order);
}
