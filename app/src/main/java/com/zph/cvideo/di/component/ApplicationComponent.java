package com.zph.cvideo.di.component;

import android.content.Context;

import com.google.gson.Gson;
import com.zph.cvideo.MyApplication;
import com.zph.cvideo.di.ApplicationContext;
import com.zph.cvideo.di.module.ApiServiceModule;
import com.zph.cvideo.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author zph
 * @date 2018/3/20
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiServiceModule.class})
public interface ApplicationComponent {
    void inject(MyApplication myApplication);


    @ApplicationContext
    Context getContext();


    Gson getGson();

}
