package com.zph.cvideo.ui.clip;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zph.cvideo.R;
import com.zph.cvideo.adpter.AdpGridView;
import com.zph.cvideo.define.CONST_QUERY;
import com.zph.cvideo.ffmpeng.FfmpegTool;
import com.zph.cvideo.ui.MvpActivity;
import com.zph.cvideo.ui.takephoto.ActTakePhoto;
import com.zph.cvideo.utils.DialogUtils;
import com.zph.cvideo.utils.constants.PermissionConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ActVideoClip extends MvpActivity<ClipVideoView, ClipVideoPresenter> implements  ClipVideoView, AdapterView.OnItemClickListener {


    @Inject
    ClipVideoPresenter mTakePhotoProsenter;


    private LinearLayout mLayList;
    GridView mGridView;
    private AdpGridView mAdpGridView;
    private ArrayList<HashMap<String,String>> mArrayData;
    private Dialog mDialog;

    private boolean granted = false;
    private int mPermisionCode = 300;
    private int mPermisionReqCode = 400;
    private String[] mPermission = PermissionConstants.getPermissions(PermissionConstants.STORAGE);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initVar();
        this.initView();
        makeCaneraPermision();
    }

    @SuppressLint("ResourceAsColor")
    private void initVar() {
        setStatusBarColor(getResources().getColor(R.color.colorHome));
        mArrayData=new ArrayList<>();
        mDialog= DialogUtils.initLodingDialog(this,"loadding");
    }
    private void initView() {
        this.setNavBtnRightType(MvpActivity.NAVBTNRIGHT_TYPE_HOME);
        mNavTxtTitle.setText(this.getString(R.string.app_act_video_clip));
        // Main
        LinearLayout layShow = (LinearLayout) LinearLayout.inflate(this, R.layout.activity_act_video_clip, null);
        mViewMain.addView(layShow, new LinearLayout.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));

        mLayList=this.findViewById(R.id.act_video_clip_lay_list);
        mGridView=this.findViewById(R.id.act_video_cilp_gridview);
        mAdpGridView=new AdpGridView(this,mArrayData,R.layout.item_grid_video);
        mGridView.setAdapter(mAdpGridView);
        mGridView.setOnItemClickListener(this);
    }
    private void makeCaneraPermision() {
        if (!AndPermission.hasPermission(ActVideoClip.this, mPermission)) {
            AndPermission.with(this)
                    .requestCode(mPermisionCode)
                    .permission(mPermission)
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                            AndPermission.rationaleDialog(ActVideoClip.this, rationale).show();
                        }
                    })
                    .callback(listener)
                    .start();
        }else{
            this.loadVideoFiled();

        }
    }
    private void loadVideoFiled() {
        mTakePhotoProsenter.loadAudioFiled();
    }

    @NonNull
    @Override
    public ClipVideoPresenter createPresenter() {
        getActivityComponent().inject(this);
        return mTakePhotoProsenter;
    }

    @Override
    public void onStart() {
        if(mDialog!=null&&!mDialog.isShowing()){
            mDialog.show();
        }
        super.onStart();

    }

    @Override
    public void onEnd() {
        if(mDialog!=null &&mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void onLoadSuccess(List<HashMap<String, String>> mapList) {
        if(mapList!=null&&mapList.size()>0){
            mArrayData.clear();
            mArrayData.addAll(mapList);
            mAdpGridView.notifyDataSetChanged();
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
                if (AndPermission.hasPermission(ActVideoClip.this, grantedPermissions)) {
                    ActVideoClip.this.loadVideoFiled();
                    granted = true;

                } else {
                    granted = false;
                    AndPermission.defaultSettingDialog(ActVideoClip.this, mPermisionReqCode).show();
                }
            }
        }
        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == mPermisionCode) {
                granted = false;
                // TODO ...
                if (!AndPermission.hasPermission(ActVideoClip.this, deniedPermissions)) {
                    // 是否有不再提示并拒绝的权限。
                    if (AndPermission.hasAlwaysDeniedPermission(ActVideoClip.this, deniedPermissions)) {
                        // 第一种：用AndPermission默认的提示语。
                        AndPermission.defaultSettingDialog(ActVideoClip.this, mPermisionReqCode).show();
                    } else {
                        AndPermission.defaultSettingDialog(ActVideoClip.this, mPermisionReqCode).show();
                    }
                }
                ActVideoClip.this.finish();
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
        new Thread(){
            @Override
            public void run() {

        String basePath = Environment.getExternalStorageDirectory().getPath();
                String videoPath=mArrayData.get(position).get(CONST_QUERY.VIDEO_IMAGE_PATH);
        String dir=basePath+ File.separator+"test"+File.separator;
        String cmd2 = String.format("ffmpeg -ss 00:00:10 -i "+ videoPath+" -f image2 -y  "+basePath+"/test.jpg");
        String regulation="[ \\t]+";
        Log.i("MainActivity","cmd2:"+cmd2);
        final String[] split2 = cmd2.split(regulation);

                int result= FfmpegTool.cmdRun(split2);
                Log.i("MainActivity","result:"+result);
            }
        }.start();

    }
}
