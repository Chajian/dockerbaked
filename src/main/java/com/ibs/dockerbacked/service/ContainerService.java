package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.dockerjava.api.model.Image;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Hardware;
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
    Result<List<Container>> getContainers(ContainerParam containerParam, Integer page, Integer pageSize, Integer userId);
    Result<List<Container>> getContainers(Integer page, Integer pageSize, Integer userId);

    //创建容器
    String createContainer(AddContainer addContainer, long userId, Hardware hardware);

    Container getContainersByIdOrStatus(String containerId, String status);


    //操作容器
    Result operateContainer(String containerId, String status);

    Long createOrder(long userId, Order order) throws ParseException;

    /**
     * 执行指令并返回执行结果
     * @param containerId 容器id
     * @param command 指令
     * @return
     */
    List<String> execCommand(String containerId,String command);

    /**
     * 是否归宿
     * @param id 容器id
     * @param userId
     * @return
     */
    boolean hasContainer(String id,int userId);
}
