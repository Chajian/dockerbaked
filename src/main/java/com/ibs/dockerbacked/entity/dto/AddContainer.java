package com.ibs.dockerbacked.entity.dto;

import com.ibs.dockerbacked.entity.Container;
import lombok.Data;

/**
 * @author chen
 * @version 1.0
 * @descript 容器的新增信息
 * @date 2023/3/4 20:34
 */
@Data
public class AddContainer {
    //环境
    private String Env;

    //端口
    private Object ExposedPorts;

    //镜像名称
    private String ImageName;

    //工作目录
    private String WorkingDir;

    //是否关闭容器的网络
    private boolean NetworkDisabled;

    //容器信息
    private Container HostConfig;
}
