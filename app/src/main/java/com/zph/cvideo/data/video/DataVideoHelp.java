package com.zph.cvideo.data.video;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author zph
 * Created by zph on 2018/8/6.
 */

public interface DataVideoHelp {

    /**
     * 加载视频文件列表
     * */
    Observable<List<HashMap<String,String>>> loadVideoFiled();
}
