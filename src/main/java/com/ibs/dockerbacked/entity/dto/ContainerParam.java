package com.ibs.dockerbacked.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
*@descript 容器的查询条件
*@author sm
*@date 2023/3/4 21:02
*@version 1.0
*/
@ApiModel(value = "容器查询配置")
@Data
public class ContainerParam {

    //用户名
    @ApiModelProperty(value = "通过用户名查询容器")
    private String account;
    //容器Id
    @ApiModelProperty(value = "通过id查询容器")
    private String containerId;

    //状态
    @ApiModelProperty(value = "通过容器状态查询容器")
    private String[] status;
}
