package com.zph.cvideo.holder;

/**
 * @author zph
 * Created by zph on 2018/8/6.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zph.cvideo.utils.ImageLoader;
import com.zph.cvideo.utils.constants.Constants;

public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder(context, parent, layoutId, position);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }
        return holder;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为TxtView设置文本
     *
     * @param viewId
     * @param txt
     * @return
     */
    public ViewHolder setTextView(int viewId, String txt) {
        TextView view = getView(viewId);
        view.setText(txt);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }
    //拿到视频文件的封面图片（视频帧图片）
    public  Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        //压缩宽高比的一半
        if(bitmap==null){
            return bitmap;
        }
        Bitmap bit = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);
        return bit;
    }


    public ViewHolder setImageByUrl(int viewId, String url, int type) {
        if (type == Constants.IMAGE_TYPE ) {
            ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(url,
                    (ImageView) getView(viewId));
        } else if (type == Constants.VIDEO_TYPE) {
            //优化视频封面图片加载缓慢效果
            //视频的封面图片找不到url地址，根据其生成bitmap,然后放到缓存中，路径当key
            //先从缓存里面拿,缓存中没有就存入
            if(ImageLoader.getInstance(3, ImageLoader.Type.LIFO).
                    getBitmapFromLruCache(url)==null){
                ImageLoader.getInstance(3, ImageLoader.Type.LIFO)
                        .addBitmapToLruCache(url,getVideoThumbnail(url));
                ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(url,
                        (ImageView) getView(viewId));
            }
            else{
                ImageLoader.getInstance(3,ImageLoader.Type.LIFO).loadImage(url,
                        (ImageView) getView(viewId));
            }


        }
        return this;
    }

}

