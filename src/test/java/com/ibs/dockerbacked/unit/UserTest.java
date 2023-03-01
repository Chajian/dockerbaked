package com.ibs.dockerbacked.unit;

import com.ibs.dockerbacked.entity.Users;
import com.ibs.dockerbacked.service.UserSerivce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    UserSerivce userSerivce;

    @Test
    public void testUserall() {
        System.out.println(userSerivce.list());
    }


    @Test
    public void testInsert() {
        Users user = new Users();
        user.setAccount("1000");
        user.setPwd("1234");
        userSerivce.save(user);
    }

    @Test
    public void testUpdate() {
        Users user = new Users();
        user.setId(3);
        user.setPwd("123");
        userSerivce.updateById(user);

    }

    @Test
    public void testDelete() {
        userSerivce.removeById(2);

    }
}

