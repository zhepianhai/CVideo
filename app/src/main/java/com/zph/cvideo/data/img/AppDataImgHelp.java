package com.zph.cvideo.data.img;

import android.graphics.Bitmap;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by 3119 on 2018/4/20.
 */
@Singleton
public class AppDataImgHelp  implements DataImgHelp{
//    String string;
    @Inject
    public AppDataImgHelp(){}
//    public AppDataImgHelp(String string){
//        this.string=string;
//    }


    @Override
    public Bitmap takePhotoByCamer(String string) {
        Bitmap bitmap=null;
        return bitmap;
    }
}
