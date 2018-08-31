package com.ye.redis.save;

import com.ye.redis.file.OperateFile;
import com.ye.redis.map.MapNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
    /**
     * 从文件中读取数据
     * @author 烨
     * */
public class SaveToFile {
    /**
     * 将内存中dict存入文件中
     * @param dict 要存储的dict
     * @return 返回是否成功
     * */
    public static boolean save(HashMap<String, MapNode> dict)
    {
        if(dict.size()==0)return true;
        for(String key:dict.keySet())
        {
            return OperateFile.AddNode(key,dict.get(key));
        }
        return false;
    }

        /**
         * 将内存中list存入文件中
         * @param list 要存储的dict
         * @return 返回是否成功
         * */
    public static boolean saveList(HashMap<String, ArrayList<MapNode>> list)
    {
        boolean tag=false;
        if(list.size()==0)return true;
        for(String key:list.keySet())
        {
            ArrayList<MapNode> nodeList=list.get(key);
            if(OperateFile.AddNode(key,"list",nodeList))
            {
                tag=true;
            }
            else
            {
                tag=false;
                break;
            }
        }
        return tag;
    }
        /**
         * 将内存中set存入文件中
         * @param set 要存储的dict
         * @return 返回是否成功
         * */
    public static boolean saveSet(HashMap<String, HashSet<MapNode>> set)
    {
        boolean tag=true;
        if(set.size()==0)
            return true;
        for(String key:set.keySet())
        {
            HashSet<MapNode> nodeSet=set.get(key);
            if(OperateFile.AddNode(key,"set",nodeSet))
            {
                tag=true;
            }
            else
            {
                tag=false;
                break;
            }
        }
        return tag;
    }
}
