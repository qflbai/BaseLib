package com.qflbai.lib.di.mudule;

import androidx.lifecycle.ViewModelProvider;

import com.qflbai.lib.base.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjectionModule;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/19 0019 14:01
 * @Version: 1.0
 * @description:
 */
@Module(includes = {AndroidInjectionModule.class})
public abstract class ViewModelFactoryModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
}
