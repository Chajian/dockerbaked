package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dockerjava.api.model.SearchItem;
import com.ibs.dockerbacked.connection.DockerConnection;
import com.ibs.dockerbacked.connection.ImageModel;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.ContainerMapper;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.service.ContainerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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


@Slf4j
@Service
public class ContainerServiceImpl extends ServiceImpl<ContainerMapper, Container> implements ContainerService {
    @Autowired
    private UserMapper userMapper;

    private ImageModel imageModel;

    /***
     *@descript 容器
     * @param
     *@return
     *@author
     *@version 1.0
     */
    @Override
    public List<Container> getContainers(ContainerParam containerParam, Long userId) {
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

    /**
     * @param imagesParam
     * @return
     * @version 1.0
     * @author sn
     */
    //获取镜像
    @Override
    public List<Image> getImages(ImagesParam imagesParam) {
        //测试用户数据
        DockerConnection dockerConnection = new DockerConnection
                ("dockerxylyjy", "docker@123789", "xylyjy@gmail.com",
                        "npipe:////./pipe/docker_engine", "https://index.docker.io/v1/");
        //获取镜像对象
        imageModel = new ImageModel(dockerConnection.connect());
        //通过指定的标签获取镜像,默认
        List<Image> images = imageModel.getImages(imagesParam.getLabel());

        //如果传来了镜像名称搜索则返回此镜像，通过镜像名称获取id
        List<Image> imageNameList = null;
        if (StringUtils.isNotEmpty(imagesParam.getId())) {
            List<SearchItem> searchItems = imageModel.searchImage(imagesParam.getId(), imagesParam.getSize() == null ? 5 : imagesParam.getSize());

            for (SearchItem searchItem : searchItems) {
                //获取镜像名称
                String nameImage = searchItem.getName();
                imageNameList = imageModel.getImages(nameImage);
                break;
            }
            //返回此列表前5个
            return imageNameList.subList(0, 6);
        }
        return images;
    }

}
