package com.ibs.dockerbacked.service.serviceImpl;

import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    @Test
    void createOrder() {
        AddContainer addContainer = new AddContainer();
        addContainer.setImageName("mysql:latest");
        addContainer.setContainerName("mysql");
        List<String> envs = new ArrayList<>();
        List<String> ports = new ArrayList<>();
        envs.add("MYSQL_ROOT_PASSWORD=Aa123456789");
        ports.add("4506:3306");
        addContainer.setPorts(ports);
        addContainer.setEnvs(envs);
        Order order = orderService.createOrder(5,1,addContainer,5);
        orderService.paied(order);
        test();
    }

    public void test(){
        while(true);
    }
}
