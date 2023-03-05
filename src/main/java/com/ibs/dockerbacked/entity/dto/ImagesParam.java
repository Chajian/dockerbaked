package com.ibs.dockerbacked.entity.dto;

import lombok.Data;


/**
 *@descript 镜像的查询条件
 *@author sm
 *@date 2023/3/4 21:02
 *@version 1.0
 */
@Data
public class ImagesParam {
    //-"label"-返回指定标签的镜像
    private String label;
    //-"page"-查询所有镜像，分页查询
    private PageParam pageParam;
    //-"id"-通过id查询
    private Integer id;
}
