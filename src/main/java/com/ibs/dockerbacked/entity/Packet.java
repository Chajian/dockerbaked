package com.ibs.dockerbacked.entity;

import lombok.Data;

/**
 * 套餐
 * @author Chajian
 */
@Data
public class Packet extends TimeRecord {
    private int id;
    private String descrption;
    private String name;
    private Hardware hardware;
}
