package com.example.shiro.utils.manager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by shirukai on 2018/7/31
 * 异步任务执行器
 */
@Component
public class AsyncTaskExecutor {
    private static Logger LOG = LoggerFactory.getLogger(AsyncTaskExecutor.class);
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;
    public void executor( TimerTask task, String taskId,ScheduledExecutorService executor) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
