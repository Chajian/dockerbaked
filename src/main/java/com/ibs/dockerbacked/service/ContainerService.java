package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ibs.dockerbacked.entity.Container;

import java.util.List;
import java.util.Map;

public interface ContainerService extends IService<Container> {
    //获取容器
    List<Container> getContainersByStatus(Integer containerId, List<String> status);
}
