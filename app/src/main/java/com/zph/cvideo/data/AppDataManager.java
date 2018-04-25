package com.zph.cvideo.data;

import android.graphics.Bitmap;

import com.zph.cvideo.data.img.DataImgHelp;
import com.zph.cvideo.data.network.ApiHelper;

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
    private  ApiHelper mApiHelper;


    @Inject
    public  AppDataManager(DataImgHelp mDataImgHelp,ApiHelper mApiHelper) {
        this.mApiHelper=mApiHelper;
        this.mDataImgHelp=mDataImgHelp;
    }

    @Override
    public Observable<String> getMorePicEffect(String string) {
        return mApiHelper.getMorePicEffect(string);
    }

    @Override
    public Observable<String> getMorePictexture(String string) {
        return mApiHelper.getMorePictexture(string);
    }

    @Override
    public Bitmap takePhotoByCamer(String string) {
        return mDataImgHelp.takePhotoByCamer(string);
    }
}
