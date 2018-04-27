package com.zph.cvideo.inject.model;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zph.cvideo.data.AppDataManager;
import com.zph.cvideo.data.DataManager;
import com.zph.cvideo.data.chache.CacheProviders;
import com.zph.cvideo.data.db.AppDbHelper;
import com.zph.cvideo.data.db.DbHelper;
import com.zph.cvideo.data.img.AppDataImgHelp;
import com.zph.cvideo.data.img.DataImgHelp;
import com.zph.cvideo.data.network.AddressHelper;
import com.zph.cvideo.data.network.ApiHelper;
import com.zph.cvideo.data.network.AppApiHelper;
import com.zph.cvideo.data.network.prefs.AppPreferencesHelper;
import com.zph.cvideo.data.network.prefs.PreferencesHelper;
import com.zph.cvideo.inject.ApplicationContext;
import com.zph.cvideo.inject.DatabaseInfo;
import com.zph.cvideo.inject.PreferenceInfo;
import com.zph.cvideo.utils.AppCacheUtils;
import com.zph.cvideo.utils.constants.Constants;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

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


    @Provides
    @PreferenceInfo
    String providePreferenceName(@ApplicationContext Context context) {
        return context.getPackageName() + "_preferences";
    }

    @Provides
    @DatabaseInfo
    String providesDatabaseName() {
        return Constants.DB_NAME;
    }


    @Singleton
    @Provides
    CacheProviders providesCacheProviders(@ApplicationContext Context context) {
        File cacheDir = AppCacheUtils.getRxCacheDir(context);
        return new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(CacheProviders.class);
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

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }


    @Singleton
    @Provides
    DataImgHelp providesDataImgHelp(AppDataImgHelp appDataImgHelp) {
        return appDataImgHelp;
    }


    @Singleton
    @Provides
    AddressHelper providesAddressHelper(PreferencesHelper preferencesHelper) {
        return new AddressHelper(preferencesHelper);
    }
    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }
}
