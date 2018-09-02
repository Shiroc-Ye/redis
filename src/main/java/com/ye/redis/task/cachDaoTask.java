package com.ye.redis.task;

import com.ye.redis.map.App;
import com.ye.redis.map.MapImpl;

/**
 * 数据持久化线程
 * @author 烨
 * */
public class cachDaoTask implements Runnable {
    private  long count;
    private MapImpl instance=MapImpl.getInstance();


   public cachDaoTask()
    {
        this.count=App.Default_Data_Hit;
    }
    cachDaoTask(long count)
    {
        this.count=count;
    }
    @Override
    public void run() {
        if(App.Data_Hit>=count)
        {
            System.out.println("访问次数达到上限，正在进行持久化....");
            System.out.println(instance.save());
            System.out.println("访问次数为"+App.Data_Hit+" 正在进行清零...");
            App.Data_Hit=0;
        }

    }
}
