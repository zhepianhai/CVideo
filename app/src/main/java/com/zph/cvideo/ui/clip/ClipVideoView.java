package com.zph.cvideo.ui.clip;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.HashMap;
import java.util.List;

/**
 * @author zph
 * Created by zph on 2018/8/3.
 */

 interface ClipVideoView  extends MvpView {

     void onStart();
     void onEnd();
     void onLoadSuccess(List<HashMap<String,String>> mapList);
}
