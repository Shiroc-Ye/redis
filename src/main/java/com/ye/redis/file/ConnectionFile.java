package com.ye.redis.file;


import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * 连接XML文件
 * @author 烨
 * */
public class ConnectionFile{
    private static volatile Document document=null;

    public  void setUrl(String url) {
        this.url = url;
    }

    public  String url = "D:\\文档\\710\\mini\\redis\\src\\main\\resources\\data.xml";

    /**
     * 加载存储数据的XML文件
     * @return 返回加载的文件
     * */
    public Document load()
    {

        try
        {
            SAXReader saxReader=new SAXReader();

            document=saxReader.read(new File(url));

            //如果文件无根结点
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return document;
    }
    /**
     * 存储一个Document 文件
     * @param document 存储的文件
     * @return 存储成功返回true
     * */
    public boolean saveFile(Document document)
    {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();

            format.setEncoding("UTF-8");

            XMLWriter writer = new XMLWriter(new FileOutputStream(url), format);

            writer.write(document);

            writer.flush();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("打开文件失败！");
            return false;
        }
    }
}
