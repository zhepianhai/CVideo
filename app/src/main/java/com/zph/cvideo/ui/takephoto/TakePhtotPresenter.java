package com.zph.cvideo.ui.takephoto;

import android.content.Context;
import android.hardware.Camera;

import com.zph.cvideo.ui.MvpBasePresenter;

import javax.inject.Inject;

/**
 * Created by 3119 on 2018/3/23.
 */

public class TakePhtotPresenter  extends MvpBasePresenter<TakePhotoView> implements ITakePhoto{
    private Context mContext;
    @Inject
    public TakePhtotPresenter(){
    }

    @Override
    public void takephoto() {

    }

    @Override
    public void initCamera(Camera camera) {

    }
}
