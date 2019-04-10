package com.example.shiro.service.impl;

import com.example.shiro.entity.User;
import com.example.shiro.exception.UserPasswordNotMatchException;
import com.example.shiro.exception.UserPasswordRetryLimitExceedException;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PassWordService {

    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    @PostConstruct
    public void init()
    {
        loginRecordCache = cacheManager.getCache("loginRecordCache");
    }

    public void validate(User user, String password) throws RuntimeException{
        String loginName = user.getLastName();
        AtomicInteger retryCount = loginRecordCache.get(loginName);
        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue())
        {
            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
        }

        if (!matches(user, password))
        {
            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(loginName);
        }

    }
    public boolean matches(User user, String newPassword)
    {
        return user.getPassword().equals(encryptPassword(user.getLastName(), newPassword, "111111"));
    }
    public void clearLoginRecordCache(String username)
    {
        loginRecordCache.remove(username);
    }

    /**
     * 加密密码
     * @param username
     * @param password
     * @param salt
     * @return
     */
    public String encryptPassword(String username, String password, String salt)
    {
        return new Md5Hash(username + password + salt).toHex().toString();
    }
    public static void main(String[] args)
    {
        System.out.println(new PassWordService().encryptPassword("admin1", "admin123", "111111"));

    }

}
