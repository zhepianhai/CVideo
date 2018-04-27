package com.zph.cvideo.data.network;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 * @author zph
 * @date 2018/4/19
 */
public interface ApiHelper {
    //pic
    Observable<List<Object>> getMorePicEffect(String category, boolean pullToRefresh);
    Observable<String> getMorePictexture(String string);
}
