package com.ibs.dockerbacked.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 订单
 * @author Chajian
 */
@Data
public class Order extends TimeRecord {
    private int id;
    private String name;
    private int userId;
    private float money;
    private String payWay;
    private int containerId;
    private int packetId;

}
