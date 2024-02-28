package com.ibs.dockerbacked.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.config.JWTToken;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.Wallet;
import com.ibs.dockerbacked.entity.dto.UserDto;
import com.ibs.dockerbacked.entity.vo.LoginResult;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.mapper.WalletMapper;
import com.ibs.dockerbacked.service.FileService;
import com.ibs.dockerbacked.service.SpaceService;
import com.ibs.dockerbacked.service.UserSerivce;
import com.ibs.dockerbacked.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sn
 */

@Slf4j
@Service
public class UserSerivceImpl extends ServiceImpl<UserMapper, User> implements UserSerivce {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private FileService  fileService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WalletMapper walletMapper;

    /**
     * 用户的注册
     *
     * @param user
     * @return
     * @author sn
     */
    @Override
    public UserDto userRegist(UserDto user) {

        //1.user为空抛出异常
        if (user.getAccount() == null || user.getAccount() == "" || user.getPwd() == null || user.getPwd() == "")
            throw new CustomExpection(Constants.CODE_400.getCode(), "添加的用户数据不正确");
        //2.用户账号存在抛异常
        User one = getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, user.getAccount()));
        //2.1判断用户是否为null,是则抛异常
        if (one != null) throw new CustomExpection(Constants.CODE_400.getCode(), "用户已存在");
        //2.2否则注册
        one = new User();
        BeanUtil.copyProperties(user, one);
        save(one);
        Wallet wallet = new Wallet();
        wallet.setBalance(0);
        wallet.setUserId(one.getId());
        walletMapper.insert(wallet);
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
    public String userLogin(UserDto user) {
        //1.判断user是否为空
        if (user == null) throw new CustomExpection(Constants.CODE_Login_500.getCode(), "用户数据为空");
        //2.判断用户是否存在数据库
        //2.1获取用户数据
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        User one = getOne(userLambdaQueryWrapper);
        //为null,拦截
        if (one == null) throw new CustomExpection(Constants.CODE_Login_500.getCode(), "账号不存在");
        if (!one.getPwd().equals(user.getPwd())) throw new CustomExpection(Constants.CODE_Login_500.getCode(), "密码错误");
        //3.登录成功
        //3.1生成token并返回
        String token = JwtUtil.sign(one.getAccount(), one.getId());
        return token;
    }

    /**
     * @param count 批量生成账号的个数
     * @param token 管理员的token
     * @return
     */
    @Override
    public boolean batchGenerationUser(int count, String token) {

        //判断是否为管理员

        //随机生成账号和密码
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setAccount(IdUtil.simpleUUID().substring(0, 7));
            user.setPwd(IdUtil.simpleUUID().substring(0, 7));
            userList.add(user);
        }
        //保存
        boolean batch = saveBatch(userList);
        if (!batch) throw new CustomExpection(Constants.CODE_BatchREgister_501.getCode(), "批量注册失败");
        return batch;

    }

    @Override
    public boolean updateAvatar(MultipartFile file, String account) {
        String userAvatarPath = spaceService.getUserAvatarPath();
        try {
            fileService.saveFile(file.getBytes(),file.getOriginalFilename(),userAvatarPath);
        } catch (IOException e) {
            throw new CustomExpection(Constants.FILE_WRITE_FAIL);
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("account",account));
        user.setAvatar(userAvatarPath+ File.separator+file.getName());
        userMapper.updateById(user);
        return true;
    }

    @Override
    public LoginResult getUserLoginInfo(String account) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("account",account));
        Wallet wallet = walletMapper.selectOne(new QueryWrapper<Wallet>().eq("user_id",user.getId()));
        LoginResult loginResult = new LoginResult();
        loginResult.setUser(user);
        loginResult.setWallet(wallet);

        return loginResult;
    }
}
