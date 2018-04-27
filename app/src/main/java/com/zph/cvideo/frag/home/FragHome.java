package com.zph.cvideo.frag.home;

import com.zph.cvideo.base.BaseMainFragment;
import com.zph.cvideo.utils.constants.Constants;

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
    public int setFragMentType() {
        return Constants.HOME;
    }

}
