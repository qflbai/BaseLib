package com.qflbai.lib.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.qflbai.lib.utils.FileUtil;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WenXian Bai
 * @Date: 2018/3/22.
 * @Description: 异常日志保存
 */

public class ExceptionLogSave {

    /**
     * 单例
     */
    private static ExceptionLogSave mExceptionLogSave = null;

    private ExceptionLogSave() {
    }

    /**
     * 获取单例对象
     *
     * @return
     */
    public synchronized static ExceptionLogSave getInstance() {
        if (mExceptionLogSave == null) {
            mExceptionLogSave = new ExceptionLogSave();
        }
        return mExceptionLogSave;
    }

    /**
     * 收集设备参数信息
     *
     * @param context 上下文
     */
    private Map<String, String> collectDeviceInfo(Context context) {
        Map<String, String> mInfoMap = new HashMap<>();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName + "";
                String versionCode = pi.versionCode + "";
                mInfoMap.put("versionName", versionName);
                mInfoMap.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfoMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mInfoMap;
    }

    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 保存错误信息到文件中
     *
     * @param context   上下文对象
     * @param path      文件保存路径
     * @param fileName  文件名称
     * @param throwable 异常
     * @return
     * @throws Exception
     */
    public void saveCrashInfoFile(Context context, String path, String fileName, Throwable throwable) throws Exception {
        Map<String, String> mInfoMap = collectDeviceInfo(context);
        StringBuilder sb = new StringBuilder();
        try {
            String date = sDateFormat.format(new java.util.Date());
            sb.append("\r\n" + date + "\n");
            for (Map.Entry<String, String> entry : mInfoMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + ";  ");
            }
            sb.append("\n");
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);
            sb.append("\n");
            writeFile(path, fileName, sb.toString());
        } catch (Exception e) {
            sb.append("an error occured while writing file...\r\n");
            writeFile(path, fileName, sb.toString());
        }
    }

    /**
     * 写入到文本
     *
     * @param sb
     * @return
     * @throws Exception
     */
    private String writeFile(String path, String fileName, String sb) throws Exception {
        File file = FileUtil.createFile(path, fileName);
        FileUtil.write(file, sb);
        return fileName;
    }
}
