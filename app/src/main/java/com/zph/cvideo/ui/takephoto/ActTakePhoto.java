package com.zph.cvideo.ui.takephoto;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zph.cvideo.R;
import com.zph.cvideo.custom.MyCameraView;
import com.zph.cvideo.ui.MvpActivity;
import com.zph.cvideo.utils.DialogUtils;
import com.zph.cvideo.utils.UtilCamera;
import com.zph.cvideo.utils.constants.PermissionConstants;
import com.zph.lib.camearlibrary.ZPHCameraView;
import com.zph.lib.camearlibrary.listener.ZPHCameraListener;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ActTakePhoto extends MvpActivity<TakePhotoView, TakePhtotPresenter>  implements  TakePhotoView{


    Unbinder butter;
    private AlertDialog mAlertDialog;
    @Inject
    TakePhtotPresenter mTakePhotoProsenter;

    @BindView(R.id.act_take_photo_zphcamearview)
    ZPHCameraView mZPHCameraView;
    private boolean granted = false;
    private int mPermisionCode = 300;
    private int mPermisionReqCode = 400;
    private String[] mPermission = PermissionConstants.getPermissions(PermissionConstants.CAMERA);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_act_take_photo);
        butter = ButterKnife.bind(this);
        makeCaneraPermision();
        mAlertDialog = DialogUtils.initLodingDialog(this, "加載中");
    }
    private  void initCameraView() {
        if(UtilCamera.isCameraRuning(getApplicationContext())){
            //设置视频保存路径
            mZPHCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "ZPHCamera");

            //JCameraView监听
            mZPHCameraView.setJCameraLisenter(new ZPHCameraListener() {
                @Override
                public void captureSuccess(Bitmap bitmap) {
                    //获取图片bitmap
                    Log.i("JCameraView", "bitmap = " + bitmap.getWidth());
                }

                @Override
                public void recordSuccess(String url, Bitmap firstFrame) {

                }


                @Override
                public void quit() {
                    //退出按钮
                    ActTakePhoto.this.finish();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatusBarTransparent();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (granted) {
            mZPHCameraView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mZPHCameraView.onPause();
    }
    @NonNull
    @Override
    public TakePhtotPresenter createPresenter() {
        getActivityComponent().inject(this);
        return mTakePhotoProsenter;
    }
    @Override
    public void initCamerFinsih( Camera mCamera) {
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
    }

    private void makeCaneraPermision() {
        if (!AndPermission.hasPermission(ActTakePhoto.this, mPermission)) {
            AndPermission.with(this)
                    .requestCode(mPermisionCode)
                    .permission(mPermission)
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                            AndPermission.rationaleDialog(ActTakePhoto.this, rationale).show();
                        }
                    })
                    .callback(listener)
                    .start();
        }else{
            this.initCameraView();
            mZPHCameraView.onResume();
        }
    }
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantedPermissions) {
            // 权限申请成功回调。
            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == mPermisionCode) {
                // TODO ...
                if (AndPermission.hasPermission(ActTakePhoto.this, grantedPermissions)) {
                    ActTakePhoto.this.initCameraView();
                    granted = true;
                    mZPHCameraView.onResume();
                } else {
                    granted = false;
                    AndPermission.defaultSettingDialog(ActTakePhoto.this, mPermisionReqCode).show();
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == mPermisionCode) {
                granted = false;
                // TODO ...
                if (!AndPermission.hasPermission(ActTakePhoto.this, deniedPermissions)) {
                    // 是否有不再提示并拒绝的权限。
                    if (AndPermission.hasAlwaysDeniedPermission(ActTakePhoto.this, deniedPermissions)) {
                        // 第一种：用AndPermission默认的提示语。
                        AndPermission.defaultSettingDialog(ActTakePhoto.this, mPermisionReqCode).show();
                    } else {
                        AndPermission.defaultSettingDialog(ActTakePhoto.this, mPermisionReqCode).show();
                    }
                }
                ActTakePhoto.this.finish();
            }
        }
    };



}
