package com.zph.cvideo.data.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by 3119 on 2018/4/20.
 */
@Singleton
public class AppApiHelper  implements ApiHelper{

    @Inject
    public AppApiHelper(){

    }

    @Override
    public Observable<String> getMorePicEffect() {
        return null;
    }

    @Override
    public Observable<String> getMorePictexture() {
        return null;
    }
}
