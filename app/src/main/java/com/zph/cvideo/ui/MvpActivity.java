package com.zph.cvideo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.delegate.ActivityMvpDelegate;
import com.hannesdorfmann.mosby3.mvp.delegate.ActivityMvpDelegateImpl;
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback;
import com.zph.cvideo.R;

import butterknife.BindView;

/**
 * An Activity that uses a {@link MvpPresenter} to implement a Model-View-Presenter
 * architecture.
 *
 * @author zph
 * @date 2018/3/20
 * @since 1.0.0
 */

public abstract class MvpActivity <V extends MvpView, P extends MvpPresenter<V>>
        extends BaseAppCompatActivity implements MvpView,
        MvpDelegateCallback<V, P>, View.OnClickListener {

    public static final int NAVBTNRIGHT_TYPE_NROMAL = 0;
    public static final int NAVBTNRIGHT_TYPE_HOME = 1;
    public static final int NAVBTNRIGHT_TYPE_TIME = 2;
    public static final int NAVBTNRIGHT_TYPE_DATA = 3;
    public static final int NAVBTNRIGHT_TYPE_LOC = 4;
    public static final int NAVBTNRIGHT_TYPE_HISTPH = 5;
    public static final int NAVBTNRIGHT_TYPE_MSGFILE = 6;
    public static final int NAVBTNRIGHT_TYPE_MEDIAUP = 7;


    protected ActivityMvpDelegate mvpDelegate;
    protected P presenter;
    protected boolean retainInstance;
    protected Button mNavBtnLeft;
    protected Button mNavBtnRight;
    protected TextView mNavTxtTitle;
    protected LinearLayout mViewMain;
    protected RelativeLayout mNavLay;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_root);
        this.initNav();
        getMvpDelegate().onCreate(savedInstanceState);
    }
    private void initNav() {
        mNavLay = (RelativeLayout) this.findViewById(R.id.root_lay_nav);
        mNavBtnLeft = (Button) this.findViewById(R.id.nav_btn_left);
        mNavBtnLeft.setOnClickListener(this);
        mNavBtnRight = (Button) this.findViewById(R.id.nav_btn_right);
        mNavBtnRight.setOnClickListener(this);
        mNavTxtTitle = (TextView) this.findViewById(R.id.nav_txt_title);
        mViewMain = (LinearLayout) this.findViewById(R.id.root_lay_show);
    }

    public void setNavTitle(String title) {
        mNavTxtTitle.setText(title);
    }

    public void setNavBtnRightType(int type) {
        mNavBtnRight.setText("");
        if (type == NAVBTNRIGHT_TYPE_NROMAL) {
        }
        else if (type == NAVBTNRIGHT_TYPE_HOME) {
        }
        else if (type == NAVBTNRIGHT_TYPE_TIME) {
        }
        else if (type == NAVBTNRIGHT_TYPE_DATA) {
        }
        else if (type == NAVBTNRIGHT_TYPE_LOC) {
        }
        else if (type == NAVBTNRIGHT_TYPE_MSGFILE) {
        }
        else if (type == NAVBTNRIGHT_TYPE_MEDIAUP) {
        }
        else if (type == NAVBTNRIGHT_TYPE_HISTPH) {
        }
        else {
            mNavBtnRight.setBackgroundDrawable(null);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG","onStart");
        getMvpDelegate().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMvpDelegate().onRestart();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getMvpDelegate().onContentChanged();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i("TAG","onPostCreate");
        getMvpDelegate().onPostCreate(savedInstanceState);
    }

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link MvpPresenter} for this view
     */
    @NonNull
    @Override
    public abstract P createPresenter();

    /**
     * Get the mvp delegate. This is internally used for creating presenter, attaching and detaching
     * view from presenter.
     * <p>
     * <p><b>Please note that only one instance of mvp delegate should be used per Activity
     * instance</b>.
     * </p>
     * <p>
     * <p>
     * Only override this method if you really know what you are doing.
     * </p>
     *
     * @return {@link ActivityMvpDelegateImpl}
     */
    @NonNull
    protected ActivityMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new ActivityMvpDelegateImpl(this, this, true);
        }
        Log.i("TAG","getMvpDelegate");
        return mvpDelegate;
    }

    @NonNull
    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_btn_left:
                this.finish();
                break;
            case R.id.nav_btn_right:
                break;
        }
    }
}


