package com.ibs.dockerbacked.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName(value = "image")
public class ImageEntity {
    String id;
    String name;
    String author;
    long userId;
    String created;
}
