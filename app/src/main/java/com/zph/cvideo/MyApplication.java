package com.zph.cvideo;

import android.app.Application;


/**
 * @author zph
 * @date 2018/3/20
 */

public class MyApplication  extends Application{

    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        super.onCreate();
        myApplication = this;
    }
}
