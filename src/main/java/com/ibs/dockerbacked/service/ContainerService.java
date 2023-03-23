package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.dockerjava.api.model.Image;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.entity.dto.PullImages;


/**
 * @author sn
 */
import java.text.ParseException;
import java.util.List;

public interface ContainerService extends IService<Container> {
    //查询容器
    Result<List<Container>> getContainers(ContainerParam containerParam, Integer page, Integer pageSize, Long userId);

    //创建容器
    void createContainer(AddContainer addContainer);

    //操作容器
    Container getContainersByIdOrStatus(String containerId, String status);

    //查询镜像
    List<Image> getImages(ImagesParam imagesParam);


    boolean pullImages(PullImages pullImages);

    Result operateContainer(String containerId, String status);

    Long createOrder(long userId, Order order) throws ParseException;
}
