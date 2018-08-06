package com.zph.cvideo.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import com.zph.cvideo.R;
import com.zph.cvideo.define.CONST_QUERY;
import com.zph.cvideo.holder.ViewHolder;
import com.zph.cvideo.utils.UtilTime;
import com.zph.cvideo.utils.constants.Constants;

import java.util.HashMap;
import java.util.List;

/**
 * @author zph
 * Created by zph on 2018/8/6.
 */

public class AdpGridView extends  CommonAdapter{
    public AdpGridView(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }
    @Override
    public void convert(ViewHolder helper, Object item) {
        HashMap<String, String> items = (HashMap<String, String>) item;
        if (items.containsKey(CONST_QUERY.VIDEO_Dir)) {
            helper.setTextView(R.id.item_gridview_txt, "" + UtilTime.getTime(Integer.valueOf(items.get(CONST_QUERY.VIDEO_DURATION))));
        }
        if(items.containsKey(CONST_QUERY.VIDEO_IMAGE_PATH)){
            helper.setImageByUrl(R.id.item_gridview_image,items.get(CONST_QUERY.VIDEO_IMAGE_PATH), Constants.VIDEO_TYPE);
        }
    }

    //拿到视频文件的封面图片（视频帧图片）
    public Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        //压缩宽高比的一半
        if(bitmap==null){
            return bitmap;
        }
        Bitmap bit = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() , bitmap.getHeight() , true);
        return bit;
    }
}
