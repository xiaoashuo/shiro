package com.example.shiro.exception;

public class UserPasswordRetryLimitExceedException extends RuntimeException {
    public UserPasswordRetryLimitExceedException(int i) {
        super("用户登录超过"+new Object[]{i}+"次");
    }
}
