package com.zph.cvideo.custom;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zph.cvideo.utils.SizeUtil;

import java.io.IOException;

/**
 * @author zph
 * @date 2018/4/11
 */
public class MyCameraView  extends SurfaceView implements SurfaceHolder.Callback{
    private Context mContext;
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.Parameters mParameters;
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
        this.mParameters=mCamera.getParameters();
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

            mCamera.startPreview();


        } catch (Exception e){
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


}
