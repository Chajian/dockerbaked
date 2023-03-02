package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.mapper.ContainerMapper;
import com.ibs.dockerbacked.service.ContainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContainerServiceImpl extends ServiceImpl<ContainerMapper, Container> implements ContainerService {
    /***
    *@descript 容器
    *@param containerId
     * @param List
    *@return
    *@author
    *@version 1.0
    */
    @Override
    public List<Container> getContainersByStatus(Integer containerId, List<String> status) {
        //测试用户
        String userId = "1";
        LambdaQueryWrapper<Container> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Container::getOwnerId, userId);
        lambdaQueryWrapper.eq(containerId != null, Container::getId, containerId);
        lambdaQueryWrapper.in(status != null, Container::getState, status);
        List<Container> containers = list(lambdaQueryWrapper);
        return containers;
    }
}
