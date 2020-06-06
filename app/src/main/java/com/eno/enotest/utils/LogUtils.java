package com.eno.enotest.utils;

import android.annotation.SuppressLint;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hao on 2020-01-06.
 * Email:itzihao@sina.com
 * 日志工具类
 */
public class LogUtils {

    static boolean DEBUG_ENABLE = true;

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    public static void logInit() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static void logD(String strMsg) {
        if (DEBUG_ENABLE) {
            Logger.d(strMsg);
        }
    }

    public static void logD(String strTag, String strMsg) {
        if (DEBUG_ENABLE) {
            Logger.d(strTag, strMsg);
        }
    }


    public static void logE(Throwable throwable, String message, Object... agrs) {
        if (DEBUG_ENABLE) {
            Logger.e(throwable, message, agrs);
        }
    }

    public static void logE(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.e(message, args);
        }else {
            Logger.e(message);
        }
    }

    public static void logI(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.i(message, args);
        }
    }

    public static void logV(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.v(message, args);
        }
    }

    public static void logW(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.v(message, args);
        }
    }

    public static void logWTF(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.wtf(message, args);
        }
    }

    public static void logJSON(String message) {
        if (DEBUG_ENABLE) {
            Logger.json(message);
        }
    }

    public static void logXML(String message) {
        if (DEBUG_ENABLE) {
            Logger.xml(message);
        }
    }

    public static void writeToFile(String meggage) {
        String fileName = "/sdcard/Meet/Meet.log";

        String log = mSimpleDateFormat.format(new Date()) + " \n" + meggage + "\n ============= \n";

        File fileGroup = new File("/sdcard/Meet/");
        if (!fileGroup.exists()) {
            fileGroup.mkdirs();
        }
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(fileName, true);
            BufferedWriter bufferedWriter =
                    new BufferedWriter(
                            new OutputStreamWriter(fileOutputStream, Charset.forName("gbk")));
            bufferedWriter.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
