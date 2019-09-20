package com.qflbai.lib.di.component;

import android.app.Application;

import com.qflbai.lib.base.delegate.ApplicationDelegate;
import com.qflbai.lib.base.repository.IDataRepository;
import com.qflbai.lib.di.mudule.BaseAppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/19 0019 14:05
 * @Version: 1.0
 * @description:
 */
@Singleton
@Component(modules = {BaseAppModule.class})
public interface AppComponent {
    void inject(ApplicationDelegate applicationDelegate);

    Application getApplication();

    IDataRepository getDataRepository();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
