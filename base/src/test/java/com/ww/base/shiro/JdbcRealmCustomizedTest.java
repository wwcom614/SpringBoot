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
public class JdbcRealmCustomizedTest {

    @Resource
    private DataSource dataSource;

    @Test
    public void testJdbcRealmCustomized() throws Exception {

        //1.构建Realm
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //注意：JdbcRealm默认是不检查权限的(全部拒绝)，需要手工打开
        jdbcRealm.setPermissionsLookupEnabled(true);

        //!!!一般自己的表结构和jdbcRealm默认定义的肯定不一样!!!
        String userSql = "select user_password from t_users where user_name = ?";
        jdbcRealm.setAuthenticationQuery(userSql);

        String roleSql = "select role_name from t_user_roles where user_name = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        String permSql = "select perm_name from t_roles_permissions where role_name = ?";
        jdbcRealm.setPermissionsQuery(permSql);

        //2.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //3.主体提交认证请求
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token1 = new UsernamePasswordToken("userww", "654321");
        subject.login(token1);
        log.info("【Authenticate】：userww登录状态{}", subject.isAuthenticated());

        UsernamePasswordToken token2 = new UsernamePasswordToken("ww1", "123456");
        try {
            subject.login(token2);
        }catch (UnknownAccountException e){
            log.info("【Authenticate】：登录用户名ww1不存在。");
        }

        UsernamePasswordToken token3 = new UsernamePasswordToken("userww", "123456");
        try {
            subject.login(token3);
        }catch (IncorrectCredentialsException e){
            log.info("【Authenticate】：userww登录密码错误。");
        }

        try {
            subject.checkRole("guest");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：userww的角色不是guest。");
        }

        try {
            subject.checkRoles("admin");
            log.info("【Authorize】：userww的角色有admin。");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：userww的角色没有admin。");
        }

        try {
            subject.checkPermission("admin:update");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：userww权限不是admin:update。");
        }

        try {
            subject.checkPermissions("admin:delete");
            log.info("【Authorize】：userww的权限有admin:delete。");
        }catch (UnauthorizedException e){
            log.info("【Authorize】：userww的权限没有admin:delete。");
        }

        subject.logout();
        log.info("【Authenticate】：userww登录状态{}", subject.isAuthenticated());


    }


}