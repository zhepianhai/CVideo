package com.zph.cvideo.data.chache;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.DynamicKeyGroup;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictDynamicKeyGroup;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.ProviderKey;
import io.rx_cache2.Reply;

/**
 *
 * @author zph
 * @date 2018/4/26
 * RxJavaCache 缓存
 */

public interface CacheProviders {
    /**
     * 缓存自动过期时间60分钟
     */
    int CACHE_TIME = 60;

    /**
     * 缓存主页面
     *
     * @param indexPhp      主页oab
     * @param evictProvider 缓存控制
     * @return oab对象
     */
    @ProviderKey("porn91VideoIndexPhp")
    @LifeCache(duration = CACHE_TIME, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<String>> getIndexPhp(Observable<String> indexPhp, EvictProvider evictProvider);

    /**
     * 缓存播放视频界面，无限制过期时间，除非手动清除
     *
     * @param playVideoPage 播放视频页oab
     * @param viewKey       观看的key
     * @param evictViewKey  缓存控制
     * @return oab对象
     */
    @ProviderKey("playVideo")
    Observable<Reply<String>> getVideoPlayPage(Observable<String> playVideoPage, DynamicKey viewKey, EvictDynamicKey evictViewKey);

    /**
     * 获取相应类别数据
     *
     * @param getCategoryPage    页码
     * @param filterPageCategory 类别
     * @param evictFilter        缓存控制
     * @return oab对象
     */
    @ProviderKey("categoryPage")
    @LifeCache(duration = CACHE_TIME, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<String>> getCategoryPage(Observable<String> getCategoryPage, DynamicKeyGroup filterPageCategory, EvictDynamicKey evictFilter);

    /**
     * 获取最近更新数据
     *
     * @param stringObservable   ob
     * @param filterPageCategory 页码
     * @param evictFilter        缓存控制
     * @return oab对象
     */
    @ProviderKey("recentUpdate")
    @LifeCache(duration = CACHE_TIME, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<String>> getRecentUpdates(Observable<String> stringObservable, DynamicKeyGroup filterPageCategory, EvictDynamicKey evictFilter);



    @ProviderKey("cache_v113")
    @LifeCache(duration = CACHE_TIME, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<String>> cacheWithLimitTime(Observable<String> observable, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);

    @ProviderKey("cache_v113")
    Observable<Reply<String>> cacheWithNoLimitTime(Observable<String> observable, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);

    @ProviderKey("cache_v113")
    @LifeCache(duration = CACHE_TIME, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<String>> cacheWithLimitTime(Observable<String> observable, DynamicKeyGroup dynamicKeyGroup, EvictDynamicKeyGroup evictDynamicKeyGroup);

    @ProviderKey("cache_v113")
    Observable<Reply<String>> cacheWithNoLimitTime(Observable<String> observable, DynamicKeyGroup dynamicKeyGroup, EvictDynamicKeyGroup evictDynamicKeyGroup);
}
