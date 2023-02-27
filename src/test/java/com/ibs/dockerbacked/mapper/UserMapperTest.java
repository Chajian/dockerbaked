package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.data.UserEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testSelect(){
        log.info(
        userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().
                eq(UserEntity::getAccount,"19240112")).toString());
    }

    @Test
    public void testInsert(){
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("19240112");
        userEntity.setPass("123456");
        userEntity.setMoney(99);
        userMapper.insert(userEntity);
    }
}
