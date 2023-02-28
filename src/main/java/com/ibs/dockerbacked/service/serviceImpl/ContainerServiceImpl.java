package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.mapper.ContainerMapper;
import com.ibs.dockerbacked.service.ContainerService;
import org.springframework.stereotype.Service;

@Service
public class ContainerServiceImpl extends ServiceImpl<ContainerMapper, Container> implements ContainerService {
}
