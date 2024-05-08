package com.ibs.dockerbacked.entity.vo;


import com.ibs.dockerbacked.entity.Hardware;
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
