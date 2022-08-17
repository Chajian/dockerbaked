package com.ibs.dockerbacked.service;

import com.ibs.dockerbacked.server.ContainerServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ContainerServiceTest {
    @Autowired
    ContainerServer containerServer;

    @Test
    public void testInspect(){
        containerServer.inspectContainer("6ed1c71a85ff025f533d4a896b905e63043712c58d675e64ad494751f5589f68");
    }

    @Test
    public void startContainer(){
        containerServer.startContainer("6ed1c71a85ff025f533d4a896b905e63043712c58d675e64ad494751f5589f68");
    }

}
