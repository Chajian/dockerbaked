package com.ibs.dockerbacked.entity.dto;

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
public class ExecParam {
    /**
     * 指令
     */
    private String command;
    /**
     * 执行命令的地址
     */
    private String loc;

}
