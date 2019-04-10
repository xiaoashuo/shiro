package com.example.shiro.utils.manager;

import com.example.shiro.constants.enumconstants.TaskStatusEnum;
import com.example.shiro.entity.TaskInfo;
import com.example.shiro.utils.Threads;
import com.example.shiro.utils.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理器
 * 
 * @author liuhulu
 */
@Component
public class AsyncManager
{


    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");
    /**
     * 保存任务信息
     */
    private Map<String, TaskInfo> taskContainer = new HashMap<>(16);
    @Autowired
    AsyncTaskExecutor asyncTaskExecutor;

    /**
     * 单例模式
     */
  //  private static AsyncManager me = new AsyncManager();

  /*  public static AsyncManager me()
    {
        return me;
    }*/

    /**
     * 执行任务
     * 
     * @param task 任务
     */
    public void execute(TimerTask task)
    {
        TaskInfo info = initTask();
        String taskId = info.getTaskId();
        asyncTaskExecutor.executor(task,taskId,executor);
    }


    /**
     * 停止任务线程池
     */
    public void shutdown()
    {
        Threads.shutdownAndAwaitTermination(executor);
    }

    /**
     * 初始化任务
     *
     * @return taskInfo
     */
    public TaskInfo initTask() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskId(getTaskId());
        taskInfo.setStatus(TaskStatusEnum.STARTED);
        taskInfo.setStartTime(new Date());
        setTaskInfo(taskInfo);
        return taskInfo;
    }



    /**
     * 保存任务信息
     *
     * @param taskInfo 任务信息
     */
    public void setTaskInfo(TaskInfo taskInfo) {
        taskContainer.put(taskInfo.getTaskId(), taskInfo);
    }

    /**
     * 获取任务信息
     *
     * @param taskId 任务ID
     * @return
     */
    public TaskInfo getTaskInfo(String taskId) {
        return taskContainer.get(taskId);
    }

    /**
     * 获取任务状态
     *
     * @param taskId 任务ID
     * @return
     */
    public TaskStatusEnum getTaskStatus(String taskId) {
        return getTaskInfo(taskId).getStatus();
    }

    /**
     * 生成任务ID
     *
     * @return taskId
     */
    public String getTaskId() {
        return UUID.randomUUID().toString();
    }

}
