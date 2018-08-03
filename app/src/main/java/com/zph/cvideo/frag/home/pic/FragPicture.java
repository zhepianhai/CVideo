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
import com.zph.cvideo.base.BaseMainFragment;
import com.zph.cvideo.ui.takephoto.ActTakePhoto;
import com.zph.cvideo.utils.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author zph
 * @date 2018/3/22
 */


public class FragPicture extends BaseMainFragment {
    public static FragPicture getInstance() {
        return new FragPicture();
    }
    View mView;
    AppCompatButton mBtnTakePhoto;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView=inflater.inflate(R.layout.frag_picture, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnTakePhoto=mView.findViewById(R.id.frag_home_pic_btn_takephoto);
        mBtnTakePhoto.setOnClickListener(this);

    }
    @Override
    public int  setFragMentType() {
        return Constants.HOME_PIC;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag_home_pic_btn_takephoto:
                Intent intent=new Intent(getActivity(),ActTakePhoto.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
