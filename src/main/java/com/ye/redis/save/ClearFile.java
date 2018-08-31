package com.ye.redis.save;

import com.ye.redis.file.OperateFile;
/**
 * 清除文件内容
 * @author 烨
* */
public class ClearFile {
    /**
     * 判断清除文件内容是否成功
     * @return 成功返回true
     * */
    public static boolean clear()
    {
        return OperateFile.clear();
    }
}
