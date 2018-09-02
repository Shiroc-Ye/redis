package com.ye.redis.map;

import com.ye.redis.save.ClearFile;
import com.ye.redis.save.ReadFromFile;
import com.ye.redis.save.SaveToFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 数据库实现及基本操作
 * @author 烨
 * */
public class MapImpl implements MapInterface {
    //存放单纯键值对
    private volatile HashMap<String, MapNode> dict=new HashMap<>();
    //存放一个键对应一个List的键值对
    private volatile HashMap<String, ArrayList<MapNode>> list=new HashMap<>();
    //存放一个键对应一个Set的键值对
    private volatile HashMap<String, HashSet<MapNode>> set=new HashMap<>();
    /*
    * 采用单例模式，只能创建一个mapImpl对象
    * */
    private static MapImpl instance;

    private MapImpl()
    {
        instance=this;
    }
    /**
     * 获取MapImpl实例
     * @return 返回MapImpl实例
     * */
    public static MapImpl getInstance()
    {
        if(instance==null)
        {
            instance=new MapImpl();
            instance.Initial();
        }
        return instance;
    }

/**
 * 该方法用于存放数据库的键值
 * @param command 具体操作命令
 * @param key 存放的键
 * @param values 存放的参数
 * @return 返回存放情况信息
 * */
    @Override
    public String set(String command,String key, String... values) {
        App.Data_Hit++;
        checkKey(key);
        switch (command)
        {
            case "dset":
                dict.put(key,new MapNode(values[0]));
//                System.out.println("系统缓存中...");
                System.out.println("存储数据为："+values[0]);
                 return "系统缓存"+(dict.containsKey(key)?"成功":"失败");
            case "lset":
                ArrayList<MapNode> mapNodeList=new ArrayList<>();
//                System.out.println("系统缓存中...");
                for (String avalues : values) {
                    MapNode temp = new MapNode(avalues);

                    mapNodeList.add(temp);
                    if (!mapNodeList.contains(temp)) {
                        return "系统缓存失败！";
                    }
                }
                System.out.println("存储数据为："+mapNodeList);
                list.put(key,mapNodeList);
                return "系统缓存"+(list.containsKey(key)?"成功":"失败");
            case "sset":
                HashSet<MapNode> mapNodeSet=new HashSet<>();
//                System.out.println("系统缓存中...");
                for (String avalues : values) {
                    MapNode temp = new MapNode(avalues);
                    mapNodeSet.add(temp);
                    if (!mapNodeSet.contains(temp)) {
                        System.out.println("系统缓存失败！");
                        break;
                    }
                }
                System.out.println("存储数据为："+mapNodeSet);
                set.put(key,mapNodeSet);
                return "系统缓存"+(set.containsKey(key)?"成功":"失败");
            default:
                return "请输入正确命令";
        }
    }

    /**
     * 删除内存中指定键及其对应的值
     * @param key 删除的键
     * @return 返回操作信息
     * */
    @Override
    public String del(String key) {
        System.out.println("删除中....");
        if(dict.containsKey(key))
        {
            if((dict.remove(key))!=null)
            {
                return "删除成功！";
            }
            else return "删除失败，该键不存在";
        }
        else if (list.containsKey(key))
        {
            if((list.remove(key))!=null)
            {
                return "删除成功！";
            }
            else return "删除失败，该键不存在";
        }
        else if(set.containsKey(key))
        {
            if((set.remove(key))!=null)
            {
                return "删除成功！";
            }
            else return "删除失败，该键不存在";
        }
        else
        {
            return "删除失败，该键不存在";
        }
    }
    /**
     * 获取指定键对应的值
     * @param key 操作键值
     * @return 返回取得的值
     * */
    @Override
    public String get(String key) {
        if(dict.containsKey(key))
        {
            return key+"对应值为："+dict.get(key).toString();
        }
        else if (list.containsKey(key))
        {
            return key+"对应值为："+list.get(key).toString();
        }
        else if(set.containsKey(key))
        {
            return key+"对应值为："+set.get(key).toString();
        }
        else
        {
            return "查找失败，该键不存在";
        }
    }

    /**
     * 判断是否有相同的键
     * @param key 判断的键
     * */
    private void checkKey(String key)
    {
        if(dict.containsKey(key))
        {
            dict.remove(key);
        }
        else if(list.containsKey(key))
        {
            list.remove(key);
        }
        else set.remove(key);
    }

    /**
     * 具体操作命令的判断
     * @param text 需要判断的字符串
     * @return 返回操作结果
     * */
    public String judgeCommand(String text)
    {
        String[] judge;
        judge=text.split(" ");
        switch (judge[0])
        {
            case "dset":
            case "lset":
            case "sset":
                String[] values=new String[judge.length-2];
                if (judge.length - 2 >= 0) System.arraycopy(judge, 2, values, 0, judge.length - 2);
                 return set(judge[0],judge[1],values);
                // return set(judge[0],judge[1],text.substring(judge[0].length()+judge[1].length()+2));
            case "get":
                return get(judge[1]);
            case "del":
               return del(judge[1]);
            case "expire":
                return changTimeOut(judge[1],judge[2]);
                default:
                  return "请输入正确命令！";
        }
    }
    /**
     * 存储内存中的数据到文件
     * 暂时不考虑管理员发送存储命令
     * @return 返回操作信息
     * */
    public String save()
    {
        if(!ClearFile.clear())
        {
            return "清除文件失败！";
        }
        if(SaveToFile.saveSet(set)&&SaveToFile.save(dict)&&SaveToFile.saveList(list))
        {
            return "存储成功";
        }
        else return "存储失败";
    }
    /**
     * 从文件中读取数据
     * @return 返回操作信息
     * */
    private String read()
    {
        if(ReadFromFile.readList(list)&&ReadFromFile.readSet(set)&&ReadFromFile.readDict(dict))
        {
            return "文件读取成功";
        }
        return "文件读取失败";
    }
    /**
     * 清除过期节点
     * */
    public void clearDateOutNodes()
    {
        for(String key:dict.keySet())
        {
            if(!dict.get(key).istimeout())
            {
                System.out.println(key+"过期，系统自动删除...");
                dict.remove(key);
            }
        }
        for(String key:list.keySet())
        {
            if(!(list.get(key).get(0).istimeout()))
            {
                set.remove(key);
                System.out.println(key+"过期，系统自动删除...");
                break;
            }
        }
        for(String key:set.keySet())
        {
            for(MapNode node:set.get(key))
            {
                if(!node.istimeout())
                {
                    set.remove(key);
                    System.out.println(key+"过期，系统自动删除...");
                    break;
                }
            }
        }
    }

    /**
     * 数据初始化
     * */
    private void Initial()
    {
        clearDateOutNodes();
        System.out.println(read());
    }
    /**
     * 设置过期时间
     * @param key 要修改的键
     * @param time 设置的时间
     * @return 返回操作信息
     * */
    private String changTimeOut(String key, String time)
    {
        long settime=Long.valueOf(time);
        if(dict.containsKey(key))
        {
            dict.get(key).setTimeout(settime);
        }
        else if(list.containsKey(key))
        {
            ArrayList<MapNode> list1=list.get(key);
            for(MapNode node:list1)
            {
                node.setTimeout(settime);
            }
        }
        else if(set.containsKey(key))
        {
            HashSet<MapNode> set1=set.get(key);
            for(MapNode node:set1)
            {
                node.setTimeout(settime);
            }
        }
        else
        {
            return "没有该建";
        }
        return "修改成功";
    }
}
