package com.ibs.dockerbacked.entity.vo;

import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录接口返回信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    User user;
    Wallet wallet;
    String token;
}
