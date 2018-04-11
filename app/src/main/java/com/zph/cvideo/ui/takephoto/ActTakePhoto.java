package com.zph.cvideo.ui.takephoto;

import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.zph.cvideo.R;
import com.zph.cvideo.custom.MyCameraView;
import com.zph.cvideo.ui.MvpActivity;
import com.zph.cvideo.utils.UtilCamera;

import javax.inject.Inject;

import butterknife.BindView;

public class ActTakePhoto extends MvpActivity<TakePhotoView, TakePhtotPresenter> {

    @BindView(R.id.view_act_take_photo_surface)
    MyCameraView mCameraView;
    private Camera mCamera;

    @Inject
    TakePhtotPresenter mTakePhotoProsenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_take_photo);
        if(UtilCamera.isCameraRuning(getApplicationContext())){
            mTakePhotoProsenter.initCamera(mCamera);
        }
    }

    @NonNull
    @Override
    public TakePhtotPresenter createPresenter() {
        getActivityComponent().inject(this);
        return mTakePhotoProsenter;
    }
}
