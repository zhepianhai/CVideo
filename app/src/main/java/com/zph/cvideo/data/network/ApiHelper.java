package com.zph.cvideo.data.network;

import io.reactivex.Observable;

/**
 *
 * @author zph
 * @date 2018/4/19
 */
public interface ApiHelper {
    //pic
    Observable<String> getMorePicEffect(String string);
    Observable<String> getMorePictexture(String string);
}
