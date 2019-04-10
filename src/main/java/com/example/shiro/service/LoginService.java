package com.example.shiro.service;

import com.example.shiro.entity.User;

public interface LoginService {
    public User login(String username,String password);
}
