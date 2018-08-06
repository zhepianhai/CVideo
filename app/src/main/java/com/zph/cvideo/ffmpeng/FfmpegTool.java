package com.zph.cvideo.ffmpeng;

import android.util.Log;
/**
 * Created by zph on 2018/8/06.
 * Describe:
 */

public class FfmpegTool {

    static {
        System.loadLibrary("avutil");
        System.loadLibrary("fdk-aac");
        System.loadLibrary("avcodec");
        System.loadLibrary("avformat");
        System.loadLibrary("swscale");
        System.loadLibrary("swresample");
        System.loadLibrary("avfilter");
        System.loadLibrary("jxffmpegrun");
    }

    /**
     * 执行FFmpeg命令
     * @param cmd
     * @return
     */
    public static native int cmdRun(String[] cmd);

    /**
     *把视频按秒解码成图片
     * @param srcPath 视频源地址
     * @param savePath 要保存的图片目录
     * @param startTime 从视频的什么时间开始 单位秒
     * @param count 要解码多少个
     * @return 返回码 成功0
     */
    public static native int decodToImage(String srcPath,String savePath,int startTime,int count);



    public void decodToImageCall(String path,int index){
        Log.i("decodToImageCall","path:"+path+"___index:"+index);
    }

    public native int decodToImageWithCall(String srcPath,String savePath,int startTime,int count);


    //test

}
