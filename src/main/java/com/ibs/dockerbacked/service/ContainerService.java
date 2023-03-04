package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.entity.dto.PageParam;


/**
 * @author sn
 */
import java.util.List;
import java.util.Map;

public interface ContainerService extends IService<Container> {
    //查询容器
    List<Container> getContainers(ContainerParam containerParam, Long userId);

    //创建容器
    void createContainer(AddContainer addContainer);

    //操作容器
    Container getContainersByIdOrStatus(Long containerId, String status);

    //查询镜像
    List<Container> getImages(ImagesParam imagesParam);


}
