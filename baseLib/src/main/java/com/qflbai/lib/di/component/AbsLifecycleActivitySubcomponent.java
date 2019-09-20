package com.qflbai.lib.di.component;

import com.qflbai.lib.base.activity.AbsLifecycleActivity;
import com.qflbai.lib.base.viewmodel.BaseViewModel;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 9:01
 * @Version: 1.0
 * @description:
 */
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface AbsLifecycleActivitySubcomponent extends AndroidInjector<AbsLifecycleActivity<BaseViewModel>> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<AbsLifecycleActivity<BaseViewModel>>{

    }
}
