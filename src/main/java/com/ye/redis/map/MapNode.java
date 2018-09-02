package com.ye.redis.map;
/**
 * 数据节点
 * @author 烨 
* */
public class MapNode {
    public String getValue() {
        return value;
    }
    //

    private String value;

    public long getTimeout() {
        return timeout;
    }


    private long timeout;//过期时间

    @Override
    public String toString() {
        App.Data_Hit++;
        return value;
    }
    public MapNode(String value)
    {
        this.value=value;
    }
    {
        timeout=System.currentTimeMillis()+App.Default_Time_Out*1000;
    }
    /**
     * 判断节点是否过期
     * @return 过期返回false
     * */
    public boolean istimeout()
    {
        //System.out.println(value+((timeout-System.currentTimeMillis())/1000));
        if(timeout==-1)
        {
            return true;
        }
        else
        {
            return System.currentTimeMillis() - timeout <= 0;
        }
    }

    /**
     * 用户设置过期时间
     * @param timeout 自定义过期时间
     * */
    public void setTimeout(long timeout)
    {
        if(timeout==-1)
        {
            this.timeout=-1;
        }
        else {
            this.timeout = timeout*1000 + System.currentTimeMillis();
        }
    }

}

