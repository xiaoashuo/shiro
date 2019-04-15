package com.example.shiro.service.impl;

import com.example.shiro.entity.OperLog;
import com.example.shiro.mapper.IOperLogMapper;
import com.example.shiro.service.IOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IOperLogServiceImpl implements IOperLogService {
/*
    @Autowired
    IOperLogMapper iOperLogMapper;
*/


    @Override
    public void insert(OperLog operLog) {
         //iOperLogMapper.save(operLog);
    }
}
