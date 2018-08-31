package com.ye.redis.net;

import com.ye.redis.task.TaskScheduleExecutor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 服务器端主线程，用于接收socket并启动服务线程
 * */
public class RedisSever {
    static List<Socket> socketList= Collections.synchronizedList(new ArrayList<Socket>());

    /**
     * 启动自动缓存服务和定期删除过期键服务
     * */
    private void Initial()
    {
        TaskScheduleExecutor.startTask();
    }
    public static void main(String[] args)throws IOException
    {
        RedisSever sever=new RedisSever();
        ServerSocket serverSocket=new ServerSocket(30000);
        System.out.println("服务器初始化成功");
        //sever.Initial();
        while (true) {
            try {
                Socket socket=serverSocket.accept();
                socketList.add(socket);
                new Thread(new RedisSeverThread(socket)).start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
