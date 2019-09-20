package com.qflbai.lib.base.delegate;

import com.qflbai.lib.di.component.AppComponent;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/19 0019 14:23
 * @Version: 1.0
 * @description:
 */
public interface IAppComponent {
    /**
     * 提供{@link AppComponent}
     * @return
     */
    AppComponent getAppComponent();
}
