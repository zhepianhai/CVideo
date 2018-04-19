package com.zph.cvideo.base;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.zph.cvideo.data.model.Category;
import com.zph.cvideo.inject.PerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public void loadAllCategoryData(int categoryType) {
        if(categoryType== Category.HOME){
            final ArrayList<HashMap<String,String>> list=new ArrayList<>();
            HashMap<String,String> map=null;
            for(int i=0;i<Category.HOMETABLNAMES.length;++i){
                map=new HashMap<>();
                map.put(Category.CATGORY_ID,i+"");
                map.put(Category.CATGORY_TYPE,Category.HOMETYPES[i]+"");
                map.put(Category.CATGORY_TITLE,Category.HOMETABLNAMES[i]);
                list.add(map);
            }
            ifViewAttached(new ViewAction<BaseMainView>() {
                @Override
                public void run(@NonNull BaseMainView view) {
                    view.onLoadAllCategoryData(list);
                }
            });

        }
    }
}
