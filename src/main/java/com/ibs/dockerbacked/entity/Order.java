package com.ibs.dockerbacked.entity;

import lombok.Data;

/**
 * 订单
 * @author Chajian
 */
@Data
public class Order extends TimeRecord {
    private int id;
    private String name;
    private User user;
    private float money;
    private String payWay;
    private Container container;
    private Packet packet;

}
