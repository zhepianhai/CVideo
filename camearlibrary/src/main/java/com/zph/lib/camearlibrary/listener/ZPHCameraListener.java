package com.zph.lib.camearlibrary.listener;

import android.graphics.Bitmap;

/**
 * @author zph
 * @date 2018/5/4
 */
public interface ZPHCameraListener {

    void captureSuccess(Bitmap bitmap);

    void recordSuccess(String url, Bitmap firstFrame);

    void quit();
}
