package com.ibs.dockerbacked.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 订单
 * @author Chajian
 */
@Data
@TableName("orders")
public class Order extends TimeRecord {
    private int id;
    @TableField("name_o")
    private String name;
    private int userId;
    private float money;
    private String payWay;
    private int containerId;
    private int packetId;
    private String description;

}
