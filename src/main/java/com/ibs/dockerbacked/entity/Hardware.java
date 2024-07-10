package com.ibs.dockerbacked.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * 硬件资源
 *
 * @author Chajian
 */
@Data
@ApiModel(value = "容器硬件配置")
public class Hardware extends TimeRecord {
    private int id;
    @NotEmpty(message = "硬件类型不能为空")
    @ApiModelProperty(value = "CPU类型")
    private String cpuType;

    @ApiModelProperty(value = "CPU核心数")
    @Min(message = "硬件类型不能为空",value = 0)
    private int cpuCoreNumber;
    @ApiModelProperty(value = "网络带宽，单位：MB")
    @Min(message = "网络参数不能小于等于0",value = 0)
    private int networkSpeed;
    @ApiModelProperty(value = "磁盘空间,单位：GB")
    @Min(message = "磁盘不能小于等于0",value = 0)
    private int disk;
    @ApiModelProperty(value = "内存大小，单位：GB")
    @Min(message = "内存不能小于等于0",value = 0)
    private int memory;
    @ApiModelProperty(value = "硬件配置花费")
    private float money;
}
