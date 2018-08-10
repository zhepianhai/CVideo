package com.zph.cvideo.ffmpeng;

import android.app.Activity;
import android.util.Log;
/**
 * Created by zph on 2018/8/06.
 * Describe:
 */

public class FfmpegTool {
    private static FfmpegTool instance;
    private Activity activity;
    private ImageDecodeing imageDecodeing;

    public ImageDecodeing getImageDecodeing() {
        return this.imageDecodeing;
    }

    public void setImageDecodeing(ImageDecodeing imageDecodeing) {
        this.imageDecodeing = imageDecodeing;
    }

    private FfmpegTool() {
    }

    private void init(Activity activity) {
        this.activity = activity;
    }

    public static FfmpegTool getInstance(Activity activity) {
        if(instance == null) {
            Class var1 = FfmpegTool.class;
            synchronized(FfmpegTool.class) {
                if(instance == null) {
                    instance = new FfmpegTool();
                }
            }
        }

        instance.init(activity);
        return instance;
    }

    public static native int cmdRun(String[] var0);

    public static native int decodToImage(String var0, String var1, int var2, int var3);

    public int videoToImage(final String src, final String dir, int startTime, int count, final FfmpegTool.VideoResult call, final int tag) {
        final int result = decodToImage(src, dir, startTime, count);
        if(call != null) {
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    boolean ret = true;
                    if(result != 0) {
                        ret = false;
                    }

                    call.clipResult(result, src, dir, ret, tag);
                }
            });
        }

        return result;
    }

    public int clipVideo(final String src, final String dst, int startTime, int duration, final int tag, final FfmpegTool.VideoResult call) {
        String cmd = String.format("ffmpeg -y -ss " + startTime + " -t " + duration + " -i " + src + " -vcodec copy -acodec copy -strict -2 " + dst, new Object[0]);
        String regulation = "[ \\t]+";
        Log.i("clipVideo", "cmd:" + cmd);
        String[] split = cmd.split(regulation);
        final int result = cmdRun(split);
        if(call != null) {
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    call.clipResult(result, src, dst, result == 0, tag);
                }
            });
        }

        return result;
    }

    public int compressVideo(final String src, String dst, final int tag, final FfmpegTool.VideoResult call) {
        final String dst2 = dst + "temp" + System.currentTimeMillis() / 1000L + ".mp4";
        String cmd = String.format("ffmpeg -threads 32 -y -i " + src + " -b:v 1500k -bufsize 3000k -maxrate 2000k -preset superfast " + dst2, new Object[0]);
        String regulation = "[ \\t]+";
        Log.i("compressVideo", "cmd:" + cmd);
        String[] split = cmd.split(regulation);
        final int result = cmdRun(split);
        if(call != null) {
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    call.clipResult(result, src, dst2, result == 0, tag);
                }
            });
        }

        Log.i("compressVideo", "result:" + result);
        return result;
    }

    public void decodToImageCall(final String path, final int index) {
        if(this.imageDecodeing != null) {
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    FfmpegTool.this.imageDecodeing.sucessOne(path, index);
                }
            });
        }

    }

    public native int decodToImageWithCall(String var1, String var2, int var3, int var4);

    public void relase() {
        this.activity = null;
    }

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

    public interface ImageDecodeing {
        void sucessOne(String var1, int var2);
    }

    public interface VideoResult {
        void clipResult(int var1, String var2, String var3, boolean var4, int var5);
    }

}
