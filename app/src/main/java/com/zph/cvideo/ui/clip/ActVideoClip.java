package com.zph.cvideo.ui.clip;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zph.cvideo.R;
import com.zph.cvideo.adpter.AdpGridView;
import com.zph.cvideo.adpter.AdpVideoClip;
import com.zph.cvideo.bean.Data;
import com.zph.cvideo.custom.RangeBar;
import com.zph.cvideo.define.CONST_QUERY;
import com.zph.cvideo.ffmpeng.FfmpegTool;
import com.zph.cvideo.ui.MvpActivity;
import com.zph.cvideo.ui.takephoto.ActTakePhoto;
import com.zph.cvideo.utils.DialogUtils;
import com.zph.cvideo.utils.SDCardUtils;
import com.zph.cvideo.utils.UIUtil;
import com.zph.cvideo.utils.constants.Constants;
import com.zph.cvideo.utils.constants.PermissionConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import butterknife.BindView;

import static android.os.Environment.getExternalStorageDirectory;

public class ActVideoClip extends MvpActivity<ClipVideoView, ClipVideoPresenter> implements  ClipVideoView, AdapterView.OnItemClickListener, RangeBar.OnRangeBarChangeListener {


    @Inject
    ClipVideoPresenter mTakePhotoProsenter;

    //list
    private LinearLayout mLayList;
    GridView mGridView;
    private AdpGridView mAdpGridView;
    private ArrayList<HashMap<String,String>> mArrayData;
    private Dialog mDialog;


    //video
    private RelativeLayout mLayVideo;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerview;
    private RangeBar mRangeBar;
    private FrameLayout mFram;
    private VideoView mVideoView;
    private FfmpegTool mFfmpegTool;

    private String videoPath;
    private String parentPath;
    private long videoTime;
    private AdpVideoClip adapter;

    private final int IMAGE_NUM=10;//每一屏图片的数量
    private int imagCount=0;//整个视频要解码图片的总数量
    private int firstItem=0;//recycleView当前显示的第一项
    private int lastItem=0;//recycleView当前显示的最后一项
    private int leftThumbIndex=0;//滑动条的左端
    private int rightThumbIndex=IMAGE_NUM;//滑动条的右端
    private int startTime,endTime=IMAGE_NUM;//裁剪的开始、结束时间
    private String videoResutlDir;//视频裁剪结果的存放目录
    private String videoResutl;
    ExecutorService executorService = Executors.newFixedThreadPool(3);


    private boolean granted = false;
    private int mPermisionCode = 300;
    private int mPermisionReqCode = 400;
    private String[] mPermission = PermissionConstants.getPermissions(PermissionConstants.STORAGE);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initVar();
        this.initView();
        makeCaneraPermision();
    }

    @SuppressLint("ResourceAsColor")
    private void initVar() {
        setStatusBarColor(getResources().getColor(R.color.colorHome));
        mArrayData=new ArrayList<>();
        mDialog= DialogUtils.initLodingDialog(this,"loadding");
        mFfmpegTool=FfmpegTool.getInstance(this);

    }
    private void initView() {
        this.setNavBtnRightType(MvpActivity.NAVBTNRIGHT_TYPE_HOME);
        mNavTxtTitle.setText(this.getString(R.string.app_act_video_clip));
        // Main
        LinearLayout layShow = (LinearLayout) LinearLayout.inflate(this, R.layout.activity_act_video_clip, null);
        mViewMain.addView(layShow, new LinearLayout.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));

        mLayList=this.findViewById(R.id.act_video_clip_lay_list);
        mGridView=this.findViewById(R.id.act_video_cilp_gridview);
        mAdpGridView=new AdpGridView(this,mArrayData,R.layout.item_grid_video);
        mGridView.setAdapter(mAdpGridView);
        mGridView.setOnItemClickListener(this);

        mLayVideo=this.findViewById(R.id.act_video_clip_lay_video);
        mRecyclerview=this.findViewById(R.id.act_video_cilp_recyclerview);
        mRangeBar=this.findViewById(R.id.act_video_cilp_rangeBar);
        mFram=this.findViewById(R.id.act_video_cilp_fram);
        mVideoView=this.findViewById(R.id.act_video_cilp_VideoView);


        mLayList.setVisibility(View.VISIBLE);
        mLayVideo.setVisibility(View.GONE);

    }
    private void initVideoView(){
        mLinearLayoutManager=new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        adapter=new AdpVideoClip(this,getDataList(videoTime));
        adapter.setParentPath(parentPath);
        adapter.setRotation(UIUtil.strToFloat(UIUtil.getVideoInf(videoPath)));
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.addOnScrollListener(onScrollListener);
        mRangeBar.setOnRangeBarChangeListener(this);//设置滑动条的监听
        mVideoView.setVideoPath(videoPath);
        mVideoView.start();
    }
    private void makeCaneraPermision() {
        if (!AndPermission.hasPermission(ActVideoClip.this, mPermission)) {
            AndPermission.with(this)
                    .requestCode(mPermisionCode)
                    .permission(mPermission)
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                            AndPermission.rationaleDialog(ActVideoClip.this, rationale).show();
                        }
                    })
                    .callback(listener)
                    .start();
        }else{
            this.loadVideoFiled();

        }
    }
    private void loadVideoFiled() {
        mTakePhotoProsenter.loadAudioFiled();
    }
    public  List<Data> getDataList(long videoTime){
        List<Data> dataList=new ArrayList<>();
        int seconds= (int) (videoTime/1000);
        for (imagCount=0;imagCount<seconds;imagCount++){
            dataList.add(new Data(imagCount,"temp"+imagCount+".jpg"));
        }
        return dataList;
    }

    private RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.i("onScrollStateChanged","onScrollStateChanged :"+newState);
            if (newState==RecyclerView.SCROLL_STATE_IDLE){
                firstItem=mLinearLayoutManager.findFirstVisibleItemPosition();
                lastItem=mLinearLayoutManager.findLastVisibleItemPosition();
                List<Data> dataList=adapter.getDataList();
                for(int i=firstItem;i<=lastItem;i++){
                    if (!UIUtil.isFileExist(parentPath+dataList.get(i).getImageName())){
                        Log.i("onScrollStateChanged","not exist :"+i);
                        runImagDecodTask(i,lastItem-i+1);
                        break;
                    }
                }
            }
            calStartEndTime();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
    /**
     * 运行一个图片的解码任务
     * @param start 解码开始的视频时间 秒
     * @param count 一共解析多少张
     */
    private void runImagDecodTask(final int start,final int count){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mFfmpegTool.decodToImageWithCall(videoPath,parentPath,start,count);
            }
        });
    }
    /**
     * 第一次解码，先解码两屏的图片
     */
    private void initData(){
        File parent=new File(parentPath);
        if (!parent.exists()){
            parent.mkdirs();
        }
        Toast.makeText(this,"第一次解码中，先解码两屏的图片",Toast.LENGTH_SHORT).show();
        runImagDecodTask(0,2 * IMAGE_NUM);
    }

    /**
     * 计算开始结束时间
     */
    private void calStartEndTime(){
        int duration=rightThumbIndex-leftThumbIndex;
        startTime=firstItem+leftThumbIndex;
        endTime=startTime+duration;
        //此时可能视频已经结束，若已结束重新start
        if (!mVideoView.isPlaying()){
            mVideoView.start();
        }
        //把视频跳转到新选择的开始时间
        mVideoView.seekTo(startTime*1000);
    }

    private void returnTOList(){
        mNavLay.setVisibility(View.VISIBLE);
        mLayVideo.setVisibility(View.GONE);
        mLayList.setVisibility(View.VISIBLE);
        mVideoView.stopPlayback();
        setStatusBarColor(getResources().getColor(R.color.colorHome));
    }

    @NonNull
    @Override
    public ClipVideoPresenter createPresenter() {
        getActivityComponent().inject(this);
        return mTakePhotoProsenter;
    }

    @Override
    public void onStart() {
        if(mDialog!=null&&!mDialog.isShowing()){
            mDialog.show();
        }
        super.onStart();

    }

    @Override
    public void onEnd() {
        if(mDialog!=null &&mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void onLoadSuccess(List<HashMap<String, String>> mapList) {
        if(mapList!=null&&mapList.size()>0){
            mArrayData.clear();
            mArrayData.addAll(mapList);
            mAdpGridView.notifyDataSetChanged();
        }

    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantedPermissions) {
            // 权限申请成功回调。
            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == mPermisionCode) {
                // TODO ...
                if (AndPermission.hasPermission(ActVideoClip.this, grantedPermissions)) {
                    ActVideoClip.this.loadVideoFiled();
                    granted = true;

                } else {
                    granted = false;
                    AndPermission.defaultSettingDialog(ActVideoClip.this, mPermisionReqCode).show();
                }
            }
        }
        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == mPermisionCode) {
                granted = false;
                // TODO ...
                if (!AndPermission.hasPermission(ActVideoClip.this, deniedPermissions)) {
                    // 是否有不再提示并拒绝的权限。
                    if (AndPermission.hasAlwaysDeniedPermission(ActVideoClip.this, deniedPermissions)) {
                        // 第一种：用AndPermission默认的提示语。
                        AndPermission.defaultSettingDialog(ActVideoClip.this, mPermisionReqCode).show();
                    } else {
                        AndPermission.defaultSettingDialog(ActVideoClip.this, mPermisionReqCode).show();
                    }
                }
                ActVideoClip.this.finish();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_btn_left:
                if(mLayVideo.getVisibility()==View.VISIBLE){
                    returnTOList();
                }else{
                    this.finish();
                }

                break;

            case R.id.act_video_cilp_image_close:
                returnTOList();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if(mLayVideo.getVisibility()==View.VISIBLE){
                    returnTOList();
                }
                return true;
        }
        return true;


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
        mLayVideo.setVisibility(View.VISIBLE);
        mLayList.setVisibility(View.GONE);
        mVideoView.stopPlayback();
        mNavLay.setVisibility(View.GONE);
        setStatusBarColor(Color.BLACK);
        setStatusBarTransparent();

//        mTakePhotoProsenter.initVideoView(mArrayData.get(position).get(CONST_QUERY.VIDEO_IMAGE_PATH));

        videoPath = mArrayData.get(position).get(CONST_QUERY.VIDEO_IMAGE_PATH);
        Log.i("onCreate","videoPath:"+videoPath);
        if (!new File(videoPath).exists()) {
            Toast.makeText(this, "视频文件不存在", Toast.LENGTH_LONG).show();
            finish();
        }
        String str="temp"+System.currentTimeMillis()/1000;

        parentPath= getExternalStorageDirectory().getAbsolutePath()
                +File.separator+ Constants.FILE_PATH+"/"+Constants.FILE_VIDEO_CLIP_PARENT+File.separator+str+File.separator;
        videoResutlDir= getExternalStorageDirectory().getAbsolutePath()
                +File.separator+ Constants.FILE_PATH+"/"+Constants.FILE_VIDEO_CLIP_PARENT+File.separator+Constants.FILE_VIDEO_CLIP_CHILD;


        File file=new File(parentPath);
        if(!file.exists()){
            file.mkdirs();
        }
        mRangeBar.setmTickCount(IMAGE_NUM+1);
        videoTime=UIUtil.getVideoDuration(videoPath);
//        FfmpegTool.decodToImage()


        mFfmpegTool.setImageDecodeing(new FfmpegTool.ImageDecodeing() {
            @Override
            public void sucessOne(String s, int i) {
                adapter.notifyItemRangeChanged(i,1);
            }
        });

        initVideoView();
        initData();


//        new Thread(){
//            @Override
//            public void run() {

//        String basePath = Environment.getExternalStorageDirectory().getPath();
//                String videoPath=mArrayData.get(position).get(CONST_QUERY.VIDEO_IMAGE_PATH);
//        String dir=basePath+ File.separator+"test"+File.separator;
//        String cmd2 = String.format("ffmpeg -ss 00:00:10 -i "+ videoPath+" -f image2 -y  "+basePath+"/test.jpg");
//        String regulation="[ \\t]+";
//        Log.i("MainActivity","cmd2:"+cmd2);
//        final String[] split2 = cmd2.split(regulation);
//
//                int result= FfmpegTool.cmdRun(split2);
//                Log.i("MainActivity","result:"+result);
//            }
//        }.start();

    }

    @Override
    public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
        this.leftThumbIndex=leftThumbIndex;
        this.rightThumbIndex=rightThumbIndex;
        calStartEndTime();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){//获取到图片总的显示范围的大小后，设置每一个图片所占有的宽度
            if(mLayVideo.getVisibility()==View.VISIBLE) {
                adapter.setImagWidth(mRangeBar.getMeasuredWidth() / IMAGE_NUM);
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
        File file=new File(getExternalStorageDirectory().getAbsolutePath()
                +File.separator+ Constants.FILE_PATH+"/"+Constants.FILE_VIDEO_CLIP_PARENT);
        SDCardUtils.deleteFile(file);
        //最后不要忘了删除这个临时文件夹 parentPath
        //不然时间长了会在手机上生成大量不用的图片，该activity销毁后这个文件夹就用不到了
        //如果内存大，任性不删也可以
    }
}
