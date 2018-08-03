package com.zph.cvideo.ui.clip;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.zph.cvideo.R;
import com.zph.cvideo.ui.MvpActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class ActVideoClip extends MvpActivity<ClipVideoView, ClipVideoPresenter> implements  ClipVideoView{


    @Inject
    ClipVideoPresenter mTakePhotoProsenter;

    @BindView(R.id.act_video_cilp_gridview)
    GridView mGridView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initVar();
        this.initView();
    }
    @SuppressLint("ResourceAsColor")
    private void initVar() {
        setStatusBarColor(getResources().getColor(R.color.colorHome));
    }

    private void initView() {
        this.setNavBtnRightType(MvpActivity.NAVBTNRIGHT_TYPE_HOME);
        mNavTxtTitle.setText(this.getString(R.string.app_act_video_clip));

        // Main
        LinearLayout layShow = (LinearLayout) LinearLayout.inflate(this, R.layout.activity_act_video_clip, null);
        mViewMain.addView(layShow, new LinearLayout.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));




    }

    @NonNull
    @Override
    public ClipVideoPresenter createPresenter() {
        getActivityComponent().inject(this);
        return mTakePhotoProsenter;
    }
}
