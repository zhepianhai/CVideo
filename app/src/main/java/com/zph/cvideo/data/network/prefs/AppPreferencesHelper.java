package com.zph.cvideo.data.network.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.zph.cvideo.inject.ApplicationContext;
import com.zph.cvideo.inject.PreferenceInfo;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author zph
 * @date 2018/4/127
 */
@SuppressLint("ApplySharedPref")
@Singleton
public class AppPreferencesHelper implements PreferencesHelper {


    private final static String KEY_SP_PIC_MORE_EFFECT_PATH = "key_sp_pic_more_effect_path";
    private final static String KEY_SP_PIC_BASE_PATH = "key_sp_pic_base_path";


    private final SharedPreferences mPrefs;

    @Inject
    AppPreferencesHelper(@ApplicationContext Context context,
                         @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void setPicMoreEffectAddress(String address) {
        mPrefs.edit().putString(KEY_SP_PIC_MORE_EFFECT_PATH, address).apply();
    }

    @Override
    public String getPicMoreEffectAddress() {
        return mPrefs.getString(KEY_SP_PIC_MORE_EFFECT_PATH, "");
    }

    @Override
    public void setPicBaseAddress(String address) {
        mPrefs.edit().putString(KEY_SP_PIC_BASE_PATH, address).apply();
    }

    @Override
    public String getPicBaseAddress() {
        return mPrefs.getString(KEY_SP_PIC_BASE_PATH, "");
    }



}
