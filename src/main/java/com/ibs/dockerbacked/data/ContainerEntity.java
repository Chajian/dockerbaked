package com.ibs.dockerbacked.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName(value = "container")
public class ContainerEntity {
    @TableId(type = IdType.AUTO)
    String id;
    String imageId;
    long userId;
    String created;
}
