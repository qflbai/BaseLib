package com.example.myapplication.di.mudule;

import com.example.myapplication.TestActivity;
import com.qflbai.lib.di.component.AbsLifecycleActivitySubcomponent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 9:49
 * @Version: 1.0
 * @description:
 */
@Module(subcomponents = AbsLifecycleActivitySubcomponent.class)
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract TestActivity contributeTestActivity();
}
