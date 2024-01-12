package com.ibs.dockerbacked.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * 硬件资源
 *
 * @author Chajian
 */
@Data
public class Hardware extends TimeRecord {
    private int id;
    @NotEmpty(message = "硬件类型不能为空")
    private String cpuType;

    @Min(message = "硬件类型不能为空",value = 0)
    private int cpuCoreNumber;
    @Min(message = "网络参数不能小于等于0",value = 0)
    private int networkSpeed;
    @Min(message = "磁盘不能小于等于0",value = 0)
    private int disk;
    @Min(message = "内存不能小于等于0",value = 0)
    private int memory;
    private float money;
}
