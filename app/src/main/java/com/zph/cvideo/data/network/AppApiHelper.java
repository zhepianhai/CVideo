package com.zph.cvideo.data.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by 3119 on 2018/4/20.
 */
@Singleton
public class AppApiHelper  implements ApiHelper{
//    String string;
    @Inject
    public AppApiHelper(){

    }

//    public AppApiHelper(String string){
//        this.string=string;
//    }

    @Override
    public Observable<String> getMorePicEffect(String string) {
        Observable<String> ast = null;
        return ast;
    }

    @Override
    public Observable<String> getMorePictexture(String string) {
        return null;
    }
}
