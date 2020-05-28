package com.qflbai.lib.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.qflbai.lib.utils.log.LogUtil;
import com.vondear.rxtool.RxFileTool;

import java.io.File;

/**
 * @author: qflbai
 * @CreateDate: 2019/11/29 11:52
 * @Version: 1.0
 * @description:
 */
public class AppFileUtil {
    /**
     * 根目录
     */
    public static final String FILE_ROOT = "yuanxin";
    /**
     * app根目录
     */
    public static final String FILE_APP_ROOT = "wzhhy";
    /**
     * img目录
     */
    public static final String FILE_IMG = "img";
    /**
     * 文件保存目录
     */
    public static final String FILE_DOWNLOAD = "download";

    /**
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context 上下文
     * @param type    文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
     */
    public static String getCacheDirectory(Context context, String type) {
        File appCacheDir = getExternalCacheDirectory(context, type);
        if (appCacheDir == null) {
            appCacheDir = getInternalCacheDirectory(context, type);
        }

        if (appCacheDir == null) {
            LogUtil.e("FileUtils", "getCacheDirectory fail ,the reason is mobile phone unknown exception !");
        } else {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                LogUtil.e("FileUtils", "getCacheDirectory fail ,the reason is make directory fail !");
            }
        }
        LogUtil.d("FileUtil", "appCacheDir===" + appCacheDir.getPath() + File.separator);
        return appCacheDir.getPath() + File.separator;
    }

    /**
     * 获取SD卡缓存目录
     *
     * @param context 上下文
     * @param type    文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *                否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     *                {@link Environment#DIRECTORY_MUSIC},
     *                {@link Environment#DIRECTORY_PODCASTS},
     *                {@link Environment#DIRECTORY_RINGTONES},
     *                {@link Environment#DIRECTORY_ALARMS},
     *                {@link Environment#DIRECTORY_NOTIFICATIONS},
     *                {@link Environment#DIRECTORY_PICTURES}, or
     *                {@link Environment#DIRECTORY_MOVIES}.or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    public static File getExternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (TextUtils.isEmpty(type)) {
                appCacheDir = context.getExternalCacheDir();
            } else {
                appCacheDir = context.getExternalFilesDir(type);
            }

            if (appCacheDir == null) {// 有些手机需要通过自定义目录
                appCacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/" + context.getPackageName() + "/cache/" + type);
            }

            if (appCacheDir == null) {
                LogUtil.e("FileUtils", "getExternalDirectory fail ,the reason is sdCard unknown exception !");
            } else {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    LogUtil.e("FileUtils", "getExternalDirectory fail ,the reason is make directory fail !");
                }
            }
        } else {

        }
        return appCacheDir;
    }

    /**
     * 获取内存缓存目录
     *
     * @param type 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    public static File getInternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (TextUtils.isEmpty(type)) {
            appCacheDir = context.getCacheDir();// /data/data/app_package_name/cache
        } else {
            appCacheDir = new File(context.getFilesDir(), type);// /data/data/app_package_name/files/type
        }

        if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
            LogUtil.e("FileUtils", "getInternalDirectory fail ,the reason is make directory fail !");
        }
        return appCacheDir;
    }


    public static void clearCacheFile(Context context, String fileName) {
        String filepath = getCacheDirectory(context, "");
        File file = new File(filepath + fileName);
        if (file.exists()) {
            file.delete();
        }

    }

    /**
     * 获取缓存图片目录
     *
     * @param context
     * @return
     */
    public static String getCacheIMGPath(Context context) {
        String cacheDirectory = getCacheDirectory(context, "");
        String imgPath = cacheDirectory + File.separator + FILE_IMG;

        if (!RxFileTool.isFileExists(imgPath)) {
            RxFileTool.initDirectory(imgPath);
        }
        return imgPath;
    }

    /**
     * 获取缓存下载路径
     *
     * @param context
     * @return
     */
    public static String getCacheDownloadPath(Context context) {
        String cacheDirectory = getCacheDirectory(context, "");
        String downloadPath = cacheDirectory + File.separator + FILE_DOWNLOAD;

        if (!RxFileTool.isFileExists(downloadPath)) {
            RxFileTool.initDirectory(downloadPath);
        }
        return downloadPath;
    }

    /**
     * 获取根目录
     *
     * @return
     */
    public static String getRootPath() {
        String rootPath = "";
        if (RxFileTool.isSDCardEnable()) {
            rootPath = RxFileTool.getSDCardPath();
        } else {
            rootPath = Environment.getDataDirectory().getAbsolutePath() + File.separator;
        }
        rootPath = rootPath + FILE_ROOT + File.separator + FILE_APP_ROOT;
        RxFileTool.createOrExistsDir(rootPath);
        return rootPath + File.separator;
    }

    /**
     * 获取图片路径
     *
     * @return
     */
    public static String getImgPath() {
        String rootPath = getRootPath();
        String imgPath = rootPath + FILE_IMG;
        RxFileTool.createOrExistsDir(imgPath);
        RxFileTool.initDirectory(imgPath);

        return imgPath;
    }

    /**
     * 获取下载路径
     *
     * @return
     */
    public static String getDownloadPath() {
        String rootPath = getRootPath();
        String downloadPath = rootPath + FILE_DOWNLOAD;
        RxFileTool.createOrExistsDir(downloadPath);

        return downloadPath;
    }
}
