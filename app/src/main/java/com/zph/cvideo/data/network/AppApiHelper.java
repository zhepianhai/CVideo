package com.zph.cvideo.data.network;

import com.zph.cvideo.data.chache.CacheProviders;
import com.zph.cvideo.data.network.apiService.ApiServicePic;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;

/**
 * @author zph
 * @date 2018/4/20
 */
@Singleton
public class AppApiHelper  implements ApiHelper{

    private CacheProviders mCacheProviders;
    private ApiServicePic mApiServicePic;
    @Inject
    AppApiHelper(CacheProviders mCacheProviders,ApiServicePic mApiServicePic){
        this.mCacheProviders=mCacheProviders;
        this.mApiServicePic=mApiServicePic;
    }


    @Override
    public Observable<List<Object>> getMorePicEffect(String category, boolean pullToRefreshg) {
        DynamicKey dynamicKey = new DynamicKey(category);
        EvictDynamicKey evictDynamicKey = new EvictDynamicKey(pullToRefreshg);
        if ("index".equals(category)) {
            return action(mCacheProviders.cacheWithLimitTime(mApiServicePic.pic_getMoreEffect(
                    ""), dynamicKey, evictDynamicKey));
        } else {
            return action(mCacheProviders.cacheWithLimitTime(mApiServicePic.pic_getMoreEffect(
                    ""), dynamicKey, evictDynamicKey));
        }
    }

    @Override
    public Observable<String> getMorePictexture(String string) {
        return null;
    }



    private Observable<List<Object>> action(Observable<Reply<String>> observable) {
        return observable
                .map(new Function<Reply<String>, String>() {
                    @Override
                    public String apply(Reply<String> stringReply) throws Exception {
                        return null;
                    }
                })
                .map(new Function<String, List<Object>>() {
                    @Override
                    public List<Object> apply(String s) throws Exception {
//                        BaseResult<List<Object>> baseResult = ParsePigAv.videoList(s);
                        return null;
                    }
                });
    }

}
