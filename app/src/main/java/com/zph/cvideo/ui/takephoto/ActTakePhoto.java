package com.zph.cvideo.ui.takephoto;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zph.cvideo.R;
import com.zph.cvideo.ui.MvpActivity;

import javax.inject.Inject;

public class ActTakePhoto extends MvpActivity<TakePhotoView, TakePhtotPresenter> {

    @Inject
    TakePhtotPresenter mTakePhotoProsenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_take_photo);
    }

    @NonNull
    @Override
    public TakePhtotPresenter createPresenter() {
        getActivityComponent().inject(this);
        return mTakePhotoProsenter;
    }
}
