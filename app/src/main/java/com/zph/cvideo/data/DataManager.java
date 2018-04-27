package com.zph.cvideo.data;

import com.zph.cvideo.data.db.DbHelper;
import com.zph.cvideo.data.img.DataImgHelp;
import com.zph.cvideo.data.network.ApiHelper;
import com.zph.cvideo.data.network.prefs.PreferencesHelper;

import dagger.Subcomponent;

/**
 *
 * @author zph
 * @date 2018/4/19
 */

public interface DataManager  extends DataImgHelp,ApiHelper,PreferencesHelper,DbHelper {

}
