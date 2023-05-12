package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.dockerjava.transport.DockerHttpClient;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.dto.AddContainer;

/**
 * @author sn
 */
public interface OrderService extends IService<Order> {

    /**
     *
     * @param packetId 套餐id
     * @param userId 用户id
     * @param addContainer 容器配置
     * @param lifetime 订单生命周期
     * @return
     */
    Order createOrder(int packetId, long userId, AddContainer addContainer,int lifetime);

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
