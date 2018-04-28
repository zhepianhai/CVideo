package com.zph.cvideo.ui.takephoto;

import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zph.cvideo.R;
import com.zph.cvideo.custom.MyCameraView;
import com.zph.cvideo.ui.MvpActivity;
import com.zph.cvideo.utils.DialogUtils;
import com.zph.cvideo.utils.UtilCamera;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ActTakePhoto extends MvpActivity<TakePhotoView, TakePhtotPresenter>  implements  TakePhotoView{

    @BindView(R.id.act_take_photo_camear_view)
    FrameLayout mFrameLayCamear;
    @BindView(R.id.act_take_photo_effect_layout)
    LinearLayout mLinearLayEffect;

    Unbinder butter;
    MyCameraView mCameraView;
    private Camera mCamera;
    private AlertDialog mAlertDialog;
    @Inject
    TakePhtotPresenter mTakePhotoProsenter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_take_photo);
        butter = ButterKnife.bind(this);
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
        if(mCamera==null){
            return;
        }

        this.mCamera=mCamera;
        mCameraView=new MyCameraView(this,mCamera);
        mFrameLayCamear.addView(mCameraView);
//        String category="pic_";
//        boolean pullToRefresh=false;
//        mTakePhotoProsenter.loadPicEffectLay(category,false);
    }

    @Override
    public void showLoading(boolean b) {
        if(mAlertDialog!=null) {
            mAlertDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing() && !isFinishing()) {
            mAlertDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!=butter){
            butter.unbind();
        }
        if(mCamera!=null){
            mCamera.setPreviewCallback(null) ;
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}
