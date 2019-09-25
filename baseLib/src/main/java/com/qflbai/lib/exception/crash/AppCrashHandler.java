package com.qflbai.lib.exception.crash;

import android.content.Context;

import com.qflbai.lib.LibBuildConfig;
import com.qflbai.lib.LibConfig;
import com.qflbai.lib.exception.ExceptionLogSave;


/**
 * @author WenXian Bai
 * @Date: 2018/3/22.
 * @Description: 异常捕获（用于没有进行try catch处理的异常）
 */

public class AppCrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     */
    private static AppCrashHandler appCrashHandler;
    /**
     * 上下文
     */
    private Context mContext;

    private AppCrashHandler() {
    }


    /**
     * 获取AppCrashHandler实例 ,单例模式
     */
    public synchronized static AppCrashHandler getInstance() {
        // 防止多线程访问安全，这里使用了双重锁
        if (appCrashHandler == null) {
            appCrashHandler = new AppCrashHandler();
        }
        return appCrashHandler;
    }

    /**
     * 初始化异常捕获（在BaseActivity 或 Appliction里初始化）
     *
     * @param context
     * @param path    文件保存路径
     */
    public void init(Context context, String path) {
        mPath = path;
        mContext = context;
        Thread.UncaughtExceptionHandler exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (exceptionHandler == this) {
            return;
        }
        mDefaultHandler = exceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void init(Context context) {
        if(LibBuildConfig.isDebug()){
            return;
        }
        mContext = context;
        Thread.UncaughtExceptionHandler exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (exceptionHandler == this) {
            return;
        }
        mDefaultHandler = exceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (mDefaultHandler != null && (LibBuildConfig.isDebug() || (!handleException(ex)))) {
            // 系统自己处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 结束进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }


    /**
     * 文件名
     */
    private String mFileName = LibConfig.getSuntechPath();
    /**
     * 文件保存路径
     */
    private String mPath = LibConfig.getDOWNLOAD();

    /**
     * 异常处理
     *
     * @param ex 异常
     * @return
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        ex.printStackTrace();
        new Thread() {
            @Override
            public void run() {
               // Looper.prepare();
                try {
                    ExceptionLogSave.getInstance().saveCrashInfoFile(mContext, mPath, mFileName, ex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
              //  Looper.loop();
            }
        }.start();

        return true;
    }
}
