package com.ww.base.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class CustomRealm extends AuthorizingRealm {
    @Override
    //AuthenticationToken是主体Subject传过来的用户认证信息
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体Subject传过来的用户认证请求信息中，获取用户名
        String userName = (String) authenticationToken.getPrincipal();
        //2.通过用户名，到模拟数据库或缓存中获取该用户密码凭证
        String password = getPasswordByUserName(userName);
        if(password == null){
            return null;
        }
        //3.将用户名userName和从模拟数据库中获取的密码凭证password，放入SimpleAuthenticationInfo
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userName, password,"customRealm");
        //authenticationInfo将密码凭证加盐setCredentialsSalt
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
        return authenticationInfo;
    }

    //模拟Dao层从数据库或缓存中通过用户获取密码
    private String getPasswordByUserName(String userName){
        Map<String, String> userMap = new HashMap<String, String>();
        //数据库或缓存中存储的密码是123456的MD5加密+salt盐username后的值
        userMap.put("wwcustom","fd07753a3caf44588959f81ede2e2d7f");
        super.setName("customRealm");
        return userMap.get(userName);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.从已经通过认证的PrincipalCollection获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //2.通过用户名，到模拟数据库或缓存中获取该用户角色信息
        Set<String> roles = getRolesByUserName(userName);
        //3.通过用户名，到模拟数据库或缓存中获取该用户权限信息
        Set<String> perms = getPermsByUserName(userName);

        //4.将该用户userName的角色信息和权限信息，放入SimpleAuthorizationInfo
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(perms);
        return authorizationInfo;
    }

    //模拟Dao层从数据库或缓存中通过用户获取角色信息
    private Set<String> getRolesByUserName(String userName){
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    //模拟Dao层从数据库或缓存中通过用户获取权限信息
    private Set<String> getPermsByUserName(String userName){
        Set<String> perms = new HashSet<>();
        perms.add("admin:add");
        perms.add("admin:delete");
        perms.add("user:select");
        return perms;
    }

}
