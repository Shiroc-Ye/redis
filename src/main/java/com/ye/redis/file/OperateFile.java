package com.ye.redis.file;

import com.ye.redis.map.MapNode;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;
/**
 * 操作xml文件
 * @author 烨
 * */

public class OperateFile {

    /**
     * 存储键值对到文件的map中
     * @param key 键
     * @param value 值
     * */
    public static boolean AddNode(String  key,MapNode value)
    {
        ConnectionFile connectionFile=new ConnectionFile();
        Document document=connectionFile.load();
        Element root=document.getRootElement();
        Element dictElement=root.element("dict");
        Element dictKeyElement=dictElement.addElement("key");
        dictKeyElement.setText(key);
        Element dictValueElement=dictKeyElement.addElement("value");
        dictValueElement.setText(value.getValue());
        dictValueElement.addAttribute("time",(value.getTimeout())+"");
        return connectionFile.saveFile(document);
    }
    /**
     * 存储list或者set到文件中
     * @param key 键值
     * @param list list或者set
     * */
    public static boolean AddNode(String key,String type, Collection<MapNode> list)
    {
        ConnectionFile connectionFile=new ConnectionFile();
        Document document=connectionFile.load();
        Element root=document.getRootElement();
        Element dictElement=root.element(type);
        Element dictKeyElement=dictElement.addElement("key");
        dictKeyElement.setText(key);
        for(MapNode node:list)
        {
            Element dictValueElement=dictKeyElement.addElement("value");
            dictValueElement.setText(node.getValue());
            dictValueElement.addAttribute("time",(node.getTimeout())+"");
        }
        return connectionFile.saveFile(document);
    }

    /**
     * 从文件中读取map
     * @param list key所对应的链表
     * @return 每个key对应的链表
     * */
    public static LinkedList<MapNode> findDict(LinkedList<String> list)
    {
        ConnectionFile connectionFile=new ConnectionFile();
        LinkedList<MapNode> nodes=new LinkedList<>();
        Document document=connectionFile.load();
        Element root=document.getRootElement();
        Element dictElement=root.element("dict");
        //获取dict节点下的所有name为key的子结点
        List dictNode=dictElement.elements("key");
        for (Object aDictNode : dictNode) {
            //获取name为key的子结点
            Element key = (Element) aDictNode;
            String keyTextTrimemp = key.getTextTrim();
            list.add(keyTextTrimemp);
            Element value = key.element("value");
            //返回value的值
            MapNode node = new MapNode(value.getTextTrim());
            Attribute arr = value.attribute("time");
            node.setTimeout(Long.valueOf(arr.getValue()));
            if(!node.istimeout())
            {
                continue;
            }
            nodes.add(node);
        }
        return nodes;
    }
    /**
     *从文件中找到指定key对应 node
     * @param list key所对应的链表
     * @return 每个可以对应的list或者set组成
    * */
    public static LinkedList<LinkedList<MapNode>> findList(LinkedList<String> list, String type)
    {
        ConnectionFile connectionFile=new ConnectionFile();
        LinkedList<LinkedList<MapNode>> linkedLists=new LinkedList<>();
        LinkedList<MapNode> linkedList=new LinkedList<>();
        Document document=connectionFile.load();
        Element root=document.getRootElement();
        Element listElement=root.element(type);
        List listNode=listElement.elements("key");
        for (Object aListNode : listNode) {
            Element key = (Element) aListNode;
            list.add(key.getTextTrim());
            List node = key.elements("value");
            for (Object aNode : node) {
                Element value = (Element) aNode;
                MapNode temp = new MapNode(value.getTextTrim());
                Attribute arr = value.attribute("time");
                temp.setTimeout(Long.valueOf(arr.getValue()));
                if(!temp.istimeout())
                {
                    continue;
                }
                linkedList.add(temp);
            }
            linkedLists.add(linkedList);
        }
        return linkedLists;
    }

    /**
     * 清除XML文件里面内容
     * @return 清除成功返回true
     * */
    public static boolean clear()
    {
        ConnectionFile connectionFile=new ConnectionFile();
        Document document=connectionFile.load();
        Element root=document.getRootElement();
        Element dict=root.element("dict");
        Element list=root.element("list");
        Element set=root.element("set");
        if((clearElement(dict)&&clearElement(list)&&clearElement(set)))
        {
            return connectionFile.saveFile(document);
        }
        return false;
    }
    /**
     * 清除该节点下的所有子结点
     * @param element 需要清除的节点
     * @return 成功返回为true
     * */
    private static boolean clearElement(Element element)
    {
        boolean tag=true;
        List keyList=element.elements("key");
        if(keyList==null) return true;
        for (Object aKeyList : keyList) {
            Element key = (Element) aKeyList;
            List valueList = key.elements("value");
            for (Object aValueList : valueList) {
                Element value = (Element) aValueList;
                 if(!key.remove(value)) return false;
            }
            tag = element.remove(key);
        }
        return tag;
    }

}
