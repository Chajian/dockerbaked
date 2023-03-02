package com.ibs.dockerbacked.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.entity.Permission;
import com.ibs.dockerbacked.entity.PermissionGroup;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.UserPermissionGroup;
import com.ibs.dockerbacked.mapper.*;
import com.ibs.dockerbacked.util.JwtUtil;
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
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 基于shiro实现的授权过滤取
 * @author yezi
 * @date 2020/9/14
 */
@Component
@Slf4j
public class AuthorizeFilterShiro extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionGroupMapper permissionGroupMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserPermissionGroupMapper userPermissionGroupMapper;

    @Autowired
    private PermissionGroupPermissionMapper permissionGroupPermissionMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("[身份授权]");
        String token = principalCollection.toString();
        //解密或得username
        String account = JwtUtil.getUserAccount(token);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        List<Permission> permissions = permissionMapper.getPermissionsByUserAccount(account);

        //存放会员拥有的权限
        Set<String> permissionSet = new HashSet<>();
        //存放会员拥有的角色
        Set<String> roleSet = new HashSet<>();
        permissions.stream().forEach(
                e->{
                    //添加会员的权限
                    permissionSet.add(e.getName());
                }
        );

        simpleAuthorizationInfo.setRoles(roleSet);
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("[身份认证]");

        //检测用户信息是否存在，并且尝试通过token验证
        String token = (String) authenticationToken.getCredentials();
        String account = JwtUtil.getUserAccount(token);
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("account",account));
        log.info(account+":"+user.toString());
        if(account == null || !JwtUtil.verity(token,user)){
            log.info("身份认证失败  token和 username");
            throw new AuthenticationException("身份认证失败");
        }
        if(user== null ){
            log.info("用户不存在");
            throw new AuthenticationException("该用户不存在");
        }
        return new SimpleAuthenticationInfo(token,token,"TokenRealm");
    }
}
