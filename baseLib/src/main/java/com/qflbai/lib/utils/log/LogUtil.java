package com.qflbai.lib.utils.log;

import android.util.Log;

import com.qflbai.lib.LibBuildConfig;


/**
 * @author WenXian Bai
 * @Date: 2018/3/14.
 * @Description: log
 */
public class LogUtil {

    /**
     * 以级别为 v 的形式输出LOG
     */
    public static void v(String tag, String msg) {
        if (isDebug()) {
            Log.v(tag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String tag, String msg) {
        if (isDebug()) {
            Log.d(tag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String tag, String msg) {
        if (isDebug()) {
            Log.i(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String tag, String msg) {
        if (isDebug()) {
            Log.w(tag, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String tag, String msg) {
        if (isDebug()) {
            Log.e(tag, msg);
        }
    }

    private static boolean isDebug() {
        return LibBuildConfig.isDebug();
    }

    /**
     * 截断输出日志
     *
     * @param msg
     */
    public static void dLong(String tag, String msg) {
        if (!isDebug()) {
            return;
        }
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            Log.d(tag, msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.d(tag, logContent);
            }
            Log.d(tag, msg);// 打印剩余日志
        }
    }

}
