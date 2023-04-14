package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.task.BaseTask;
import com.ibs.dockerbacked.entity.task.DTask;
import com.ibs.dockerbacked.entity.task.OrderTask;
import com.ibs.dockerbacked.entity.task.TaskThread;
import com.ibs.dockerbacked.mapper.OrderMapper;
import com.ibs.dockerbacked.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;
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
    private int maxTasks;
    List<TaskThread> taskThreads;
    Map<Integer,Long> orderToTask = new HashMap<>();//get Task by OrderId

    OrderMapper orderMapper;


    public OrderServiceImpl() {
        init();
    }
    public void init(){
        maxThread = 10;
        executor = Executors.newFixedThreadPool(maxThread);//线程池
        maxTasks = 100;
    }

    /**
     * 添加订单任务
     * @param task 任务
     */
    public synchronized void addOrderTask(OrderTask task,Order order) {
        Iterator<TaskThread> iterator = taskThreads.iterator();
        while(iterator.hasNext()){
            TaskThread t = iterator.next();
            if(t.add(task)) {
                orderToTask.put(order.getId(),task.getId());
                return;
            }
            else if(!t.isLive()){
                iterator.remove();
            }
        }
        TaskThread taskThread = new TaskThread(maxTasks);
        taskThread.add(task);
        orderToTask.put(order.getId(),task.getId());
        taskThreads.add(taskThread);
        executor.execute(taskThread);

    }


    @Override
    @PostMapping(value = "/createOrder")
    public String createOrder(int packetId,long userId) {
        Order order = new Order();
        order.setPacketId(packetId);
        order.setUserId(userId);
        order.setName("order");
        orderMapper.insert(order);


        OrderTask baseTask = new OrderTask(900,order){
            @Override
            public synchronized void recall() {
                super.recall();
            }
        };



        addOrderTask(baseTask,order);
        return "";
    }

    @Override
    public String createQrCode(Order order) {
        return null;
    }

    @Override
    public void Paied(Order order) {

    }

    /**
     * 通过orderId获取对应的Task
     * @param orderId
     * @return
     */
    public DTask getDTaskByOrderId(int orderId){
        long taskId = orderToTask.get(orderId);
        for(TaskThread t:taskThreads){
            DTask task = t.getDTaskById(taskId);
            if(task!=null) return task;
        }
        return null;
    }
}
