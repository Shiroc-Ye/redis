package com.ye.redis.task;

import com.ye.redis.map.App;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * 定时任务器
 * @author 烨
 * */

public class TaskScheduleExecutor {

    /**
     * 开始自动任务
     * */
    public static void startTask()
    {
        int threadPoolSize= App.Redis_Check_Map.size();
        //定义线程池
        ScheduledExecutorService service= Executors.newScheduledThreadPool(threadPoolSize);

        System.out.println("正在进行定期删除任务...");
        service.scheduleWithFixedDelay(new cachTimerTask(),App.Initial_Delay_Time,App.Data_Check_Time, TimeUnit.SECONDS);

        System.out.println("正在进行定时缓存任务...");
        for(Long key:App.Redis_Check_Map.keySet())
        {
            service.scheduleWithFixedDelay(new cachDaoTask(App.Redis_Check_Map.get(key)),App.Initial_Delay_Time,key,TimeUnit.SECONDS);
        }
    }
}
