package com.ww.base.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomRealmTest {

    //1.构建Realm
    @Resource
    private CustomRealm customRealm;

    @Test
    public void testAuthenticationAndAuthorization() throws Exception {

        //HashedCredentialsMatcher指定密码加密算法和迭代次数，并把HashedCredentialsMatcher加入到Realm中
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

        //2.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //3.主体提交认证请求
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token1 = new UsernamePasswordToken("wwcustom", "123456");
        subject.login(token1);
        log.info("【Authenticate】：wwcustom登录状态{}", subject.isAuthenticated());

        UsernamePasswordToken token2 = new UsernamePasswordToken("ww1", "123456");
        try {
            subject.login(token2);
        }catch (UnknownAccountException e){
            log.info("【Authenticate】：登录用户名ww1不存在。");
        }

        UsernamePasswordToken token3 = new UsernamePasswordToken("wwcustom", "654321");
        try {
            subject.login(token3);
        }catch (IncorrectCredentialsException e){
            log.info("【Authenticate】：wwcustom登录密码错误。");
        }

        try {
            subject.checkRole("guest");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：wwcustom的角色不是guest。");
        }

        try {
            subject.checkRoles("admin");
            log.info("【Authorize】：wwcustom的角色有admin。");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：wwcustom的角色没有admin。");
        }

        try {
            subject.checkPermission("admin:update");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：wwcustom权限不是admin:update。");
        }

        try {
            subject.checkPermissions("admin:delete");
            log.info("【Authorize】：wwcustom的权限有admin:delete。");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：wwcustom的权限没有admin:delete。");
        }

        subject.logout();
        log.info("【Authenticate】：wwcustom登录状态{}", subject.isAuthenticated());

    }


}