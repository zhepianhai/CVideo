package com.zph.cvideo.base;


import com.zph.cvideo.ui.BaseView;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author zph
 * @date 2018/3/22
 */

public interface BaseMainView extends BaseView {
    void onLoadAllCategoryData(List<HashMap<String,String>> categoryList);
}
