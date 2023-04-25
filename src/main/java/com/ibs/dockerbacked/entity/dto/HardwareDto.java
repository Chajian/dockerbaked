package com.ibs.dockerbacked.entity.dto;

import com.ibs.dockerbacked.entity.Hardware;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HardwareDto extends Hardware {

    @Min(value = 0,message = "不能小于于0")
    private float cpuTypemoney = 20l;
    @Min(value = 0,message = "不能小于于0")
    private float cpuCoreNumberMoney = 20l;
    @Min(value = 0,message = "不能小于于0")
    private float networkSpeedMoney = 20l;
    @Min(value = 0,message = "不能小于于0")
    private float diskMoney = 20l;
}
