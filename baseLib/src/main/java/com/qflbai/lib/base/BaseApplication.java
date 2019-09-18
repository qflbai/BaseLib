package com.qflbai.lib.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qflbai.lib.LibBuildConfig;


/**
 * @author WenXian Bai
 * @Date: 2018/3/22.
 * @Description: 全局应用APP状态基类
 */

public class BaseApplication extends Application {

    /**
     * 上下文对象
     */
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initARouter();
    }


    public static Context getAPPContext(){
        return mContext;
    }

    private void initARouter() {
        if (LibBuildConfig.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
