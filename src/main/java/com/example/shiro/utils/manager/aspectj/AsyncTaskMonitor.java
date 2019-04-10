package com.example.shiro.utils.manager.aspectj;
import com.example.shiro.constants.enumconstants.TaskStatusEnum;
import com.example.shiro.entity.TaskInfo;
import com.example.shiro.utils.manager.AsyncManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by shirukai on 2018/7/31
 * 异步任务监控
 */
@Component
@Aspect
public class AsyncTaskMonitor {

    private static Logger LOG = LoggerFactory.getLogger(AsyncTaskMonitor.class);

    @Autowired
    AsyncManager manager;

    @Pointcut("execution(* com.example.shiro.utils.manager.AsyncTaskExecutor.executor(..))")
    public void pointCut(){

    }


    /**
     * 包及其子包下的所有类所有方法 ..*.*(..)
     *环绕增强
     * @param pjp
     */
    @Around("pointCut()")
    public void taskHandle(ProceedingJoinPoint pjp) {
       //获取taskId
        String taskId = pjp.getArgs()[1].toString();
        //获取任务信息
        TaskInfo taskInfo = manager.getTaskInfo(taskId);
        LOG.info("AsyncTaskMonitor is monitoring async task:{}", taskId);
        taskInfo.setStatus(TaskStatusEnum.RUNNING);
        manager.setTaskInfo(taskInfo);
        TaskStatusEnum status = null;
        try {
            pjp.proceed();
            status = TaskStatusEnum.SUCCESS;
        } catch (Throwable throwable) {
            status = TaskStatusEnum.FAILED;
            LOG.error("AsyncTaskMonitor:async task {} is failed.Error info:{}", taskId, throwable.getMessage());
        }
        taskInfo.setEndTime(new Date());
        taskInfo.setStatus(status);
        taskInfo.setTotalTime();
        manager.setTaskInfo(taskInfo);
        System.out.println(taskInfo);
    }
}