package com.zph.cvideo.ui.main;

import android.graphics.Color;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.zph.cvideo.frag.home.FragHome;
import com.zph.cvideo.frag.infomation.FragInfomation;
import com.zph.cvideo.frag.more.FragMore;
import com.zph.cvideo.ui.MvpActivity;
import com.zph.cvideo.utils.FragmentUtils;
import com.zph.cvideo.utils.SDCardUtils;
import com.zph.cvideo.utils.SizeUtil;
import com.zph.cvideo.utils.constants.PermissionConstants;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ashokvarma.bottomnavigation.BottomNavigationBar.MODE_DEFAULT;
import static com.ashokvarma.bottomnavigation.BottomNavigationBar.MODE_FIXED;
import static com.ashokvarma.bottomnavigation.BottomNavigationBar.MODE_SHIFTING;

/**
 * @author zph
 * @date 2018/3/20
 */
public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView{
    private final int TAB_HOME=0;
    private final int TAB_INFO=1;
    private final int TAB_MORE=2;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    @BindView(R.id.fab_search)
    FloatingActionButton mFabSearch;
    @BindView(R.id.content_main_framelay)
    FrameLayout mContent;

    @Inject
    MainPresenter mainPresenter;
    private Fragment mCurrentFragment;
    private FragHome mFragHome;
    private FragInfomation mFragInfomation;
    private FragMore  mFragMore;
    private FragmentManager mFragmentManager;
    private int mSelectIndex;
    private int mPermisionCode = 300;
    private int mPermisionReqCode = 400;
    private String[] mPermission = PermissionConstants.getPermissions(PermissionConstants.STORAGE);

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        doOnTabSelected(mSelectIndex);
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
            getActivityComponent().inject(this);
        return mainPresenter;
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
        mBottomNavigationBar.addItem(new BottomNavigationItem(ResourceUtil.getDrawable(this, R.drawable.ic_home_black_24dp), R.string.title_home));
        mBottomNavigationBar.addItem(new BottomNavigationItem(ResourceUtil.getDrawable(this, R.drawable.ic_infome_black_svg_24), R.string.title_information));
        mBottomNavigationBar.addItem(new BottomNavigationItem(ResourceUtil.getDrawable(this, R.drawable.ic_more_black_svg_24_1), R.string.title_more));
        mBottomNavigationBar.setMode(MODE_FIXED);
        mBottomNavigationBar.setActiveColor(R.color.bottom_navigation_bar_active);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
//        mBottomNavigationBar.setMode(MODE_SHIFTING);
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
        SizeUtil.setBottomNavigationItem(mBottomNavigationBar,4,28,12,this);
    }
    private void doOnTabSelected(@IntRange(from = 0, to = 2) int position) {
        switch (position) {
            case 0:
                handlerFirstTabClickToShow(position, false);
                showFloatingActionButton(mFabSearch);
                setStatusBarColor(getResources().getColor(R.color.colorHome));
                break;
            case 1:
                handlerFirstTabClickToShow(position, false);
                hideFloatingActionButton(mFabSearch);
                setStatusBarColor(Color.WHITE);
                break;
            case 2:
                handlerFirstTabClickToShow(position, false);
                hideFloatingActionButton(mFabSearch);
                setStatusBarColor(Color.WHITE);
                break;
            default:
        }
        mSelectIndex = position;
    }
    private void showFloatingActionButton(final FloatingActionButton fabSearch) {
        fabSearch.show(new FloatingActionButton.OnVisibilityChangedListener() {
            @Override
            public void onShown(FloatingActionButton fab) {
                fabSearch.requestLayout();
                mBottomNavigationBar.setFab(fab);
            }
        });
    }
    private void handlerFirstTabClickToShow(int position,boolean isInnerReplace) {
        switch (position) {
            case TAB_HOME:
                if (mFragHome == null) {
                    mFragHome = FragHome.getInstance();
                }
                mCurrentFragment = FragmentUtils.switchContent(mFragmentManager, mCurrentFragment, mFragHome, mContent.getId(), position, false);
                break;
            case TAB_INFO:
                if (mFragInfomation == null) {
                    mFragInfomation = FragInfomation.getInstance();
                }
                mCurrentFragment = FragmentUtils.switchContent(mFragmentManager, mCurrentFragment, mFragInfomation, mContent.getId(), position, false);

                break;
            case TAB_MORE:
                if (mFragMore == null) {
                    mFragMore = FragMore.getInstance();
                }
                mCurrentFragment = FragmentUtils.switchContent(mFragmentManager, mCurrentFragment, mFragMore, mContent.getId(), position, false);
                break;
            default:
        }
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
    private void hideFloatingActionButton(FloatingActionButton fabSearch) {
        ViewGroup.LayoutParams layoutParams = fabSearch.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams coLayoutParams = (CoordinatorLayout.LayoutParams) layoutParams;
            FloatingActionButton.Behavior behavior = new FloatingActionButton.Behavior();
            coLayoutParams.setBehavior(behavior);
        }
        fabSearch.hide();
    }
    private void doOnFloatingActionButtonClick(@IntRange(from = 0, to = 2) int position) {
        switch (position) {
            case 0:
                showHomeBottomSheet();
                break;
            case 1:
                break;
            case 2:
                break;
            default:
        }
    }

    private void showHomeBottomSheet() {

    }


}
