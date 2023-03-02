package com.ibs.dockerbacked.unit;


import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sn
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void orderAll() {
        System.out.println(orderService.getById(1));
    }

    @Test
    public void orderInsert() {
        Order order = new Order();
        order.setContainerId(0);
        order.setMoney(1000);
        order.setName("1");
        order.setPacketId(3);
        order.setPayWay("1");
        order.setUserId(1);
        orderService.save(order);
    }

    @Test
    public void orderUpdate() {
        Order order = new Order();
        order.setId(2);
        order.setContainerId(0);
        order.setPacketId(3);
        order.setUserId(4);
        orderService.saveOrUpdate(order);
    }

    @Test
    public void orderRemove() {
        orderService.removeById(2);
    }
}
