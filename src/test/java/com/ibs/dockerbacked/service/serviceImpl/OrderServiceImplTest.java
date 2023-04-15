package com.ibs.dockerbacked.service.serviceImpl;

import com.ibs.dockerbacked.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    @Test
    void createOrder() {
        orderService.createOrder(3,1);


    }
}
