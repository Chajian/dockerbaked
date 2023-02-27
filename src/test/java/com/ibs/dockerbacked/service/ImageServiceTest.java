package com.ibs.dockerbacked.service;

import com.ibs.dockerbacked.server.ImageServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ImageServiceTest {
    @Autowired
    ImageServer imageServer;

    @Test
    public void testInspectImage(){
        imageServer.inspectImage("mysql:8.0.28");
    }

    @Test
    public void testPullImage() throws InterruptedException {
        imageServer.pullImage("mysql","8.0.28");
    }

}
