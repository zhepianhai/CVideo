package com.zph.cvideo.ui.takephoto;

import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

import com.zph.cvideo.R;
import com.zph.cvideo.custom.MyCameraView;
import com.zph.cvideo.ui.MvpActivity;
import com.zph.cvideo.utils.DialogUtils;
import com.zph.cvideo.utils.UtilCamera;

import javax.inject.Inject;

import butterknife.BindView;

public class ActTakePhoto extends MvpActivity<TakePhotoView, TakePhtotPresenter>  implements  TakePhotoView{

    MyCameraView mCameraView;
    private Camera mCamera;
    private AlertDialog mAlertDialog;
    @Inject
    TakePhtotPresenter mTakePhotoProsenter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_take_photo);
        setStatusBarColor(getResources().getColor(R.color.white));
        if(UtilCamera.isCameraRuning(getApplicationContext())){
            mTakePhotoProsenter.initCamera(mCamera);
        }
        mAlertDialog = DialogUtils.initLodingDialog(this, "加載中");
    }

    @NonNull
    @Override
    public TakePhtotPresenter createPresenter() {
        getActivityComponent().inject(this);
        return mTakePhotoProsenter;
    }
    @Override
    public void initCamerFinsih( Camera mCamera) {
        this.mCamera=mCamera;
//        mTakePhotoProsenter.loadPicEffectLay();
    }

    @Override
    public void showLoading(boolean b) {
        mAlertDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing() && !isFinishing()) {
            mAlertDialog.dismiss();
        }
    }
}
