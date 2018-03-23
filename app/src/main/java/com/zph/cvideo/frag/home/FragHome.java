package com.zph.cvideo.frag.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zph.cvideo.R;
import com.zph.cvideo.base.BaseMainFragment;
import com.zph.cvideo.data.model.Category;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author zph
 * @date 2018/3/22
 */
public class FragHome extends BaseMainFragment {
    public static FragHome getInstance() {
        return new FragHome();
    }
    @Override
    public int getCategoryType() {
        return Category.HOME;
    }

    @Override
    public ArrayList<HashMap<String, String>> getCategoryList() {
        return null;
    }

}
