package com.example.shiro.exception;

public class UserPasswordNotMatchException extends RuntimeException {

    public UserPasswordNotMatchException(){
        super("用户或密码不匹配");
    }
}
