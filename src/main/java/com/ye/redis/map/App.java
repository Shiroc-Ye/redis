package com.ye.redis.map;

import com.sun.istack.internal.NotNull;

import java.util.HashMap;

/**
 * 用来存放一些特定值
 * @author 烨
 * */
public class App {
    public static int                   Data_Hit=0;                               //数据访问次数

    public static int                   Data_Check_Time =60;                      //定义定期检查时间

    public static long                   Initial_Delay_Time=30;                    //定义初始时间

    static int                          Default_Time_Out=30;                      //默认过期时间30秒

    public static int                   Default_Data_Hit =6;                      //默认访问次数,超过该次数缓存文件

    public static HashMap<Long,Long>    Redis_Check_Map;                          //定时检查策略

   static
    {
        Redis_Check_Map=new HashMap<>();
        Redis_Check_Map.put((long)30,(long)6);
        Redis_Check_Map.put((long)60,(long)9);
    }
}
