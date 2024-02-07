package com.ibs.dockerbacked.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.dto.UserParam;
import com.ibs.dockerbacked.service.UserSerivce;
import com.ibs.dockerbacked.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户接口
 * @author Yanglin
 */
@RestController("/ibs/api/user")
public class UserController {

    @Autowired
    UserSerivce userSerivce;


    /**
     * update user info
     * @return
     */
    @PostMapping("/update")
    public Result updateUser(@RequestBody UserParam userParam){
        //TODO update user info



        return null;
    }

    /**
     * get user info only by own
     * @return
     */
    @GetMapping()
    public Result getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        String userName = JwtUtil.getUserAccount(token);
        User user = userSerivce.getOne(new QueryWrapper<User>().eq("account",userName));
        return Result.success(Constants.CODE_200,user);

    }

    /**
     * update user avatar
     * @return
     */
    @PostMapping("upavatar")
    public Result updateAvatar(@RequestParam("file") MultipartFile avatar,@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        String account = JwtUtil.getUserAccount(token);
        userSerivce.updateAvatar(avatar,account);
        return Result.success(Constants.CODE_200);
    }

}
