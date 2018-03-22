package com.zph.cvideo;

import android.app.Application;

import com.zph.cvideo.inject.component.ApplicationComponent;
import com.zph.cvideo.inject.component.DaggerApplicationComponent;
import com.zph.cvideo.inject.model.ApplicationModule;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


/**
 * @author zph
 * @date 2018/3/20
 */

public class MyApplication  extends Application{

    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication myApplication;
    private ApplicationComponent applicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);
        BGASwipeBackHelper.init(this, null);

    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


}
