package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.config.JWTToken;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.dto.UserDto;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.service.UserSerivce;
import com.ibs.dockerbacked.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sn
 */

@Slf4j
@Service
public class UserSerivceImpl extends ServiceImpl<UserMapper, User> implements UserSerivce {


    /**
     * 用户的注册
     *
     * @param user
     * @return
     * @author sn
     */
    @Override
    public User userRegist(User user) {

        //1.user为空抛出异常
        if (user == null) throw new CustomExpection(Constants.CODE_400, "添加的用户数据不正确");
        //2.用户账号存在抛异常
        User one = getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, user.getAccount()));
        //2.1判断用户是否为null,是则抛异常
        if (one != null) throw new CustomExpection(Constants.CODE_400, "用户已存在");
        //2.2否则注册
        save(user);
        return user;
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     * @author sn
     */
    @Override
    public String userlogin(User user) {
        //1.判断user是否为空
        if (user == null) throw new CustomExpection(Constants.CODE_400, "用户为空");
        //2.判断用户是否存在数据库
        //2.1获取用户数据
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccount, user.getAccount()).eq(User::getPwd, user.getPwd());
        User one = getOne(userLambdaQueryWrapper);
        //为null,拦截
        if (one == null) throw new CustomExpection(Constants.CODE_400, "用户不存在");
        //3.登录成功
        //3.1获取token
        String token = JwtUtil.sign(one.getAccount());
        return token;
    }
}
