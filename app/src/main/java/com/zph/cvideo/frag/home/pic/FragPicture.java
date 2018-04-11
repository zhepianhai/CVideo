package com.zph.cvideo.frag.home.pic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zph.cvideo.R;
import com.zph.cvideo.frag.home.audio.FragAudio;
import com.zph.cvideo.ui.BaseFragment;
import com.zph.cvideo.ui.takephoto.ActTakePhoto;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author zph
 * @date 2018/3/22
 */


public class FragPicture extends BaseFragment {
    public static FragPicture getInstance() {
        return new FragPicture();
    }

    Unbinder unbinder;
    @BindView(R.id.frag_home_pic_btn_takephoto)
    AppCompatButton mBtnTakePhoto;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag_picture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }
    @OnClick(R.id.frag_home_pic_btn_takephoto)
    public void tackPhoto(){
        Intent intent=new Intent(getActivity(),ActTakePhoto.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
