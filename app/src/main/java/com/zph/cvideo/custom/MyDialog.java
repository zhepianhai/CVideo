package com.zph.cvideo.custom;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author zph
 * Created by zph on 2018/8/6.
 */

public class MyDialog extends Dialog{
    public MyDialog(@NonNull Context context) {
        super(context);
    }

    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
