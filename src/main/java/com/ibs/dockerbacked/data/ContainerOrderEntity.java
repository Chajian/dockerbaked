package com.ibs.dockerbacked.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName(value = "containerorder")
public class ContainerOrderEntity {
    @TableId(type = IdType.AUTO)
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
