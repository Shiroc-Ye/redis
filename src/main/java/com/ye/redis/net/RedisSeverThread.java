package com.ye.redis.net;

import com.ye.redis.map.MapImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 服务器端处理线程
 * @author 烨
 * */
public class RedisSeverThread implements Runnable{
    private Socket socket;//接收到的socket
    private PrintStream ps;//输出流
    private BufferedReader reader;//输入流
    private boolean isClose=false;//判断流是否关闭

    RedisSeverThread(Socket socket)throws IOException
    {
        this.socket=socket;
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ps=new PrintStream(socket.getOutputStream());
    }
    @Override
    public void run()
    {
        //字典实体
        MapImpl map = MapImpl.getInstance();
        String command;
        while ((command=readFormClient())!=null) {
            ps.println(map.judgeCommand(command));
            System.out.println("客户端命令为：" + command);
            if(isClose)
            {
                Thread.currentThread().interrupt();
            }
        }
    }
    /**
     * 从客户端读取数据
     * @return 返回读取数据
     * */
    private String readFormClient()
    {
        try {
            return reader.readLine();
        }
        catch (IOException e)
        {
            isClose=true;
            RedisSever.socketList.remove(socket);

        }
        return null;
    }
}
