package com.ibs.dockerbacked.unit;

import com.ibs.dockerbacked.service.FileService;
import com.ibs.dockerbacked.service.SpaceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileTest {

    @Autowired
    SpaceService spaceService;

    @Test
    public void testCreateContainerFolder(){
        spaceService.createContainerSpace("Zekk","containeraaa");
    }

    @Test
    public void testCreateImageFolder(){
        spaceService.createImageSpace("Zekk","imageaa");
    }

    @Test
    public void createUserSpace(){
        System.out.println(spaceService.getUserSpace("Zekk"));
        spaceService.createUserSpace("Zekk");
    }

}
