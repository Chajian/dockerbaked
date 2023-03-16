package com.ibs.dockerbacked.unit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.service.UserSerivce;
import com.ibs.dockerbacked.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author sn
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {
    @Autowired
    UserSerivce userSerivce;

    @Autowired
    UserMapper userMapper;

    @Test
    public void testUserall() {
        System.out.println(userSerivce.list());
    }


    @Test
    public void testInsert() {
        User user = new User();
        user.setAccount("1000");
        user.setPwd("12345");
        userSerivce.save(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(3);
        user.setPwd("123");
        userSerivce.updateById(user);

    }

    @Test
    public void testDelete() {
        userSerivce.removeById(2);
    }

    /**
     * 通过javaAPi执行Where子句
     */
    @Test
    public void selectUserByAccount(){
        User user = userSerivce.getBaseMapper().selectOne(new QueryWrapper<User>()
                .eq("account","10001"));
        EntityUtils.print(user);
    }


}

