package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.UserSerivce;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户的登录测试
 *
 * @author sn
 */
@SpringBootTest
@Slf4j
class UserSerivceImplTest {

    @Autowired
    private UserSerivce userSerivce;

    //登录测试
    @Test
    void userLogin() {
        User user = new User();
        user.setAccount("1000");
        user.setPwd("12");
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        User one = userSerivce.getOne(userLambdaQueryWrapper);
        if (one == null) log.info("用户不存在");
        if (!one.getPwd().equals(user.getPwd())) log.info("密码错误");
        log.info("登录成功", one.toString());

    }


}