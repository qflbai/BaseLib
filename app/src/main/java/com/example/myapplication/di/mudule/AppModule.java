package com.example.myapplication.di.mudule;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 9:29
 * @Version: 1.0
 * @description:
 */

import com.qflbai.lib.di.mudule.ViewModelFactoryModule;

import dagger.Module;

@Module(includes = {ViewModelFactoryModule.class,ViewModelModule.class,ActivityModule.class})
public class AppModule {
}
