package com.zph.cvideo.ui.takephoto;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by 3119 on 2018/3/23.
 */

public interface TakePhotoView extends MvpView {

    void takePhoto();
    void save();
    void dismiss();
    void transformation();
    void initCamera();
}
