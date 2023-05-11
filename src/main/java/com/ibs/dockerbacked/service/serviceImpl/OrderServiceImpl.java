package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.task.*;
import com.ibs.dockerbacked.mapper.HardwareMapper;
import com.ibs.dockerbacked.mapper.OrderMapper;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Autowired
    ContainerService containerService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HardwareMapper hardwareMapper;


    public OrderServiceImpl() {
        init();
    }
    public void init(){
        taskThreads = new ArrayList<>();
        maxThread = 10;
        executor = Executors.newFixedThreadPool(maxThread);//线程池
        maxTasks = 100;
    }

    /**
     * 添加订单任务
     *
     * @param task 任务
     */
    public synchronized void addOrderTask(OrderTask task, Order order) {

        Iterator<TaskThread> iterator = taskThreads.iterator();
        while (iterator.hasNext()) {
            TaskThread t = iterator.next();
            if (t.add(task)) {
                orderToTask.put(order.getId(), task.getId());
                return;
            } else if (!t.isLive()) {
                iterator.remove();
            }
        }
        TaskThread taskThread = new TaskThread(maxTasks);
        taskThread.add(task);
        orderToTask.put(order.getId(), task.getId());
        taskThreads.add(taskThread);
        executor.execute(taskThread);

    }


    @Override
    @PostMapping(value = "/createOrder")
    public String createOrder(int packetId, long userId, AddContainer addContainer) {
        Packet packet = null;//套餐
        Order order = new Order();
        order.setPacketId(packetId);
        order.setUserId(userId);
        order.setState("初始化");
        order.setName("order");
        orderMapper.insert(order);
        //获取硬件信息
        int basePacketId = 1;
        Hardware hardware = packet==null?hardwareMapper.selectById(basePacketId):hardwareMapper.selectById(packet.getHardwareId());

        OrderTask baseTask = new OrderTask(120,order){
            @Override
            public synchronized void recall() {
                super.recall();
                System.out.println("test to create");
                String containerId = containerService.createContainer(addContainer, userId,hardware);
                if(containerId!=null){
                    order.setContainerId(containerId);
                    order.setState("支付成功!");
                }
                else{
                    order.setState("创建容器失败!");
                }
                orderMapper.updateById(order);
                setStatus(TaskStatus.DEATH);
            }
            @Override
            public synchronized void run() {
                super.run();
                System.out.println("daled:"+getTime());
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
        OrderTask orderTask = (OrderTask) getDTaskByOrderId(order.getId());
        setPayedFromTask(orderTask);
    }

    /**
     *  设置订单状态为支付成功
     * @param orderTask
     */
    private void setPayedFromTask(OrderTask orderTask){
        orderTask.setOrderState("支付成功");
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
