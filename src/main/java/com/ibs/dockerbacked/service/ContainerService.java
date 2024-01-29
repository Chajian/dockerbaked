package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Image;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.connection.DashboardResultCallback;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.entity.dto.PullImages;
import com.ibs.dockerbacked.entity.vo.Dashboard;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


/**
 * @author sn
 */
import java.text.ParseException;
import java.util.List;

public interface ContainerService extends IService<Container> {
    //查询容器
    Result<List<Container>> getContainers(ContainerParam containerParam, Integer page, Integer pageSize, Integer userId);
    Result<List<Container>> getContainers(Integer page, Integer pageSize, Integer userId);

    //管理员接口
    Result<List<Container>> getContainers(Integer page, Integer pageSize);

    //创建容器
    String createContainer(AddContainer addContainer, long userId, Hardware hardware);

    Container getContainersByIdOrStatus(String containerId, String status);

    Container getContainerById(String containerId);

    //操作容器
    Result operateContainer(String containerId, String status);

    Long createOrder(long userId, Order order) throws ParseException;

    /**
     * 执行指令并返回执行结果
     * @param containerId 容器id
     * @param command 指令
     * @return
     */
    String execCommand(String containerId,String command,String location);

    /**
     * 查询操作容器是否归属User
     * @param id 容器id
     * @param userId
     * @return id不存在返回false||OwnerId！=userId也返回false
     */
    boolean hasContainer(String id,int userId);

    /**
     * 获取容器的dashboard
     * @param id 容器id
     * @return
     */
    ResultCallback getDashboard(String id);

    /**
     * 关闭dashboard
     * @param dashboardResultCallback
     */
    void closeDashboard(DashboardResultCallback dashboardResultCallback);
}
