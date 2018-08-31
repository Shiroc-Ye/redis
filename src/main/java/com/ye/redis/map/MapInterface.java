package com.ye.redis.map;

/**
 * 数据库接口
 * @author 烨
 * */
public interface MapInterface {
    /**
     * 该方法用于存放数据库的键值
     * @param command 具体操作命令
     * @param key 存放的键
     * @param values 存放的参数
     * @return 返回存放情况信息
     * */
    String set(String command, String key, String values);
    /**
     * 删除内存中指定键及其对应的值
     * @param key 删除的键
     * @return 返回操作信息
     * */
    String del( String key);
    /**
     * 获取指定键对应的值
     * @param key 操作键值
     * @return 返回取得的值
     * */
    String get( String key);
}
