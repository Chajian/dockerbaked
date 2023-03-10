package com.ibs.dockerbacked.service.serviceImpl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.UserSerivce;
import com.ibs.dockerbacked.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        user.setAccount("10002");
        user.setPwd("1234");
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        User one = userSerivce.getOne(userLambdaQueryWrapper);
        if (one == null) log.info("用户不存在");
        if (!one.getPwd().equals(user.getPwd())) log.info("密码错误");
        log.info("登录成功", one.toString());

    }

    /**
     * id自增失效，使用UUID
     */
    @Test
    void batchGenerationUser() {
        String sign = JwtUtil.sign("1000");
        String token = sign;
        if (!sign.equals(token)) {
            log.info("token不正确");
            return;
        }
        //随机生成账号和密码
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            User user = new User();
            String substring = IdUtil.simpleUUID().substring(0, 7);
            String substring1 = IdUtil.simpleUUID().substring(0, 7);

            user.setAccount(substring);
            user.setPwd(substring1);
            userList.add(user);
        }
        //保存
        boolean batch = userSerivce.saveBatch(userList);
        if (!batch) log.info( "批量注册失败");


    }

}