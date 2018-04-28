package com.zph.cvideo.data.img;

import android.graphics.Bitmap;
import android.hardware.Camera;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author zph
 * @date 2018/4/20
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

    @Override
    public Camera initCamra() {
        Camera camera=null;
        try {

            camera = Camera.open(0);
            camera.startPreview();
        }catch (Exception e){
            e.printStackTrace();
        }
        return camera;
    }
}
