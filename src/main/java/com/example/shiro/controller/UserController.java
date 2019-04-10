package com.example.shiro.controller;

import com.example.shiro.aspectj.annotation.Log;
import com.example.shiro.constants.enumconstants.BusinessType;
import com.example.shiro.entity.User;
import com.example.shiro.mapper.UserMapper;
import com.example.shiro.service.LoginService;
import com.example.shiro.utils.AjaxResult;
import com.example.shiro.utils.ServletUtils;
import com.example.shiro.utils.StringUtils;
import com.example.shiro.utils.security.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {


    @GetMapping("/clear")
    @ResponseBody
    public String clearAuth(){
        ShiroUtils.clearCachedAuthorizationInfo();
        return "1";
    }

    @RequiresPermissions({"user:view:level1"})
    @GetMapping("/level1")
    public String level1(){
        return "level1";
    }
    @Log(title = "访问登录地址",businessType = BusinessType.VIEW)
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password)
    {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return AjaxResult.success("登录成功", subject.getPrincipal());
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return AjaxResult.error(msg);
        }
    }


}
