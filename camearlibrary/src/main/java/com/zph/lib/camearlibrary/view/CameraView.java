package com.zph.lib.camearlibrary.view;

import android.graphics.Bitmap;

/**
 * @author zph
 * @date 2018/5/4
 */

public interface CameraView {
    void resetState(int type);

    void confirmState(int type);

    void showPicture(Bitmap bitmap, boolean isVertical);

    void playVideo(Bitmap firstFrame, String url);

    void stopVideo();

    void setTip(String tip);

    void startPreviewCallback();

    boolean handlerFoucs(float x, float y);
}
