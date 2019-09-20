package com.example.myapplication.dagger.id;

import android.app.Application;

import com.example.myapplication.MainActivity;
import com.example.myapplication.dagger.id.mudule.DraggerMudule;
import com.qflbai.lib.base.repository.IDataRepository;
import com.qflbai.lib.di.component.AppComponent;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 11:02
 * @Version: 1.0
 * @description:
 */
@Component
public interface DraggerComponent {

    void inject(MainActivity activity);

    Application getApplication();


    @Component.Builder
    interface Builder{
        @BindsInstance
        DraggerComponent.Builder application(Application application);

        DraggerComponent build();
    }
}
