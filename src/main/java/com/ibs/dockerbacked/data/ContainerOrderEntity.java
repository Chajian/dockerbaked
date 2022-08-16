package com.ibs.dockerbacked.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ContainerOrderEntity {
    String id;
    long userId;
    String startTime;
    String endTime;
    String containerId;
    int cpu;
    int memory;
    int network;
    int disk;
    double cost;
}
