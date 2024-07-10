package com.ibs.dockerbacked.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author sm
 * @version 1.0
 * @descript 镜像的查询条件
 * @date 2023/3/4 21:02
 */
@Data
@ApiModel(value = "镜像查询条件")
public class ImagesParam {
    //-"label"-返回指定标签的镜像
    @ApiModelProperty(value = "通过label查询镜像")
    private String label;
    //-"page"-查询所有镜像，分页查询
    @ApiModelProperty(value = "分页配置")
    private PageParam pageParam;
    //-"id"-通过id查询
    @ApiModelProperty(value = "通过id查询")
    private String id;
    // size 获取的个数
    @ApiModelProperty(value = "限制查询数量")
    private Integer size;
    // 是否获取中央仓库的镜像
    @ApiModelProperty(value = "是否从中央仓库查询")
    private boolean center;
}
