package com.zph.lib.camearlibrary.listener;

/**
 * @author zph
 * @date 2018/5/4
 */
public interface CaptureListener {
    void takePictures();

    void recordShort(long time);

    void recordStart();

    void recordEnd(long time);

    void recordZoom(float zoom);

    void recordError();
}
