package com.ibs.dockerbacked.service.serviceImpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.connection.ContainerModel;
import com.ibs.dockerbacked.connection.ImageModel;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.entity.dto.PullImages;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.ContainerMapper;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.service.OrderService;
import com.ibs.dockerbacked.service.PacketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.dockerjava.api.model.Image;
/**
 * @author sn
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ContainerServiceImpl extends ServiceImpl<ContainerMapper, Container> implements ContainerService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ImageModel imageModel;
    @Autowired
    @Lazy
    private OrderService orderService;
    @Autowired
    private PacketService packetService;

    @Autowired
    private ContainerModel containerModel;

    /***
     *@descript 容器列表
     * @param
     *@return
     *@author chen
     *@version 1.0
     */
    @Override
    public Result<List<Container>> getContainers(ContainerParam containerParam, Integer page, Integer pageSize, Long userId) {
        //测试用户
        //状态
        String[] status = containerParam.getStatus();
        //容器Id
        String containerId = containerParam.getContainerId();
        //用户名
        String account = containerParam.getAccount();
        //主要是管理员可以根据用户名查询容器用的
        if (account != null) {
            //拿到用户Id
            userId = userMapper.getUserIdByAccount(account);
        }
        Page<Container> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<Container> lambdaQueryWrapper = new LambdaQueryWrapper<>();//条件
        lambdaQueryWrapper.eq(userId != null && userId != -1, Container::getOwnerId, userId); //根据用户找
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(containerId), Container::getId, containerId); //根据容器Id查找
        lambdaQueryWrapper.in(status != null, Container::getState, status); //根据状态找
        Page<Container> pageResult = page(p, lambdaQueryWrapper);
        List<Container> containers = pageResult.getRecords();
        if (pageResult.getSize() <= 0) {
            return null;
        }
        return Result.success(200, "success", containers);
    }

    //创建容器
    @Transactional
    @Override
    public synchronized String createContainer(AddContainer addContainer,long userId) {
        //用户Id
//        //用户money
//
        long userMoney = userId;
//        //用户选择配置的价格 -->需要根据配置来计算价格
//        //:todo
//        Long userConfigMoney = 200L;
//        //判断余额
//        if (userMoney < userConfigMoney) {
//            throw new CustomExpection(500, "余额不足请充钱");
//        }
//        //要保证原子性 -->可以通过锁来实现 请求量不大的话
//        userMoney = userMoney - userConfigMoney;

        //2.1检查填写的是否为空 envs和imageName
        check(addContainer);

        //环境
        List<String> envs = addContainer.getEnv();
        //镜像名字
        String imageName = addContainer.getImageName();
        //容器资料
        Container hostConfig = addContainer.getHostConfig();
        //容器名字
        String containName = userId+"-"+hostConfig.getName();
        //2.2 创建容器
        CreateContainerResponse createContainerResponse = containerModel.createContainer(containName, imageName,
                    addContainer.generatePorts(), envs);
        String containId = null;
        try {
            containId = createContainerResponse.getId();
            //把容器信息同步到数据库
            Container container = new Container();
            BeanUtils.copyProperties(hostConfig, container);
            container.setOwnerId((int) userId);
            container.setImageName(imageName);
            container.setName(containName);
            container.setCreatedAt(new Date());
            container.setState("start");
            container.setId(containId);
            save(container);
            return containId;
        } catch (Exception e) {
            //创建容器成功了 但是数据库保存出现异常 删除创建容器
            containerModel.deleteContaqqiner(containId);
            throw new CustomExpection(500, "保持数据异常");
        }
    }

    /**
     * author chen
     * @param addContainer 检查参数是否合法
     */
    private void check(AddContainer addContainer) {
        List<String> env = addContainer.getEnv();
        String imageName = addContainer.getImageName();
        if(CollectionUtils.isEmpty(env)){
            throw new CustomExpection(500, "please fill in the envs params");
        }
        if (StringUtils.isEmpty(imageName)) {
            throw new CustomExpection(500, "please fill in the imageName param");
        }
    }

    //管理员接口
    @Override
    public Container getContainersByIdOrStatus(String containerId, String status) {
        if (StringUtils.isEmpty(containerId)){
            throw new CustomExpection(500, "容器id不能为空");
        }
        if (StringUtils.isEmpty(status)){
            throw new CustomExpection(500, "容器状态不能为空");
        }
        LambdaQueryWrapper<Container> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Container::getId, containerId); //根据Id找
        lambdaQueryWrapper.eq(Container::getState, status);  //根据状态找
        Container container = getOne(lambdaQueryWrapper);
        if (container == null) {
            return null;
        }
        return container;
    }

    /**
     *  修改容器状态
     *  author chen
     * @param containerId
     * @param status
     * @return
     */
    @Override
    public Result operateContainer(String containerId, String status) {
        //根据状态来操作容器
        //1.查找这个容器当前状态
        Container container = getById(containerId);
        if (container == null) {
            throw new CustomExpection(500,"当前容器不存在");
        }
        if (container.getState().equals(status)) {
            throw new CustomExpection(500, "已经是当前状态已经不用重复操作");
        }
        switch (status) {
            case "start":
                containerModel.startContainer(containerId);
                break;
            case "stop":
                containerModel.stopContainer(containerId);
                break;
            case "delete":
                containerModel.deleteContaqqiner(containerId);
                break;
            case "restart":
                containerModel.restartContainer(containerId);
                break;
            case "pause":
                containerModel.pauseContainer(containerId);
                break;

        }
        //更新数据库
        container.setState(status);
        container.setUpdatedAt(new Date());
        updateById(container);
        return Result.success(200, "success", "修改成功");
    }

    /**
     * 创建订单
     *
     * @param userId
     * @param order
     * @return
     */
    @Override
    public Long createOrder(long userId, Order order) throws ParseException {

        //创建订单
        if (order == null) throw new CustomExpection(500, "订单不存在");
        Container containerId = query().eq("id", order.getContainerId()).one();
        Packet packetId = packetService.query().eq("id", order.getPacketId()).one();
        order.setDescription(containerId.getDescription() + packetId.getDescription());
        order.setUserId((int)userId);
        boolean save = orderService.save(order);
        if (!save) throw new CustomExpection(500, "创建失败");
        //判断订单是否支付成功 1成功 0失败
        if (Integer.parseInt(order.getPayWay()) == 0) {
            //获取当前时间
            String now = DateUtil.now();
            //获取订单创建时间
            Date createdAt = order.getCreatedAt();
            //转换日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date nowTime = df.parse(now);
            long differTime = nowTime.getTime() - createdAt.getTime();
            int minutes = (int) (differTime / 1000 / 60);

            if (minutes > 15) { //大于15分钟删除表
                removeById(order.getContainerId());
                packetService.removeById(order.getPacketId());
                orderService.removeById(order);
                return Integer.toUnsignedLong(0);
            }
        }

        return Integer.toUnsignedLong(order.getId());
    }

}
