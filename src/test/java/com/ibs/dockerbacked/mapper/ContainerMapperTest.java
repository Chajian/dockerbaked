package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.data.ContainerEntity;
import com.ibs.dockerbacked.data.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class ContainerMapperTest {
    @Autowired
    ContainerMapper containerMapper;

    @Test
    public void testSelect(){
        log.info(
                containerMapper.selectOne(new QueryWrapper<ContainerEntity>().lambda().
                        eq(ContainerEntity::getId,0)).toString());
    }

    @Test
    public void testInsert(){
        ContainerEntity containerEntity = new ContainerEntity();
        containerEntity.setUserId(1);
        containerEntity.setImageId("1");
        containerMapper.insert(containerEntity);
    }

    @Test
    public void testSelectMutilple(){
        Map<String,String> map =  new HashMap<>();
        map.put("id","1");
        map.put("user_id","1");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(map);
        ContainerEntity containerEntity = containerMapper.selectOne(queryWrapper);
        log.info(containerEntity.toString());
    }
}
