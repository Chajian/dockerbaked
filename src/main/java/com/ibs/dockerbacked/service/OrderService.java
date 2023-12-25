package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.AddOrder;
import com.ibs.dockerbacked.task.DTask;

import java.util.List;

/**
 * @author sn
 */
public interface OrderService extends IService<Order> {

    /**
     *
     * @param packetId 套餐id
     * @param userId 用户id
     * @param addContainer 容器配置
     * @param lifeTime 订单生命周期
     * @return
     */
    Order createOrderTask(Order order,int packetId, long userId, AddContainer addContainer,int lifeTime);

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
    boolean paied(Order order);

    public Order sendMessage(AddOrder addOrder);

    /**
     * get Dtask
     * @param orderId
     * @return
     */
    public DTask getDTaskByOrderId(int orderId);

    /**
     * 获取当前用户相关的order
     * @return
     */
    public List<Order> getAllOrderByUser(int userId);
}
