package com.zph.cvideo.ui;

import android.arch.lifecycle.Lifecycle;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * @author zph
 * @date 2018/3/22
 */

public class BasePresenter {
    protected LifecycleProvider<Lifecycle.Event> provider;
    public BasePresenter(LifecycleProvider<Lifecycle.Event> provider) {
        this.provider = provider;
    }

    public BasePresenter() {
    }
}
