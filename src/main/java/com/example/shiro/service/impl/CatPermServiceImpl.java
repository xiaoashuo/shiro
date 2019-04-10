package com.example.shiro.service.impl;

import com.example.shiro.mapper.CatPermMapper;
import com.example.shiro.service.CatPermService;
import com.example.shiro.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CatPermServiceImpl implements CatPermService {

    @Autowired
    CatPermMapper catPermMapper;
    @Override
    public Set<String> getPermsByUserId(Long user_id) {
        Set<String> perms = catPermMapper.getPermsByUserId(user_id);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            System.out.println(perm);
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim()));
            }
        }
        return permsSet;


    }
}
