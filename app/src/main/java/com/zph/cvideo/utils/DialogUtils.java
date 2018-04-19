package com.zph.cvideo.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zph.cvideo.R;

/**
 *
 * @author zph
 * @date 2018/4/19
 */
public class DialogUtils {
    public static AlertDialog initLodingDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.LoadingDialogStyle);
        View view = View.inflate(context, R.layout.item_loading_layout, null);
        if (!TextUtils.isEmpty(message)) {
            TextView textView = view.findViewById(R.id.textView);
            textView.setText(message);
        }
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.setCancelable(false);

        return mAlertDialog;
    }
}
