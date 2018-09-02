package com.ye.redis.net;

import com.ye.redis.hashCheck.Hash;

import java.io.*;
import java.net.Socket;

/**
 * 主客户端线程，用于启动接收数据线程以及向服务端写入数据
 * @author 烨
 * */
public class RedisClient {
    /*
    * 客户端从键盘输入*/

    public static void main(String[] args)throws  IOException
    {
        System.out.println("请输入命令：");
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String temp;
        while ((temp=br.readLine())!=null)
        {

            switch (temp) {
                case "quit":
                    System.exit(0);
                case " ":
                    System.out.println("输入命令不合法！！！");
                    System.out.println("请输入命令：");
                    break;
                default:


                    if(!CommandCheck.checkCommand(temp))
                    {
                        System.out.println("输入命令不合法！！！");
                        System.out.println("请输入命令：");
                        continue;
                    }
                    String[] text=temp.split(" ");
                    String key = text[1];

                    try {
                        Socket socket = new Socket("127.0.0.1", Hash.getSocket(key));


                        new Thread(new RedisClientThread(socket)).start();


                        PrintStream ps = new PrintStream(socket.getOutputStream());

                        ps.println(temp);
                    }
                    catch (IOException e)
                    {
                        System.out.println("服务器连接失败，可能对应服务器未开启，请尝试其他服务器！");
                        continue;
                    }
                    System.out.println("请输入命令：");
                    break;
            }

        }
    }
}
