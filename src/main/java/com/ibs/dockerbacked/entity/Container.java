package com.ibs.dockerbacked.entity;

import lombok.Data;

import java.util.Date;

/**
 * 容器
 * @author Chajian
 */
@Data
public class Container extends TimeRecord {
    private int id;
    private String imageId;
    private String name;
    private String description;
    private Users owner;
    private String state;
    private Packet packet;
    private Date leaseAt;
    private Date leaseEnd;
}
