package com.qflbai.lib.base.model;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author: qflbai
 * @CreateDate: 2019/9/19 0019 10:26
 * @Version: 1.0
 * @description:
 */
public interface IModel extends LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();
}
