package com.zph.cvideo.inject.model;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zph.cvideo.data.AppDataManager;
import com.zph.cvideo.data.DataManager;
import com.zph.cvideo.data.img.AppDataImgHelp;
import com.zph.cvideo.data.img.DataImgHelp;
import com.zph.cvideo.data.network.ApiHelper;
import com.zph.cvideo.data.network.AppApiHelper;
import com.zph.cvideo.inject.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author zph
 * @date 2018/3/21
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    public Application providesApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    public Context providesContext() {
        return mApplication;
    }


    @Singleton
    @Provides
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Singleton
    @Provides
    ApiHelper providesApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }


    @Singleton
    @Provides
    DataImgHelp providesApiHelper(AppDataImgHelp appDataImgHelp) {
        return appDataImgHelp;
    }

}
