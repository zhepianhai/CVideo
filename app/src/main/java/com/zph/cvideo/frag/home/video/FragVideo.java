package com.zph.cvideo.frag.home.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zph.cvideo.R;
import com.zph.cvideo.base.BaseMainFragment;
import com.zph.cvideo.ui.BaseFragment;
import com.zph.cvideo.ui.clip.ActVideoClip;
import com.zph.cvideo.ui.takephoto.ActTakePhoto;
import com.zph.cvideo.utils.constants.Constants;

/**
 * @author zph
 * @date 2018/3/22
 */


public class FragVideo  extends BaseMainFragment {
    public static FragVideo getInstance() {
        return new FragVideo();
    }
    private View mView;
    private AppCompatButton mBtnClip;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView=inflater.inflate(R.layout.frag_video, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClip=mView.findViewById(R.id.frag_home_video_btn_clip);
        mBtnClip.setOnClickListener(this);

    }

    @Override
    public int  setFragMentType() {
        return Constants.HOME_VIDEO;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag_home_video_btn_clip:
                Intent intent=new Intent(getActivity(),ActVideoClip.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
