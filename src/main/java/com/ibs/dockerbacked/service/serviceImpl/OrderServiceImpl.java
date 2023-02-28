package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.mapper.OrderMapper;
import com.ibs.dockerbacked.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
