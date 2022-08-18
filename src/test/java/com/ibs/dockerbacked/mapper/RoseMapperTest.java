package com.ibs.dockerbacked.mapper;

import com.ibs.dockerbacked.data.RoseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoseMapperTest {
    @Autowired
    RoseMapper roseMapper;

    @Test
    public void testInsert(){
        RoseEntity roseEntity = new RoseEntity();
        roseEntity.setName("游客");
        roseMapper.insert(roseEntity);
    }

}
