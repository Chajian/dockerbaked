package com.ibs.dockerbacked.entity.dto;

import lombok.Data;


/**
 * @author sm
 * @version 1.0
 * @descript 镜像的查询条件
 * @date 2023/3/4 21:02
 */
@Data
public class ImagesParam {
    //-"label"-返回指定标签的镜像
    private String label;
    //-"page"-查询所有镜像，分页查询
    private PageParam pageParam;
    //-"id"-通过id查询
    private String id;
    // size 获取的个数
    private Integer size;
}
