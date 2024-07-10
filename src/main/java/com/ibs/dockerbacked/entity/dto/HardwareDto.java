package com.ibs.dockerbacked.entity.dto;

import com.ibs.dockerbacked.entity.Hardware;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("硬件配置参数")
public class HardwareDto extends Hardware {
    @ApiModelProperty(value = "CPU类型价格")
    @Min(value = 0,message = "不能小于于0")
    private float cpuTypemoney = 20l;
    @ApiModelProperty(value = "cpu一个核心的价格")
    @Min(value = 0,message = "不能小于于0")
    private float cpuCoreNumberMoney = 20l;
    @ApiModelProperty(value = "1MB网速的价格")
    @Min(value = 0,message = "不能小于于0")
    private float networkSpeedMoney = 20l;
    @Min(value = 0,message = "不能小于于0")
    @ApiModelProperty(value = "1GB存储空间的价格")
    private float diskMoney = 20l;
    @ApiModelProperty(value = "硬件配置参数的名称")
    private String name = "";
    private String desc = "";
}
