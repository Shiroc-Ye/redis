package com.ye.redis.hashCheck;


import java.util.zip.CRC32;
/**
 * 进行hash计算
 * @author 烨
 * */
public class Hash {

    /**
     * 计算键所对应的端口号
     * @param key 用来计算服务区端口号的键
     * @return 返回键对应的服务器端口号
     * */
    public static int getSocket(String key)
    {
        CRC32 crc32=new CRC32();
        crc32.update(key.getBytes());
        long hash_Size = 30000;
        long value=crc32.getValue()% hash_Size;
        if(value>=0&&value<9999)
            {
                return 31000;
            }
        else if(value>=9999&&value<19999)
            {
                return 32000;
            }
        else if(value>=19999&value<29999)
            {
                return 33000;
            }
        return 0;
    }

}
