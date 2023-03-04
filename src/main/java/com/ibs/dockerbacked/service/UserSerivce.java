package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.dto.UserDto;

/**
 * 用户模块
 *
 * @author sn
 */


public interface UserSerivce extends IService<User> {
    //用户注册
    UserDto userRegist(UserDto user);

    //用户登录
    String userLogin(UserDto user);

    //批量生产账号
    boolean batchGenerationUser(int count, String token);
}
