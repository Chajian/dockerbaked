package com.ibs.dockerbacked.mapper;

import com.ibs.dockerbacked.data.RoleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoseMapperTest {
    @Autowired
    RoseMapper roseMapper;

    @Test
    public void testInsert(){
        RoleEntity roseEntity = new RoleEntity();
        roseEntity.setName("管理员");
        roseMapper.insert(roseEntity);
    }

}
