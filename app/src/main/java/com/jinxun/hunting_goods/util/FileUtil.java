package com.jinxun.hunting_goods.util;

import android.content.Context;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by admin on 2019/1/8.
 */

public class FileUtil {

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    //图片文件转化为Base64
    public static String fileToStream(File file){
        FileInputStream inputStream = null;
        String str = "";
        try {
            inputStream = new FileInputStream(file);
            byte[]bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            str = Base64.encode(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
}
