package com.ibs.dockerbacked.controller;


import cn.hutool.core.lang.hash.Hash;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.dto.UserDto;
import com.ibs.dockerbacked.service.UserSerivce;
import com.ibs.dockerbacked.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @author sn
 * @version 1.0
 * @descript 用户接口
 * @date 2023/3/2 21:57
 */
@RestController
@RequestMapping("/ibs/api/verify")
public class UserController {
    @Autowired
    private UserSerivce userSerivce;

    @PostMapping("register")
    public Result register(@RequestBody UserDto user) {
        return Result.success(Constants.CODE_200, "新增成功", userSerivce.userRegist(user));
    }

    @GetMapping("/login")
    public Result<String> userLogin(@RequestParam("account") String account,@RequestParam("pwd") String pwd) {

        Map<String,String> map = new HashMap<>();
        map.put("account",account);
        map.put("pwd",pwd);
        User user = userSerivce.getOne(new QueryWrapper<User>().allEq(map));
        String token = JwtUtil.sign(user.getAccount());
        return Result.success(Constants.CODE_200, "登录成功", token);
    }

//    批量生产账号
    @GetMapping("/batch/{count}/{token}")
    public Boolean batchGenerationUser(@PathVariable("count") int count,
                                              @PathVariable String token) {
        boolean isSuccess = userSerivce.batchGenerationUser(count, token);
        return isSuccess;
    }


}
