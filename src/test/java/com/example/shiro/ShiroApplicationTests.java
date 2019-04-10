package com.example.shiro;

import com.example.shiro.entity.TaskInfo;
import com.example.shiro.entity.User;
import com.example.shiro.mapper.UserMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =ShiroApplication.class)
public class ShiroApplicationTests {



    @Autowired
    UserMapper userMapper;

    @Test
    public void contextLoads() {

    }

}
