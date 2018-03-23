package com.zph.cvideo.data.model;

import java.io.Serializable;

/**
 * @author zph
 * @date 2018/3/22
 */


public class Category implements Serializable{
    public static final String[] HOMETABLNAMES={"图片","视频","音频"};
    public static final int HOME=1000;
    public static final int HOME_PICTURE=1001;
    public static final int HOME_VIDEO=1002;
    public static final int HOME_AUDIO=1003;
    public static final int[] HOMETYPES={HOME_PICTURE,HOME_VIDEO,HOME_AUDIO};
    public static final int INFO=2000;
    public static final int MORE=3000;

    public static final String CATGORY_TITLE="category_title";
    public static final String CATGORY_TYPE="category_type";
    public static final String CATGORY_ID="category_id";
}
