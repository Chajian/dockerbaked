package com.ibs.dockerbacked.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.dto.UserDto;
import com.ibs.dockerbacked.entity.vo.LoginResult;
import com.ibs.dockerbacked.service.UserSerivce;
import com.ibs.dockerbacked.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author sn
 * @version 1.0
 * @descript 验证接口
 * 不需要进行身份验证
 * @date 2023/3/2 21:57
 */
@RestController
@RequestMapping("/ibs/api/verify")
public class VerifyController {
    @Autowired
    private UserSerivce userSerivce;

    /**
     * @descript用户的注册
     * @param user
     * @return user
     * @version 1.0
     * @author sn
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserDto user) {
        return Result.success(Constants.CODE_200, userSerivce.userRegist(user));
    }
    /***
     *@descript 用户的登录
     *@param user *
     *@return  String
     *@author sn
     *@version 1.0
     */
    @PostMapping("/login")
    public Result userLogin(@RequestBody UserDto user) {
        String userLoginToken = userSerivce.userLogin(user);

        LoginResult loginResult = userSerivce.getUserLoginInfo(user.getAccount());
        User userEntity = userSerivce.getOne(new QueryWrapper<User>().eq("account",user.getAccount()));

        loginResult.setUserName(userEntity.getAccount());
        loginResult.setAvatar(userEntity.getAvatar());
        loginResult.setToken(userLoginToken);

        return Result.success(Constants.CODE_200, loginResult);
    }




}
