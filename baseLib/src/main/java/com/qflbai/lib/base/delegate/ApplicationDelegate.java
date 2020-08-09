package com.qflbai.lib.base.delegate;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;


import com.qflbai.lib.di.component.AppComponent;
import com.qflbai.lib.di.component.DaggerAppComponent;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/19 0019 14:23
 * @Version: 1.0
 * @description:
 */
public class ApplicationDelegate implements IAppComponent,ApplicationLifecycle{
    private Application mApplication;

    private AppComponent mAppComponent;


    public ApplicationDelegate(@NonNull Application application){
        this.mApplication = application;
    }


    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    /**
     * 在{@link Application#onCreate()} 中执行，初始化{@link #mAppComponent}
     */
    @Override
    public void onCreate() {
        this.mAppComponent = DaggerAppComponent.builder()
                .application(mApplication)
                .build();
        //注入
        this.mAppComponent.inject(this);
    }

    /**
     * 在{@link Application#onTerminate()} 中执行
     */
    @Override
    public void onTerminate() {
        this.mAppComponent = null;
        this.mApplication = null;
    }

    /**
     * 在{@link Application#onLowMemory()} 中执行,低内存的时候执行。（非必须调用）
     */
    @Override
    public void onLowMemory() {

    }

    /**
     * 在{@link Application#onTrimMemory(int)} 中执行，清理内存时执行。（非必须调用）
     */
    @Override
    public void onTrimMemory(int level) {

    }

    /**
     * 获取{@link AppComponent}
     * @return {@link #mAppComponent}
     */
    @Override
    public AppComponent getAppComponent() {

        return mAppComponent;
    }
}
