package com.example.myapplication.di.mudule;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.test.TestViewModle;
import com.qflbai.lib.di.scope.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/20 0020 9:26
 * @Version: 1.0
 * @description:
 */
@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TestViewModle.class)
    abstract ViewModel bindTestViewModle(TestViewModle viewModel);

}
