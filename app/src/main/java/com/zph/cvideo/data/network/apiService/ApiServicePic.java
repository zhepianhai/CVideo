package com.zph.cvideo.data.network.apiService;


import com.zph.cvideo.data.network.API;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/**
 * @author zph
 * @date 2018/4/26
 */

public interface ApiServicePic {


    @Headers({"Domain-Name: " + API.API_PIC_MOREEFFECT})
    @GET
    Observable<String> pic_getMoreEffect(@Url String url);
}
