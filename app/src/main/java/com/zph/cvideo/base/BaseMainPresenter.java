package com.zph.cvideo.base;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.zph.cvideo.inject.PerActivity;
import com.zph.cvideo.utils.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

/**
 * @author zph
 * @date 2018/3/22
 */
@PerActivity
public class BaseMainPresenter extends MvpBasePresenter<BaseMainView> implements IBaseMain {
    @Inject
    public BaseMainPresenter() {
    }

    @Override
    public void loadOneFragMentData(int fragmentType) {
        final ArrayList<HashMap<String,String>> list=loadFragMentByType(fragmentType);
        if(null==list){
            return;
        }
        ifViewAttached(new ViewAction<BaseMainView>() {
            @Override
            public void run(@NonNull BaseMainView view) {
                view.onLoadAllFragMentViewFinish(list);
            }
        });
    }



    private ArrayList<HashMap<String,String>>  loadFragMentByType(int type) {

        String[] typesNamed=null;
        int [] types=null;
        switch (type){
            case Constants.HOME:
                typesNamed=Constants.HOMETABLNAMES;
                types=Constants.HOMETABLTYPES;
                break;
                default:
                    break;
        }
        if(null==typesNamed){
            return  null;
        }
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        HashMap<String,String> map=null;
        for(int i=0;i<typesNamed.length;++i){
            map=new HashMap<>();
            map.put(Constants.CATGORY_ID,i+"");
            map.put(Constants.CATGORY_TYPE,types[i]+"");
            map.put(Constants.CATGORY_TITLE,typesNamed[i]);
            list.add(map);
        }
        return list;
    }
}
