package com.zph.cvideo.utils;

import android.os.Environment;

import java.io.File;

/**
 * @author zph
 * @date 2018/3/20
 */

public class SDCardUtils {
    private static final String ROOT_FOLDER = Environment.getExternalStorageDirectory() + "/cvideo/";
    public static final String DOWNLOAD_VIDEO_PATH = ROOT_FOLDER + "video/";
    public static final String DOWNLOAD_IMAGE_PATH = ROOT_FOLDER + "image/";
    public static final String DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String EXPORT_FILE = ROOT_FOLDER + "export.txt";

    /**
     * 存储卡是否挂载
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }
}
