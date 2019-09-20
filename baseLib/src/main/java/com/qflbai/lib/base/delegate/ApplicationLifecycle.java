package com.qflbai.lib.base.delegate;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/19 0019 14:25
 * @Version: 1.0
 * @description:
 */
public interface ApplicationLifecycle {

    /**
     * 在{@link Application attachBaseContext(Context)} 中执行
     * @param base
     */
    void attachBaseContext(@NonNull Context base);
    /**
     * 在{@link Application#onCreate()} 中执行
     */
    void onCreate();
    /**
     * 在{@link Application#onTerminate()} 中执行
     */
    void onTerminate();
    /**
     * 在{@link Application#onLowMemory()} 中执行
     */
    void onLowMemory();
    /**
     * 在{@link Application#onTrimMemory(int)} 中执行
     */
    void onTrimMemory(int level);
}
