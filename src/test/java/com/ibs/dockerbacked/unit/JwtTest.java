package com.ibs.dockerbacked.unit;

import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {
    @Autowired
    UserMapper userMapper;


    @Test
    public void test(){
        User user = userMapper.selectById(1);
        String token = JwtUtil.sign(user.getAccount(),user.getId())+"1";
        if(JwtUtil.verity(token)){
            System.out.println("验证成功!"+"账号:"+JwtUtil.getUserAccount(token)+"id:"+JwtUtil.getUserId(token));
        }
    }


}
