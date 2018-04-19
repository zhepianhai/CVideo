package com.zph.cvideo.ui;

import android.arch.lifecycle.Lifecycle;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.zph.cvideo.data.AppDataManager;

/**
 * @author zph
 * @date 2018/3/22
 */

public class BasePresenter {
    protected LifecycleProvider<Lifecycle.Event> provider;
    protected AppDataManager appDataManager;
    public BasePresenter(LifecycleProvider<Lifecycle.Event> provider) {
        this.provider = provider;
    }

    public BasePresenter(LifecycleProvider<Lifecycle.Event> provider, AppDataManager appDataManager) {
        this.provider = provider;
        this.appDataManager = appDataManager;
    }

    public BasePresenter() {
    }
}
