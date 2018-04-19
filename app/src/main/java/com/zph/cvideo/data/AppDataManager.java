package com.zph.cvideo.data;

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
    private final DataImgHelp mDataImgHelp;
    private final ApiHelper mApiHelper;
    @Inject
    public  AppDataManager(DataImgHelp mDataImgHelp,ApiHelper mApiHelper) {
        this.mApiHelper=mApiHelper;
        this.mDataImgHelp=mDataImgHelp;
    }

    @Override
    public Observable<String> getMorePicEffect() {
        return null;
    }

    @Override
    public Observable<String> getMorePictexture() {
        return null;
    }

    @Override
    public void takePhotoByCamer() {

    }
}
