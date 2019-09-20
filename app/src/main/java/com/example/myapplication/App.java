package com.example.myapplication;

import com.example.myapplication.di.component.ApplicationComponent;
import com.example.myapplication.di.component.DaggerApplicationComponent;
import com.qflbai.lib.base.BaseApplication;


/**
 * @author: qflbai
 * @CreateDate: 2019/9/11 0011 17:43
 * @Version: 1.0
 * @description:
 */
public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationComponent applicationComponent = (ApplicationComponent) DaggerApplicationComponent.builder()
                .appComponent(getAppComponent())
                .build();
        //注入
        applicationComponent.inject(this);

    }
}
