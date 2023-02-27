package com.ibs.dockerbacked.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName(value = "imageorder")
public class ImageOrderEntity {
    @TableId(type = IdType.AUTO)
    long id;
    String imageId;
    long userId;
    String imageName;
    String created;
    String dockerfile;
}
