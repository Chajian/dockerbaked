package com.ibs.dockerbacked.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.AddOrder;
import com.ibs.dockerbacked.entity.task.*;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.HardwareMapper;
import com.ibs.dockerbacked.mapper.OrderMapper;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.service.OrderService;
import com.ibs.dockerbacked.service.PacketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.ibs.dockerbacked.connection.KafkaModel;

/**
 *
 * @author xyl
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    ExecutorService executor;
    /*总容量等于maxThread✖️maxTasks */
    /*最大线程池容量*/
    private int maxThread;
    /*最大线程任务容量*/
    private int maxTasks;
    List<TaskThread> priorityThreads;
    List<TaskThread> fullThreahds;
    Map<Integer,Long> orderToTask = new HashMap<>();//get Task by OrderId
    @Autowired
    ContainerService containerService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HardwareMapper hardwareMapper;
    @Autowired
    @Lazy
    PacketService packetService;
    @Autowired
    KafkaModel kafkaModel;

    {
        init();
    }
    public void init(){
        fullThreahds = new ArrayList<>();
        priorityThreads = new ArrayList<>();
        maxThread = 10;
        executor = Executors.newFixedThreadPool(maxThread);//线程池
        maxTasks = 1000;

        executor.execute(()->{
            receiveMessage();
        });
    }
    /**
     * 添加订单任务
     *
     * @param task 任务
     */
    public synchronized void addOrderTask(OrderTask task, Order order) {

        Iterator<TaskThread> iterator = priorityThreads.iterator();
        while (iterator.hasNext()) {
            TaskThread t = iterator.next();
            if (t.add(task)) {
                orderToTask.put(order.getId(), task.getId());
                return;
            } else{
                fullThreahds.add(t);
                iterator.remove();
            }
        }
        //checkFullThread
        Iterator<TaskThread> fullIterator = fullThreahds.iterator();
        while(fullIterator.hasNext()){
            TaskThread taskThread = fullIterator.next();
            if(taskThread.getDensity()<0.8){
                priorityThreads.add(taskThread);
                fullIterator.remove();
            }
        }
        TaskThread taskThread = new TaskThread(maxTasks);
        taskThread.add(task);
        orderToTask.put(order.getId(), task.getId());
        priorityThreads.add(taskThread);
        executor.execute(taskThread);

    }

    //生产者生产消息
    public void sendMessage(AddOrder addOrder){

        kafkaModel.getProducer().send(new ProducerRecord<Long,String>("docker-order", addOrder.getUserId(), JSON.toJSONString(addOrder)));
    }

    //消费者消费消息
    public void receiveMessage(){
        while (true) {
            ConsumerRecords<Long, String> records = kafkaModel.getConsumer().poll(Duration.ofMillis(100));
            for (ConsumerRecord<Long, String> record : records) {
                AddOrder addOrder = JSON.parseObject(record.value(),AddOrder.class);
                //创建订单
                createOrder(addOrder.getPacketId(),addOrder.getUserId(),addOrder.getAddContainer(),addOrder.getLifeTime());
//                log.info("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
    }



    public Order createOrder(int packetId, long userId, AddContainer addContainer,int lifeTime) {
        Packet packet = packetService.getById(packetId);//套餐
        Order order = new Order();
        order.setPacketId(packetId);
        order.setUserId(userId);
        order.setState("未支付");
        order.setName("order");
        Hardware hard = hardwareMapper.selectById(packet.getHardwareId());
        if (hard == null) throw new CustomExpection(500,"硬件不存在");

        order.setMoney(hard.getMoney());
        orderMapper.insert(order);
        //获取硬件信息
        int basePacketId = 1;
        Hardware hardware = packet==null?hardwareMapper.selectById(basePacketId):hardwareMapper.selectById(packet.getHardwareId());

        OrderTask baseTask = new OrderTask(lifeTime,order){
            @Override
            public synchronized void recall() {
                super.recall();
                switch (order.getState()){
                    case "支付成功":
                        System.out.println("test to create");
                        String containerId = containerService.createContainer(addContainer, userId, hardware);
                        if (containerId != null) {
                            order.setContainerId(containerId);
                            order.setState("支付成功!");
                        } else {
                            order.setState("创建容器失败!");
                        }
                        orderMapper.updateById(order);
                        break;
                    case "未支付":
                        log.info("创建订单失败！");
                        death();
                }
            }
            @Override
            public synchronized void run() {
                super.run();
                System.out.println("daled:"+getTime());
            }
        };
        addOrderTask(baseTask,order);

        return order;
    }

    @Override
    public String createQrCode(Order order) {
        return null;
    }

    @Override
    public boolean paied(Order order) {
        OrderTask orderTask = (OrderTask) getDTaskByOrderId(order.getId());
        if(orderTask==null||orderTask.getStatus()!=TaskStatus.RUNNING)
            return false;
        setPayedFromTask(orderTask);
        return true;
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
        for(TaskThread t:priorityThreads){
            DTask task = t.getDTaskById(taskId);
            if(task!=null) return task;
        }
        for(TaskThread t:fullThreahds){
            DTask task = t.getDTaskById(taskId);
            if(task!=null) return task;
        }
        return null;
    }
}
