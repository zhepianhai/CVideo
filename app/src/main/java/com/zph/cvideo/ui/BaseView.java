package com.zph.cvideo.ui;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * @author zph
 * @date 2018/3/20
 */

public interface BaseView extends MvpView {

    void showLoading(boolean pullToRefresh);

    void showContent();

    void showMessage(String msg, int type);

    void showError(String message);
}
