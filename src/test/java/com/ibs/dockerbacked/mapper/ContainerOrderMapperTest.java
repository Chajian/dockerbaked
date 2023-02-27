package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.data.ContainerOrderEntity;
import com.ibs.dockerbacked.data.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ContainerOrderMapperTest {
    @Autowired
    ContainerOrderMapper containerOrderMapper;

    @Test
    public void testSelect(){
        log.info(
                containerOrderMapper.selectOne(new QueryWrapper<ContainerOrderEntity>().lambda().
                        eq(ContainerOrderEntity::getId,"1")).toString());
    }

    @Test
    public void testInsert(){
        ContainerOrderEntity containerOrderEntity = new ContainerOrderEntity();
        containerOrderEntity.setCost(1);
        containerOrderEntity.setNetwork(1);
        containerOrderEntity.setCpu(1);
        containerOrderEntity.setDisk(1);
        containerOrderEntity.setUserId(1);
        containerOrderEntity.setContainerId("123asd");
        containerOrderMapper.insert(containerOrderEntity);
    }
}
