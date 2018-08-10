package com.zph.cvideo.ui.clip;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.zph.cvideo.data.DataManager;
import com.zph.cvideo.rxjava.CallBackWrapper;
import com.zph.cvideo.rxjava.RxSchedulersHelper;
import com.zph.cvideo.ui.MvpBasePresenter;
import com.zph.cvideo.ui.takephoto.TakePhotoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zph on 2018/8/3.
 */

public class ClipVideoPresenter extends MvpBasePresenter<ClipVideoView> implements IClipVideo {
    private DataManager mDataManager;
    @Inject
    public ClipVideoPresenter(LifecycleProvider<Lifecycle.Event> provider, DataManager dataManager) {
        super(provider);
        this.mDataManager = dataManager;
    }

    @Override
    public  void loadAudioFiled() {
        if(provider==null){
            return ;
        }
        mDataManager.loadVideoFiled()
                .compose(RxSchedulersHelper.<List<HashMap<String,String>>> ioMainThread())
                .compose(provider.
                        <List<HashMap<String,String>>>bindUntilEvent(
                                Lifecycle.Event.ON_DESTROY))
                .subscribe(new CallBackWrapper<List<HashMap<String,String>>>() {

                    @Override
                    public void onBegin(Disposable d) {
                        ifViewAttached(new ViewAction<ClipVideoView>() {
                            @Override
                            public void run(@NonNull ClipVideoView view) {
                                view.onStart();
                            }
                        });
                    }
                    @Override
                    public void onSuccess(final  List<HashMap<String,String>> o) {
                        ifViewAttached(new ViewAction<ClipVideoView>() {
                            @Override
                            public void run(@NonNull ClipVideoView view) {
                                view.onLoadSuccess(o);
                                view.onEnd();
                            }
                        });
                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                });
    }

    //初始化video
    @Override
    public void initVideoView(String filePath) {

    }
}
