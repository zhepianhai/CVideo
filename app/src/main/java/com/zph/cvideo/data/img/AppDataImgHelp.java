package com.zph.cvideo.data.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.view.Surface;

import com.zph.cvideo.MyApplication;
import com.zph.cvideo.inject.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author zph
 * @date 2018/4/20
 */
@Singleton
public class AppDataImgHelp  implements DataImgHelp{
//    String string;
    private Context mContext;
    @Inject
    public AppDataImgHelp(@ApplicationContext Context context){
        this.mContext=context;
    }


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
            android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
            android.hardware.Camera.getCameraInfo(0, info);
            int  degrees= 90;
            camera.startPreview();
            camera.setDisplayOrientation(
                    degrees
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return camera;
    }
}
