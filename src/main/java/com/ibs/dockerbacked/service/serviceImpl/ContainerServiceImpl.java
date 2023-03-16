package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dockerjava.api.model.PortBinding;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.connection.ContainerModel;
import com.ibs.dockerbacked.connection.ImageModel;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.entity.dto.PullImages;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.ContainerMapper;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.service.ContainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.dockerjava.api.model.Image;
/**
 * @author sn
 */

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
    private ContainerModel containerModel;

    /***
     *@descript 容器
     * @param
     *@return
     *@author
     *@version 1.0
     */
    @Override
    public Result<List<Container>> getContainers(ContainerParam containerParam, Long userId) {
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
        if (account != null) {
            //拿到用户Id
            userId = userMapper.getUserIdByAccount(account);
        }
        Page<Container> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<Container> lambdaQueryWrapper = new LambdaQueryWrapper<>();//条件
        lambdaQueryWrapper.eq(userId != null, Container::getOwnerId, userId); //根据用户找
        lambdaQueryWrapper.eq(containerId != null, Container::getId, containerId); //根据容器Id查找
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
    public synchronized void createContainer(AddContainer addContainer) {
        //用户Id
        int userId = 1234;
        //用户money
        //:todo
        Long userMoney = 100L;
        //用户选择配置的价格 -->需要根据配置来计算价格
        //:todo
        Long userConfigMoney = 200L;
        //判断余额
        if (userMoney < userConfigMoney) {
            throw new CustomExpection(500, "余额不足请充钱");
        }
        //要保证原子性 -->可以通过锁来实现 请求量不大的话
        userMoney = userMoney - userConfigMoney;

        //创建容器  保存到虚拟机中
        List<String> envs = addContainer.getEnv(); //环境
        List<PortBinding> ports = addContainer.getExposedPorts(); //端口
        String imageName = addContainer.getImageName(); //镜像名字
        Container hostConfig = addContainer.getHostConfig(); //容器资料
        containerModel.createContainer(hostConfig.getName(), imageName,
                ports, envs);
        //把容器信息保存到数据库
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
    public Container getContainersByIdOrStatus(String containerId, String status) {
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
     * @param imagesParam
     * @return
     * @version 1.0
     * @author sn
     */
    //获取镜像
    @Override
    public List<Image> getImages(ImagesParam imagesParam) {

        //通过指定的标签获取镜像,默认
        List<Image> images = imageModel.getImages(imagesParam.getLabel());
        int Cunrrentpage = imagesParam.getPageParam().getPage() == null ? 1 : imagesParam.getPageParam().getPage();
        int CunrrentpageSize = imagesParam.getPageParam().getPageSize() == null ? 5 : imagesParam.getPageParam().getPageSize();

        //判断镜像id是否为空
        if (imagesParam.getId() != null) {
            //不为空，通过镜像id获取指定镜像
            List<Image> imageIDList = new ArrayList<>();
            for (Image image : images) {
                if (image.getId().equals(imagesParam.getId())) {
                    imageIDList.add(image);
                    return imageIDList;
                }
            }
            return null;
        }
        //镜像id为空，则返回前几条数据 ，参数数据范围过大则不正确
        if ((Cunrrentpage - 1) * CunrrentpageSize < images.size()) {
            return images.stream().skip((Cunrrentpage - 1) * CunrrentpageSize).limit(CunrrentpageSize).
                    collect(Collectors.toList());
        }
        return null;
    }

    /**
     * @param pullImages
     * @return
     * @derscipt 拉取镜像
     * @author sn
     */
    @Override
    public boolean pullImages(PullImages pullImages) {
        //通过指定的标签获取镜像,默认
        try {
            imageModel.pullImage(pullImages.getName(), pullImages.getTag());
        } catch (InterruptedException e) {
            throw new CustomExpection(Constants.Internal_Server_Error, "拉取失败");
        }
        return true;
    }

}
