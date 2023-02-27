package com.ibs.dockerbacked.entity;

/**
 * 订单
 * @author Chajian
 */
public class Order extends TimeRecord {
    private int id;
    private String name;
    private User user;
    private float money;
    private String payWay;
    private Container container;
    private Packet packet;

}
