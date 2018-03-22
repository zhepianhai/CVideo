package com.zph.cvideo.ui.main;

import com.zph.cvideo.inject.PerActivity;
import com.zph.cvideo.ui.MvpBasePresenter;

import javax.inject.Inject;

/**
 * @author zph
 * @date 2018/3/20
 */
@PerActivity
public class MainPresenter extends MvpBasePresenter<MainView> implements IMain {
    @Inject
    public MainPresenter() {
    }
}
