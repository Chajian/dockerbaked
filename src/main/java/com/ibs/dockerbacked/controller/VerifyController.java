package com.ibs.dockerbacked.controller;


import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.dto.UserDto;
import com.ibs.dockerbacked.service.UserSerivce;
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
    public Result<String> userLogin(@RequestBody UserDto user) {
        String userLoginToken = userSerivce.userLogin(user);
        return Result.success(Constants.CODE_200, userLoginToken);
    }




}
