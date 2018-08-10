package com.zph.cvideo.bean;

/**
 * Created by zph on 2018/08/08.
 * Describe:保存解码视频图片帧的信息
 */

public class Data {

    public Data(int seconds,String imageName){
        this.seconds=seconds;
        this.imageName=imageName;
    }

    private int seconds;//当前帧的图片在视频中的时间
    private String imageName;//当前帧图片保存到本地的名字 格式为temp+seconds+.jpg

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
