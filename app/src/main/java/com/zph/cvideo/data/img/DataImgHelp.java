package com.zph.cvideo.data.img;

import android.graphics.Bitmap;
import android.hardware.Camera;

/**
 *
 * @author zph
 * @date 2018/4/19
 */
public interface DataImgHelp {

    Bitmap takePhotoByCamer(String string);

    Camera initCamra();

}
