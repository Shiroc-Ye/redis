package com.ye.redis.task;

import com.ye.redis.map.MapImpl;

/***
 * 数据过期自动删除
 * @author 烨
 */
public class cachTimerTask implements Runnable {
    private MapImpl instance=MapImpl.getInstance();
    @Override
    public void run() {
        System.out.println("正在进行过期自动删除...");
        instance.clearDateOutNodes();
    }
}
