package com.ww.base.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationTest {
    //1.构建Realm
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        //Realm添加属性值
        simpleAccountRealm.addAccount("ww", "123456");
    }

    @Test
    public void testAuthentication() throws Exception {
        //2.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //3.主体提交认证请求
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token1 = new UsernamePasswordToken("ww", "123456");
        subject.login(token1);
        log.info("【Authenticate】：ww登录状态{}", subject.isAuthenticated());

        UsernamePasswordToken token2 = new UsernamePasswordToken("ww1", "123456");
        try {
            subject.login(token2);
        }catch (UnknownAccountException e){
            log.info("【Authenticate】：登录用户名ww1不存在。");
        }

        UsernamePasswordToken token3 = new UsernamePasswordToken("ww", "654321");
        try {
            subject.login(token3);
        }catch (IncorrectCredentialsException e){
            log.info("【Authenticate】：ww登录密码错误。");
        }

        subject.logout();
        log.info("【Authenticate】：ww登录状态{}", subject.isAuthenticated());


    }


}