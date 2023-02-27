package com.ibs.dockerbacked.data;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 用于存放用户的凭证和账户信息
 */
@Data
public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
