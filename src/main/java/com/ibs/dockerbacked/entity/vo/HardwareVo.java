package com.ibs.dockerbacked.entity.vo;


import lombok.Data;

@Data
public class HardwareVo {
    String cpuType;
    String diskPercent;
    String containerStatus;
    String containerName;
    String imageName;
    String memory;
}
