package com.ibs.dockerbacked.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ibs.dockerbacked.util.StringUtil;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName(value = "container")
public class ContainerEntity {
    String id;
    String imageId;
    long userId;
    String created;
    long cpu;
    long memory;
    long disk;
    int networkSpeed;

}
