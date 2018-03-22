package com.zph.cvideo.base;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.zph.cvideo.inject.PerActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * @author zph
 * @date 2018/3/22
 */
@PerActivity
public class BaseMainPresenter extends MvpBasePresenter<BaseMainView> implements IBaseMain {
    @Inject
    public BaseMainPresenter() {
    }
}
