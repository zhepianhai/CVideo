package com.zph.cvideo.ui.main;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.devbrackets.android.exomedia.util.ResourceUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zph.cvideo.R;
import com.zph.cvideo.define.CONST_QUERY;
import com.zph.cvideo.ui.MvpActivity;
import com.zph.cvideo.utils.SDCardUtils;
import com.zph.cvideo.utils.constants.PermissionConstants;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @author zph
 * @date 2018/3/20
 */
public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView{
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    @BindView(R.id.fab_search)
    FloatingActionButton mFabSearch;
    @BindView(R.id.content_main_framelay)
    FrameLayout mContent;

    @Inject
    MainPresenter mainPresenter;

    private FragmentManager mFragmentManager;
    private int mSelectIndex;
    private int mPermisionCode = 300;
    private int mPermisionReqCode = 400;
    private String[] mPermission = PermissionConstants.getPermissions(PermissionConstants.STORAGE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        mSelectIndex = getIntent().getIntExtra(CONST_QUERY.KEY_SELECT_INDEX, 0);
        if (savedInstanceState != null) {
            mSelectIndex = savedInstanceState.getInt(CONST_QUERY.KEY_SELECT_INDEX);
        }
        initBottomNavigationBar(mSelectIndex);
        makeDirAndCheckPermision();
        mFabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOnFloatingActionButtonClick(mSelectIndex);

            }
        });
//        firstTabShow = dataManager.getMainFirstTabShow();
//        secondTabShow = dataManager.getMainSecondTabShow();
        doOnTabSelected(mSelectIndex);
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return null;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showMessage(String msg, int type) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CONST_QUERY.KEY_SELECT_INDEX, mSelectIndex);
    }
    private void initBottomNavigationBar(@IntRange(from = 0, to = 2) int position) {
        mBottomNavigationBar.addItem(new BottomNavigationItem(ResourceUtil.getDrawable(this, R.drawable.ic_home_black_svg_24), R.string.title_home));
        mBottomNavigationBar.addItem(new BottomNavigationItem(ResourceUtil.getDrawable(this, R.drawable.ic_infome_black_svg_24), R.string.title_information));
        mBottomNavigationBar.addItem(new BottomNavigationItem(ResourceUtil.getDrawable(this, R.drawable.ic_more_black_svg_24), R.string.title_more));

        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setActiveColor(R.color.bottom_navigation_bar_active);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        mBottomNavigationBar.setFirstSelectedPosition(position);
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.SimpleOnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                doOnTabSelected(position);
            }
        });
        mBottomNavigationBar.setBarBackgroundColor(R.color.bottom_navigation_bar_background);
        mBottomNavigationBar.setFab(mFabSearch);
        mBottomNavigationBar.initialise();
    }
    private void doOnTabSelected(@IntRange(from = 0, to = 2) int position) {
        switch (position) {
            case 0:
//                handlerFirstTabClickToShow(firstTabShow, position, false);
//                showFloatingActionButton(fabSearch);
                break;
            case 1:
//                handlerSecondTabClickToShow(secondTabShow, position, false);
//                showFloatingActionButton(fabSearch);
                break;
            case 2:
                break;
            default:
        }
        mSelectIndex = position;
    }

    /**
     * 申请权限并创建下载目录
     */
    private void makeDirAndCheckPermision() {
        if (!AndPermission.hasPermission(MainActivity.this, mPermission)) {
            AndPermission.with(this)
                    .requestCode(mPermisionCode)
                    .permission(mPermission)
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                            AndPermission.rationaleDialog(MainActivity.this, rationale).show();
                        }
                    })
                    .callback(listener)
                    .start();
        }
    }
    private PermissionListener listener = new PermissionListener() {
        File file = new File(SDCardUtils.DOWNLOAD_VIDEO_PATH);

        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == mPermisionCode) {
                // TODO ...
                if (AndPermission.hasPermission(MainActivity.this, grantedPermissions)) {
                    if (!file.exists()) {
                        if (!file.mkdirs()) {
//                            showMessage("创建下载目录失败了", TastyToast.ERROR);
                        }
                    }
                } else {
                    AndPermission.defaultSettingDialog(MainActivity.this, mPermisionReqCode).show();
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == mPermisionCode) {
                // TODO ...
                if (!AndPermission.hasPermission(MainActivity.this, deniedPermissions)) {
                    // 是否有不再提示并拒绝的权限。
                    if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                        // 第一种：用AndPermission默认的提示语。
                        AndPermission.defaultSettingDialog(MainActivity.this, mPermisionReqCode).show();
                    } else {
                        AndPermission.defaultSettingDialog(MainActivity.this, mPermisionReqCode).show();
                    }
                }
            }
        }
    };

    private void doOnFloatingActionButtonClick(@IntRange(from = 0, to = 2) int position) {
        switch (position) {
            case 0:
//                showVideoBottomSheet(firstTabShow);
                break;
            case 1:
//                showPictureBottomSheet(secondTabShow);
                break;
            case 2:
//                showForumBottomSheet(0);
                break;
            default:
        }
    }


}
