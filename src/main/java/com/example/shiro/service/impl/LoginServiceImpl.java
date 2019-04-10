package com.example.shiro.service.impl;

import com.example.shiro.constants.UserConstants;
import com.example.shiro.entity.User;
import com.example.shiro.exception.UserNotExistsException;
import com.example.shiro.exception.UserPasswordNotMatchException;
import com.example.shiro.mapper.UserMapper;
import com.example.shiro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PassWordService passWordService;

    @Override
    public User login(String username,String password) {
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            throw new UserNotExistsException();
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throw new UserPasswordNotMatchException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw new UserPasswordNotMatchException();
        }

        User user =userMapper.getByName(username);
        if (user==null){
            throw new UserNotExistsException();
        }
        passWordService.validate(user,password);
        return user;
    }
}
