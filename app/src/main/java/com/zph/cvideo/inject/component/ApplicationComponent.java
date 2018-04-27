package com.zph.cvideo.inject.component;

import android.content.Context;

import com.zph.cvideo.MyApplication;
import com.zph.cvideo.data.DataManager;
import com.zph.cvideo.data.network.AddressHelper;
import com.zph.cvideo.inject.ApplicationContext;
import com.zph.cvideo.inject.model.ApiServiceModule;
import com.zph.cvideo.inject.model.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

/**
 *
 * @author zph
 * @date 2018/3/21
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiServiceModule.class})
public interface ApplicationComponent{
    void inject(MyApplication myApplication);

    @ApplicationContext
    Context getContext();
    AddressHelper getAddressHelper();
    DataManager getDataManager();
}
