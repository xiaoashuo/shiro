package com.example.shiro.service.impl;

import com.example.shiro.mapper.UserMapper;
import com.example.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Set<String> getRIdByUid(Long id) {
        return userMapper.getRIdByUid(id);
    }
}
