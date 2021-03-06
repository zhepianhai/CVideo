package com.zph.cvideo.data;

import android.graphics.Bitmap;
import android.hardware.Camera;

import com.zph.cvideo.data.db.DbHelper;
import com.zph.cvideo.data.img.DataImgHelp;
import com.zph.cvideo.data.network.ApiHelper;
import com.zph.cvideo.data.network.prefs.PreferencesHelper;
import com.zph.cvideo.data.video.DataVideoHelp;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
/**
 *
 * @author zph
 * @date 2018/4/19
 */
@Singleton
public class AppDataManager implements DataManager {
    private  DataImgHelp mDataImgHelp;
    private DataVideoHelp mDataVideoHelp;
    private  ApiHelper mApiHelper;
    private PreferencesHelper  mPreferencesHelper;
    private  DbHelper mDbHelper;

    @Inject
    public  AppDataManager(DataImgHelp mDataImgHelp,ApiHelper mApiHelper,PreferencesHelper  mPreferencesHelper,DbHelper mDbHelper,DataVideoHelp mDataVideoHelp) {
        this.mApiHelper=mApiHelper;
        this.mDataImgHelp=mDataImgHelp;
        this.mPreferencesHelper=mPreferencesHelper;
        this.mDbHelper=mDbHelper;
        this.mDataVideoHelp=mDataVideoHelp;
    }

    @Override
    public Observable<List<Object>> getMorePicEffect(String category, boolean pullToRefresh) {
        return mApiHelper.getMorePicEffect(category,pullToRefresh);
    }

    @Override
    public Observable<String> getMorePictexture(String string) {
        return mApiHelper.getMorePictexture(string);
    }

    @Override
    public Bitmap takePhotoByCamer(String string) {
        return mDataImgHelp.takePhotoByCamer(string);
    }

    @Override
    public Camera initCamra() {
        return mDataImgHelp.initCamra();
    }

    @Override
    public void setPicMoreEffectAddress(String address) {
        mPreferencesHelper.setPicMoreEffectAddress(address);
    }

    @Override
    public String getPicMoreEffectAddress() {
        return mPreferencesHelper.getPicMoreEffectAddress();
    }

    @Override
    public void setPicBaseAddress(String address) {
        mPreferencesHelper.setPicBaseAddress(address);
    }

    @Override
    public String getPicBaseAddress() {
        return mPreferencesHelper.getPicBaseAddress();
    }

    @Override
    public void initCategory(int type, String[] value, String[] name) {

    }
    /**
     * Video
     * */
    @Override
    public Observable<List<HashMap<String,String>>> loadVideoFiled() {
      return mDataVideoHelp.loadVideoFiled();
    }
}
