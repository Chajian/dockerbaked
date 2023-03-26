package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.task.DTask;
import com.ibs.dockerbacked.entity.task.TaskThread;
import com.ibs.dockerbacked.mapper.OrderMapper;
import com.ibs.dockerbacked.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sn
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    ExecutorService executor;
    private int maxThread;
    TaskThread taskThread;

    public OrderServiceImpl() {
        init();
    }
    public void init(){
        executor = Executors.newFixedThreadPool(10);//线程池
        maxThread = 10;
        taskThread = new TaskThread(20);
    }





}
