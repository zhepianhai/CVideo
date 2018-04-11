package com.zph.cvideo.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Created by zph
 */

public class UtilCamera {

    /**
     * 获取一个Camera Obj
     * */
    public static Camera getCameraInstance(Context mContext){
        Camera c=null;
        try{
            c=Camera.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        return c;
    }

    /*
     *判断手机是否存在可用的Camera设备 可用的数量1 or 2
    **/
    public static boolean isCameraRuning(Context mContext){
        if(mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            if(Camera.getNumberOfCameras()>0){
                return true;
            }
        }
        return false;
    }

}
