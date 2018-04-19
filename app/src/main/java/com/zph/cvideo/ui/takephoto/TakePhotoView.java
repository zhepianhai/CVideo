package com.zph.cvideo.ui.takephoto;

import android.hardware.Camera;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 *
 * @author zph
 * @date 2018/3/23
 */

public interface TakePhotoView extends MvpView {
    void initCamerFinsih( Camera mCamera);

    void showLoading(boolean b);
    void dismissDialog();
}
