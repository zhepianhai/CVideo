package com.zph.cvideo.ui.clip;

import android.arch.lifecycle.Lifecycle;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.zph.cvideo.data.DataManager;
import com.zph.cvideo.ui.MvpBasePresenter;

import javax.inject.Inject;

/**
 * Created by zph on 2018/8/3.
 */

public class ClipVideoPresenter extends MvpBasePresenter<ClipVideoView> implements IClipVideo {
    private DataManager mDataManager;
    @Inject
    public ClipVideoPresenter(LifecycleProvider<Lifecycle.Event> provider, DataManager dataManager) {
        super(provider);
        this.mDataManager = dataManager;
    }
}
