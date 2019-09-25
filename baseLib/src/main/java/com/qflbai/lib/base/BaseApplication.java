package com.qflbai.lib.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.qflbai.lib.LibBuildConfig;
import com.qflbai.lib.base.delegate.ApplicationDelegate;
import com.qflbai.lib.base.delegate.IAppComponent;
import com.qflbai.lib.di.component.AppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.internal.Preconditions;


/**
 * @author WenXian Bai
 * @Date: 2018/3/22.
 * @Description: 全局应用APP状态基类
 */

public class BaseApplication extends Application implements IAppComponent, HasAndroidInjector {

    /**
     * 上下文对象
     */
    private static Context mContext;
    /**
     * Dagger.Android 注入
     */
    @Inject
    DispatchingAndroidInjector<Object> mAndroidInjector;
    /**
     * Application 代理 规避项目中集成了其它第三方类或其它原因，不能集成本类{@link BaseApplication}时，
     * 参照本类实现 {@link ApplicationDelegate} 即可初始化MVVMFrame框架配置信息。
     */
    private ApplicationDelegate mApplicationDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        mApplicationDelegate = new ApplicationDelegate(this);
        mApplicationDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApplicationDelegate.onCreate();

        //initARouter();
    }

    private void initARouter() {
      //  if (LibBuildConfig.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
         //   ARouter.openLog();     // 打印日志
         //   ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
       // }
        //ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //ARouter.getInstance().destroy();
        mApplicationDelegate.onTerminate();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mApplicationDelegate.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        mApplicationDelegate.onTrimMemory(level);
    }

    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mApplicationDelegate,"%s cannot be null",ApplicationDelegate.class.getName());
        return mApplicationDelegate.getAppComponent();
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return mAndroidInjector;
    }

    public static Context getAPPContext() {
        return mContext;
    }
}
