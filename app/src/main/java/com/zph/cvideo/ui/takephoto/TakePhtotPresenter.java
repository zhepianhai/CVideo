package com.zph.cvideo.ui.takephoto;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.util.Log;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.zph.cvideo.data.DataManager;
import com.zph.cvideo.rxjava.CallBackWrapper;
import com.zph.cvideo.rxjava.RxSchedulersHelper;
import com.zph.cvideo.ui.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author zph
 * @date 2018/3/23
 */

public class TakePhtotPresenter extends MvpBasePresenter<TakePhotoView> implements ITakePhoto {
    private DataManager mDataManager;
//    private LifecycleProvider<Lifecycle.Event> provider;
    @Inject
    public TakePhtotPresenter(LifecycleProvider<Lifecycle.Event> provider, DataManager dataManager) {
        super(provider);
        this.mDataManager = dataManager;
    }

    @Override
    public void initCamera(Camera camera) {


        ifViewAttached(new ViewAction<TakePhotoView>() {
            @Override
            public void run(@NonNull TakePhotoView view) {
                view.initCamerFinsih(mDataManager.initCamra());
            }
        });
    }

    @Override
    public void transformCamer(Camera camera) {

    }

    @Override
    public void loadPicEffectLay(String category, boolean pullToRefresh) {
        String a="";
        if(provider==null){
            return;
        }
        mDataManager.getMorePicEffect(category,pullToRefresh)
                .compose(RxSchedulersHelper.<List<Object>> ioMainThread())
                .compose(provider.
                        <List<Object>>bindUntilEvent(
                                Lifecycle.Event.ON_DESTROY))
                .subscribe(new CallBackWrapper<List<Object>>() {

                    @Override
                    public void onBegin(Disposable d) {
                        ifViewAttached(new ViewAction<TakePhotoView>() {
                            @Override
                            public void run(@NonNull TakePhotoView view) {
                                view.showLoading(true);
                            }
                        });
                    }



                    @Override
                    public void onSuccess(List<Object> o) {
                        ifViewAttached(new ViewAction<TakePhotoView>() {
                            @Override
                            public void run(@NonNull TakePhotoView view) {
                                view.dismissDialog();
                            }
                        });
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                });
    }
}
