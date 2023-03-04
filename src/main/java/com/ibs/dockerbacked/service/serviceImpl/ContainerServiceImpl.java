package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.entity.dto.PageParam;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.ContainerMapper;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.service.ContainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sn
 */
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class ContainerServiceImpl extends ServiceImpl<ContainerMapper, Container> implements ContainerService {
    @Autowired
    private UserMapper userMapper;
    /***
     *@descript 容器
     *@param c
     * @param
     *@return
     *@author
     *@version 1.0
     */
    @Override
    public List<Container> getContainers(ContainerParam containerParam,Long userId) {
        //测试用户
        Integer page = containerParam.getPageParam().getPage() == null ? 1 : containerParam.getPageParam().getPage(); //页数  没传页数 默认第一
        Integer pageSize = containerParam.getPageParam().getPageSize() == null ? 5 : containerParam.getPageParam().getPageSize();//页大小 默认5条每页
        //状态
        List<String> status = containerParam.getStatus();
        //容器Id
        Long containerId = containerParam.getContainerId();
        //用户名
        String account = containerParam.getAccount();
        //主要是管理员可以根据用户名查询容器用的
        if(account!=null) {
            //拿到用户Id
            userId  = userMapper.getUserIdByAccount(account);
        }
        Page<Container> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<Container> lambdaQueryWrapper = new LambdaQueryWrapper<>();//条件
        lambdaQueryWrapper.eq(userId!=null,Container::getOwnerId, userId); //根据用户找
        lambdaQueryWrapper.eq(containerId != null, Container::getId, containerId); //根据容器Id查找
        lambdaQueryWrapper.in(status != null, Container::getState, status); //根据状态找
        Page<Container> pageResult = page(p, lambdaQueryWrapper);
        List<Container> containers = pageResult.getRecords();
        if (pageResult.getSize() <= 0) {
            return null;
        }
        return containers;
    }

    //创建容器
    @Transactional
    @Override
    public void createContainer(AddContainer addContainer) {
        //用户Id
        int userId = 1234;
        //用户money
        Long userMoney = 100L;
        //用户选择配置的价格 -->需要根据配置来计算价格
        Long userConfigMoney = 200L;
        //判断余额
        if (userMoney < userConfigMoney) {
            throw new CustomExpection(500, "余额不足请充钱");
        }
        //要保证原子性 -->可以通过锁来实现 请求量不大的话
        userMoney = userMoney - userConfigMoney;
        //创建容器
        Container hostConfig = addContainer.getHostConfig(); //容器资料
        Container container = new Container();
        BeanUtils.copyProperties(hostConfig, container);
        container.setOwnerId(userId);
        container.setImageId("1234");
        container.setCreatedAt(new Date());
        boolean save = save(container);
        if (!save) {
            throw new CustomExpection(500, "创建容器失败");
        }
    }
    //管理员接口
    @Override
    public Container getContainersByIdOrStatus(Long containerId, String status) {
        LambdaQueryWrapper<Container> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Container::getId, containerId); //根据Id找
        lambdaQueryWrapper.eq(Container::getState, status);  //根据状态找
        Container container = getOne(lambdaQueryWrapper);
        if (container == null) {
            return null;
        }
        return container;
    }

    //获取镜像
    @Override
    public List<Container> getImages(ImagesParam imagesParam) {
        Integer page = imagesParam.getPageParam().getPage() == null ? 1 : imagesParam.getPageParam().getPage(); //页数  没传页数 默认第一
        Integer pageSize = imagesParam.getPageParam().getPageSize() == null ? 5 : imagesParam.getPageParam().getPageSize();//页大小 默认5条每页

        return null;
    }

}
