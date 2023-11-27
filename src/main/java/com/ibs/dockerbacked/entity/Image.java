package com.ibs.dockerbacked.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 镜像实体类
 * @author Chajian
 */
@Data
public class Image extends TimeRecord{
    @TableId(type = IdType.AUTO)
    private int id;
    private String repository;
    private String tag;
    /*镜像id*/
    private String imageId;
    /*存储大小*/
    private int size;
    /*存储单位*/
    private String unit;
    /*作者*/
    private String author;
}
