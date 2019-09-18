package com.qflbai.lib;

import android.os.Environment;

import java.io.File;

/**
 * @author WenXian Bai
 * @Date: 2018/3/15.
 * @Description: 库配置类
 */
public class LibConfig {
    /**
     * 应用文件夹名
     */
    private final static String APP_FOLDER_NAME = "suntech";

    /**
     * 手机存储卡根目录
     */
    public final static String ROOT_PATH = Environment
            .getExternalStorageDirectory().getPath();
    /**
     * 应用存储卡目录
     */
    public final static String SUNTECH_PATH = ROOT_PATH + File.separator + APP_FOLDER_NAME;
    /**
     * 未捕获的异常文件名
     */
    public final static String FILE_NAME_CRASH = "crash.log";
    /**
     * 异常文件名
     */
    public final static String FILE_NAME_EX = "ex.log";

    /**
     * 下载目录
     */
    public final static String DOWNLOAD = SUNTECH_PATH+"download";

    /**
     * 总部路径
     */
    public static class Headquarters {
        /**
         * 总部文件夹名
         */
        private final static String APP_FOLDER_NAME = "hwadquarters";
        /**
         * 总部存储卡目录
         */
        public final static String HWADQUARTERS_PATH = SUNTECH_PATH + File.separator + APP_FOLDER_NAME;
    }

}
