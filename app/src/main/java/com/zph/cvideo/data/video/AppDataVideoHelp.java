package com.zph.cvideo.data.video;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.zph.cvideo.R;
import com.zph.cvideo.define.CONST_QUERY;
import com.zph.cvideo.inject.ApplicationContext;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.Reply;

/**
 * @author zph
 * Created by zph on 2018/8/6.
 */
@Singleton
public class AppDataVideoHelp implements DataVideoHelp {
    private Context mContext;
    @Inject
    public AppDataVideoHelp(@ApplicationContext Context context){
        this.mContext=context;
    }
    @Override
    public Observable<List<HashMap<String,String>>> loadVideoFiled() {
        Observable cc = Observable.create(new ObservableOnSubscribe<List<HashMap<String,String>>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HashMap<String,String>>> e) throws Exception {
                List<HashMap<String,String>>list= getVideoList();
                e.onNext(list);
            }
        });
        return cc;
    }





    private List<HashMap<String,String>> getVideoList(){
        final List<HashMap<String,String>> mapList=new ArrayList<>();
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.app_toast_no_cache), Toast.LENGTH_SHORT).show();
            return null;
        }
        Uri mImageUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = mContext.getContentResolver();

        Cursor mCursor = mContentResolver.query(mImageUri, null,
                MediaStore.Video.Media.MIME_TYPE + "=? or " + MediaStore.Video.Media.MIME_TYPE + "=?",
                new String[] { "video/3gp", "video/mp4" }, MediaStore.Video.Media.DATE_MODIFIED);

        String firstImage=null;
        HashSet<String> mDirPaths = new HashSet<>();
        while (mCursor.moveToNext()) {
            // 获取视频的路径
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
            // 拿到第一个视频的路径
            if (firstImage == null)
                firstImage = path;
            // 获取该图片的父路径名
            File parentFile = new File(path).getParentFile();
            if (parentFile == null)
                continue;
            String dirPath = parentFile.getAbsolutePath();
            String times=mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DURATION));
            HashMap<String,String> imageFloder =null;
            // 利用一个HashSet防止多次扫描同一个文件夹
            if (mDirPaths.contains(dirPath)) {
                continue;
            } else {
                mDirPaths.add(dirPath);
                // 初始化imageFloder
                imageFloder=new HashMap<>();
                imageFloder.put(CONST_QUERY.VIDEO_Dir,dirPath);
                imageFloder.put(CONST_QUERY.VIDEO_IMAGE_PATH,path);
                imageFloder.put(CONST_QUERY.VIDEO_DURATION,times);

            }
            if (parentFile.list() == null) {
                continue;
            }

            mapList.add(imageFloder);
        }
        mCursor.close();
        // 扫描完成，辅助的HashSet释放内存
        mDirPaths = null;
        return  mapList;
    }
}
