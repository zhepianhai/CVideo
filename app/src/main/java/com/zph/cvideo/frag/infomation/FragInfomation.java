package com.zph.cvideo.frag.infomation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zph.cvideo.R;
import com.zph.cvideo.base.BaseMainFragment;
import com.zph.cvideo.data.model.Category;
import com.zph.cvideo.frag.home.FragHome;
import com.zph.cvideo.ui.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author zph
 * @date 2018/3/22
 */
public class FragInfomation extends BaseFragment {
    public static FragInfomation getInstance() {
        return new FragInfomation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_infomation, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
