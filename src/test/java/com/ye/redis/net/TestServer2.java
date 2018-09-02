package com.ye.redis.net;


import com.ye.redis.file.ConnectionFile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestServer2 {
    private static List<Socket> socketList= Collections.synchronizedList(new ArrayList<Socket>());

    public static void main(String[] args)throws IOException
    {
        ConnectionFile con=new ConnectionFile();
        con.setUrl("D:\\文档\\710\\mini\\redis\\src\\main\\resources\\data1.xml");
        RedisSever sever=new RedisSever();
        ServerSocket serverSocket=new ServerSocket(32000);
        System.out.println("服务器初始化成功");
        sever.Initial();
        while (true) {
            Socket socket=serverSocket.accept();
            System.out.println("服务器2收到命令");
            socketList.add(socket);
            new Thread(new RedisSeverThread(socket)).start();
        }
    }
}
