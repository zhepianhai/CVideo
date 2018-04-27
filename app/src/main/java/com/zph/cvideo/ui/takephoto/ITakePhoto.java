package com.zph.cvideo.ui.takephoto;

import android.hardware.Camera;

/**
 * Created by 3119 on 2018/3/23.
 */

public interface ITakePhoto {
    void initCamera(Camera camera);
    void transformCamer(Camera camera);
    void loadPicEffectLay(String category, boolean pullToRefresh);
}
