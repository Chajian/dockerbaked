package com.ibs.dockerbacked.mapper;

import com.ibs.dockerbacked.entity.Hardware;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
//@SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class HarewardMapperTest {

    @Autowired
    HardwareMapper hardwareMapper;


    @Test
    public void getHardWareByContainer(){
        Hardware hardware = hardwareMapper.getHardwareByContainer("023cd63d9b97d603cb45539c5391d5b5654a7464bfd2be1f8c3c5c925564899c");
        System.out.println(hardware.toString());
    }
}
