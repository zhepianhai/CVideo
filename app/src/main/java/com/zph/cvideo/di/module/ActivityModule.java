package com.zph.cvideo.di.module;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.zph.cvideo.di.ActivityContext;
import com.zph.cvideo.utils.AppCacheUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author zph
 * @date 2018/3/20
 */
@Module
public class ActivityModule {
    private AppCompatActivity mAppCompatActivity;

    public ActivityModule(AppCompatActivity mAppCompatActivity) {
        this.mAppCompatActivity = mAppCompatActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mAppCompatActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mAppCompatActivity;
    }

    @Provides
    File providesVideoCacheDir(@ActivityContext Context context) {
        return AppCacheUtils.getVideoCacheDir(context);
    }

    @Provides
    List<Fragment> providesFragmentList() {
        return new ArrayList<>();
    }

    @Provides
    FragmentManager providesSupportFragmentManager(AppCompatActivity mAppCompatActivity) {
        return mAppCompatActivity.getSupportFragmentManager();
    }

    @Provides
    LifecycleProvider<Lifecycle.Event> providerLifecycleProvider(AppCompatActivity mAppCompatActivity) {
        return AndroidLifecycle.createLifecycleProvider(mAppCompatActivity);
    }
}
