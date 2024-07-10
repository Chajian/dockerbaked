package com.ibs.dockerbacked.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PullImage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("拉取镜像参数")
public class PullImages {
    @ApiModelProperty(value = "通过镜像名查询")
    private String name;
    @ApiModelProperty(value = "通过tag查询")
    private String tag;
    @ApiModelProperty(value = "通过镜像id查询")
    private int imageId;
    @ApiModelProperty(value = "通过状态查询")
    private String status;
}
