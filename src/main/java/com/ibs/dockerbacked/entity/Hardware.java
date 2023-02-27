package com.ibs.dockerbacked.entity;

import lombok.Data;

/**
 * 硬件资源
 * @author Chajian
 */
@Data
public class Hardware extends TimeRecord {
    private int id;
    private String cpuType;
    private int cpuCoreNumber;
    private int networkSpeed;
    private int disk;
    private int memory;
}
