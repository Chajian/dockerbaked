package com.ibs.dockerbacked.service.serviceImpl;

import com.ibs.dockerbacked.service.ContainerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContainerServiceImplTest {
    @Autowired
    private ContainerService containerService;

    //查询容器
    @Test
    void getContainersByStatus() {
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
//        System.out.println(containerService.getContainersByStatus(null, list));

    }
}