package com.ww.base.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcRealmTest {

    @Resource
    private DataSource dataSource;

    @Test
    public void testJdbcRealm() throws Exception {

        //1.构建Realm
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //注意：JdbcRealm默认是不检查权限的(全部拒绝)，需要手工打开
        jdbcRealm.setPermissionsLookupEnabled(true);

        //2.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
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

        try {
            subject.checkRole("guest");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：ww的角色不是guest。");
        }

        try {
            subject.checkRoles("admin");
            log.info("【Authorize】：ww的角色有admin。");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：ww的角色没有admin。");
        }

        try {
            subject.checkPermission("user:update");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：ww权限不是user:update。");
        }

        try {
            subject.checkPermissions("user:delete");
            log.info("【Authorize】：ww的权限有user:delete。");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：ww的权限没有user:delete。");
        }

        subject.logout();
        log.info("【Authenticate】：ww登录状态{}", subject.isAuthenticated());


    }


}