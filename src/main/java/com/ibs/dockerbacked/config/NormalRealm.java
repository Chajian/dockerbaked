package com.ibs.dockerbacked.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.data.JWTToken;
import com.ibs.dockerbacked.data.RoleEntity;
import com.ibs.dockerbacked.data.UserEntity;
import com.ibs.dockerbacked.mapper.RoseMapper;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * 普通的Reaml 授权,身份验证
 * @author YangLin
 * @date 2021/3/23
 */
@Slf4j
public class NormalRealm extends AuthorizingRealm {

    @Autowired
    RoseMapper roleUserMapper;

    @Autowired
    UserMapper mapper;

    /**
     * 用户鉴权(授权)
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info(this.getClass().toString()+"鉴权开始咯!"+principalCollection.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取用户通过token
        String account = JwtUtils.getAccount(principalCollection.toString());
        long roleId = mapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount,account)).getRoleId();


        //角色处理
        RoleEntity roleUsers = roleUserMapper.selectOne(new QueryWrapper<RoleEntity>().lambda().eq(RoleEntity::getId,roleId));
        simpleAuthorizationInfo.addRole(roleUsers.getName());
        log.info(this.getClass().toString()+" : "+roleUsers.getName());
        //默认用户都拥有VISITER身份


        return simpleAuthorizationInfo;

    }

    /**
     * 判断传递过来的token对象是否正确
     * @param token JWTToken对象
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 用户身份验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String account = JwtUtils.getAccount(token);
        if (account == null || account.equals("")){
            throw new AuthenticationException("token invalid");
        }
        UserEntity baseInfo = mapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount,account));
        if (baseInfo == null)
            throw new AuthenticationException("用户不存在");

        if (!JwtUtils.verify(token))
            throw new AuthenticationException("学号和token不匹配");


        return new SimpleAuthenticationInfo(token,token,"normalRealm");
    }
}
