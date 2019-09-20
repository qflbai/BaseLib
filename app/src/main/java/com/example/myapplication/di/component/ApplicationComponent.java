package com.example.myapplication.di.component;

import com.example.myapplication.App;
import com.example.myapplication.di.mudule.AppModule;
import com.qflbai.lib.di.component.AppComponent;
import com.qflbai.lib.di.scope.ApplicationScope;

import dagger.Component;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 9:12
 * @Version: 1.0
 * @description:
 */
@ApplicationScope
@Component(dependencies = AppComponent.class,modules = {AppModule.class})
public interface ApplicationComponent {
    void inject(App app);
}
