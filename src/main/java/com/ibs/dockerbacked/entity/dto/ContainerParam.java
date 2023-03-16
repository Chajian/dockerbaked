package com.ibs.dockerbacked.entity.dto;

import lombok.Data;

import java.util.List;

/**
*@descript 容器的查询条件
*@author sm
*@date 2023/3/4 21:02
*@version 1.0
*/
@Data
public class ContainerParam {

    //用户名
    private String account;
    //容器Id
    private String containerId;

    //状态
    private String[] status;
}
