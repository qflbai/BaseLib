package com.qflbai.lib.di.mudule;

import androidx.lifecycle.ViewModel;

import com.qflbai.lib.base.repository.BaseRepository;
import com.qflbai.lib.base.repository.IDataRepository;
import com.qflbai.lib.base.viewmodel.BaseViewModel;
import com.qflbai.lib.base.viewmodel.DataViewModel;
import com.qflbai.lib.di.scope.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/19 0019 11:15
 * @Version: 1.0
 * @description:
 */
@Module(includes = {ViewModelFactoryModule.class})
public abstract class BaseAppModule {
    @Binds
    abstract IDataRepository bindDataRepository(BaseRepository dataRepository);

    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel.class)
    abstract ViewModel bindBaseViewModel(BaseViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DataViewModel.class)
    abstract ViewModel bindDataViewModel(DataViewModel viewModel);

}
