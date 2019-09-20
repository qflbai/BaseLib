package com.example.myapplication.dagger.id.mudule;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 15:24
 * @Version: 1.0
 * @description:
 */

public  class DraggerMudule {

    Application getApplication(Application application){
        return application;
    }
}
