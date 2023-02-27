package com.ibs.dockerbacked.control;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.data.UserEntity;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.util.JwtUtils;
import com.ibs.dockerbacked.util.RSAUtils;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ibs.dockerbacked.data.ResponseBody;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@RestController
@RequestMapping("/ibs/verify")
@CrossOrigin(origins = "*")
public class VerifyControl {

    @Autowired
    UserMapper userMapper;

//    @PostMapping()
//    public ResponseBody getLeagueRequests(@RequestParam String account, @RequestParam String pass){
//        return null;
//    }

    @GetMapping(value = "key")
    public ResponseBody getPublicKey() throws NoSuchAlgorithmException {
        RSAUtils.KeyStore keyStore = RSAUtils.createKeys();
        return ResponseBody.SUCCESS(RSAUtils.getPublicKey(keyStore),"成功啦");
    }

    @PostMapping(value = "login")
    public ResponseBody login(String account,String password) throws Exception {
        byte[] encryptByPrivate = RSAUtils.decryptByPrivateKey(Base64.decode(password),RSAUtils.getPrivateKey(RSAUtils.createKeys()));
        String realPass = new String(encryptByPrivate);
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount,account));
        if(userEntity==null)
            return ResponseBody.Fail("账号或密码不存在!");
        else if(!userEntity.getPass().equals(realPass)){
            return ResponseBody.Fail("账号或密码错误!");
        }
        else if(userEntity.getPass().equals(realPass)&&account.equals(userEntity.getAccount())){
            String token = JwtUtils.sign(userEntity.getAccount());
            return ResponseBody.SUCCESS(token,"登录成功!");
        }
        return ResponseBody.Fail("error from client");
    }
}
