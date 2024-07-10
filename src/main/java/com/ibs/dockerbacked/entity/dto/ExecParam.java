package com.ibs.dockerbacked.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@descript 容器的执行指令参数
 *@author Yanglin
 *@date 2023/11/08 21:02
 *@version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "容器执行指令实体")
public class ExecParam {
    /**
     * 指令
     */
    @ApiModelProperty(value = "执行的指令,例如 cd /home等")
    private String command;
    /**
     * 执行命令的地址
     */
    @ApiModelProperty(value = "指令执行的路径，例如/home表示指令在/home路径下执行的")
    private String loc;

}
