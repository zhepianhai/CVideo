package com.zph.cvideo.custom;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by 3119 on 2018/4/11.
 */

public class MyCameraView  extends SurfaceView implements SurfaceHolder.Callback{
    private Context mContext;
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public MyCameraView(Context context,Camera mCamera) {
        this(context,null,mCamera);
    }

    public MyCameraView(Context context, AttributeSet attrs,Camera mCamera) {
        this(context, attrs,0,mCamera);
    }

    public MyCameraView(Context context, AttributeSet attrs, int defStyleAttr,Camera mCamera) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        this.mCamera=mCamera;
        initVar();
    }

    private void initVar() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null){
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e){
        }
        try {
            Camera.Parameters objParam = mCamera.getParameters();
            //设置对焦模式为持续对焦，（最好先判断一下手机是否有这个对焦模式，有些手机没有会报错的）
            objParam.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            mCamera.setDisplayOrientation(90);
            mCamera.setParameters(objParam);
            mCamera.startPreview();


        } catch (Exception e){
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


}
