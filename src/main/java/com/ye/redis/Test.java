package com.ye.redis;

import java.util.zip.CRC32;

public class Test {
    public static void main(String[] args)
    {
        CRC32 crc32=new CRC32();
        CRC32 crc1=new CRC32();
        crc32.update("jack".getBytes());
        System.out.println(crc32.getValue());
        crc1.update("Dave".getBytes());
        System.out.println(crc1.getValue());
    }
}
