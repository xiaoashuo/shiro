package com.example.shiro.shiro.realm;

import com.example.shiro.entity.Perms;
import com.example.shiro.entity.User;
import com.example.shiro.exception.UserNotExistsException;
import com.example.shiro.exception.UserPasswordNotMatchException;
import com.example.shiro.exception.UserPasswordRetryLimitExceedException;
import com.example.shiro.mapper.CatPermMapper;
import com.example.shiro.mapper.UserMapper;
import com.example.shiro.service.CatPermService;
import com.example.shiro.service.LoginService;
import com.example.shiro.service.UserService;
import com.example.shiro.utils.security.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    private static final Logger log=LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    UserService userService;
    @Autowired
    CatPermService catPermService;
    @Autowired
    LoginService loginService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-----");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        User user = ShiroUtils.getSysUser();
        Set<String> roles=new HashSet<>();
        Set<String> perms=new HashSet<>();
        if (user.getId()==1L){
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        }else {
                roles=userService.getRIdByUid(user.getId());
                perms = catPermService.getPermsByUserId(user.getId());
                info.addRoles(roles);
                info.addStringPermissions(perms);
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password="";
        if (token.getPassword()!=null){
            password=new String(token.getPassword());
        }
        User user=null;
        try {
            user=loginService.login(username,password);
        } catch (UserNotExistsException e) {
             throw new UnknownAccountException(e.getMessage(),e);
        }catch (UserPasswordNotMatchException e){
            throw new IncorrectCredentialsException(e.getMessage(),e);
        }catch (UserPasswordRetryLimitExceedException e){
            throw new ExcessiveAttemptsException(e.getMessage(),e);
        }  catch (Exception e)
        {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }

        /**
         * 正常参数步骤流程
         * 所需参数Object principal, Object credentials, String realmName
         * 1.唯一字符串 username email 或者user对象都可以 推荐是字符串形式
         * 2.password --->数据库的密码
         * 3.自定义Relam 的名字 数据库与应用程序的桥梁
         */
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        //清楚以前的验证
        clearCachedAuthorizationInfo();
        return info;
    }
    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}