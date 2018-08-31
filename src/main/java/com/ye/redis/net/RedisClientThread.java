package com.ye.redis.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
/**
 * 客户端接收数据的线程
 * @author 烨
 * */
public class RedisClientThread implements Runnable {
    private BufferedReader br;
    RedisClientThread(Socket socket)throws IOException
    {
        /*
         * 客户端用来写入线程*/
        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    /**
     * 线程启动后运行方法，用于接收数据
     * */
    @Override
    public void run() {
        try {
            String conten;
            while ((conten=br.readLine())!=null)
            {
                System.out.println(conten);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
