package com.ibs.dockerbacked.controller;


import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.dto.UserDto;
import com.ibs.dockerbacked.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author sn
 * @version 1.0
 * @descript 用户接口
 * @date 2023/3/2 21:57
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserSerivce userSerivce;

    @PostMapping("register")
    public Result register(@RequestBody UserDto user) {
        return Result.success(Constants.CODE_200, "新增成功", userSerivce.userRegist(user));
    }

    @PostMapping("/login")
    public Result<String> userLogin(@RequestBody UserDto user) {
        String userLoginToken = userSerivce.userLogin(user);
        return Result.success(Constants.CODE_200, "登录成功", userLoginToken);
    }

//    批量生产账号
    @GetMapping("/batch/{count}/{token}")
    public Boolean batchGenerationUser(@PathVariable("count") int count,
                                              @PathVariable String token) {
        boolean isSuccess = userSerivce.batchGenerationUser(count, token);
        return isSuccess;
    }


}
