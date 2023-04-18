package com.ibs.dockerbacked.service.serviceImpl;

import com.ibs.dockerbacked.entity.Container;
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
        Container container = new Container();
//        container.setImageId("mysql:latest");
        container.setName("dockerSql");
        container.setImageId("mysql:latest");
        addContainer.setHostConfig(container);
        container.setId("123"); //id没得拿
        List<String> envs = new ArrayList<>();
        envs.add("MYSQL_ROOT_PASSWORD=Aa123456789");
        addContainer.setPorts(List.of("3307:3307"));
        orderService.createOrder(3,1,addContainer);
        test();
    }

    public void test(){
        while(true);
    }
}
