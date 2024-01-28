package com.ibs.dockerbacked.entity.vo;

import com.ibs.dockerbacked.entity.Container;
import lombok.Data;

/**
 * dashboard信息
 */
@Data
public class Dashboard {
    String containerName;
    double cpuPortion;
    double memoryPortion;

}
