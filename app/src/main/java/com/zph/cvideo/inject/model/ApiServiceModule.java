package com.zph.cvideo.inject.model;

import android.support.annotation.NonNull;

import com.zph.cvideo.data.network.API;
import com.zph.cvideo.data.network.AddressHelper;
import com.zph.cvideo.data.network.apiService.ApiServicePic;
import com.zph.cvideo.utils.CommonHeaderInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author zph
 * @date 2018/3/21
 */
@Module
public class ApiServiceModule {
    private static final String TAG = ApiServiceModule.class.getSimpleName();





    @Singleton
    @Provides
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return logging;
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient(CommonHeaderInterceptor commonHeaderInterceptor, HttpLoggingInterceptor httpLoggingInterceptor,  AddressHelper addressHelper) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(commonHeaderInterceptor);
        builder.addInterceptor(httpLoggingInterceptor);
//        builder.cookieJar(persistentCookieJar);
        //动态baseUrl
        RetrofitUrlManager.getInstance().putDomain(API.APP_GITHUB_DOMAIN_NAME,API.APP_GITHUB_DOMAIN);
//        RetrofitUrlManager.getInstance().putDomain(API.API_PIC_MOREEFFECT_NAME, API.API_PIC_MOREEFFECT);

//        if (!TextUtils.isEmpty(addressHelper.getPicBaseAddress())) {
//            RetrofitUrlManager.getInstance().putDomain(API.API_PIC_MOREEFFECT_NAME, addressHelper.getPicMoreEffectAddress());
//        }

        return RetrofitUrlManager.getInstance().with(builder).build();
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API.APP_GITHUB_DOMAIN)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }


    @Singleton
    @Provides
    ApiServicePic providesApiServicePic(Retrofit retrofit) {
        return retrofit.create(ApiServicePic.class);
    }

}
