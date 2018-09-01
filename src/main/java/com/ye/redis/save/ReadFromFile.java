package com.ye.redis.save;

import com.ye.redis.file.OperateFile;
import com.ye.redis.map.MapNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * 用来封装读取文件的操作
 * @author 烨
 * */
public class ReadFromFile {
    /**
     * 从文件中读取字典数据
     * @param dict 存放读后的数据
     * @return 返回读取是否成功
     * */
    public static boolean readDict(HashMap<String, MapNode> dict)
    {
        LinkedList<String> key=new LinkedList<>();
        LinkedList<MapNode> node;
        node=OperateFile.findDict(key);
        if(node==null)return true;
        for(int i=0;i<key.size();i++)
        {
            if(node.get(i)==null)
            {
                System.out.println("读取文件失败！");
                return false;
            }
            else {
                dict.put(key.get(i),node.get(i));
            }
        }
        return true;
    }

    /**
     * 从文件中读取链表内容
     * @param list 读取内容存放链表
     * @return 返回读取是否成功
     * */
    public static boolean readList(HashMap<String, ArrayList<MapNode>> list)
    {
        LinkedList<LinkedList<MapNode>> linkedLists;
        LinkedList<MapNode> linkedList;
        LinkedList<String> list1=new LinkedList<>();
        linkedLists=OperateFile.findList(list1,"list");
        for(int i=0;i<linkedLists.size();i++)
        {
            linkedList=linkedLists.get(i);
            if(linkedList==null&&list1.get(i)!=null)
            {
                return false;
            }
            else {
                assert linkedList != null;
                ArrayList<MapNode> arrayList = new ArrayList<>(linkedList);
                list.put(list1.get(i), arrayList);
            }
        }
        return true;
    }
    /**
     * * 从文件中读取set内容
     * @param set 读取内容存放链表
     * @return 返回读取是否成功
     * */
    public static boolean readSet(HashMap<String, HashSet<MapNode>> set)
    {
        LinkedList<LinkedList<MapNode>> linkedLists;
        LinkedList<MapNode> linkedList;
        LinkedList<String> list1=new LinkedList<>();
        linkedLists=OperateFile.findList(list1,"set");

        for(int i=0;i<linkedLists.size();i++)
        {
            linkedList=linkedLists.get(i);
            if(linkedList==null&&list1.get(i)!=null)
            {
                return false;
            }
            else {
                assert linkedList != null;
                HashSet<MapNode> hashSet = new HashSet<>(linkedList);
                set.put(list1.get(i),hashSet);
            }
        }
        return true;
    }
}
