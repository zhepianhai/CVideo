package com.zph.cvideo.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zph.cvideo.holder.ViewHolder;
import com.zph.cvideo.utils.ImageLoader;
import com.zph.cvideo.utils.constants.Constants;

import java.util.List;

/**
 * @author zph
 * Created by zph on 2018/8/6.
 */

public abstract class CommonAdapter<T> extends BaseAdapter{
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }
    @Override
    public int getCount(){
        return mDatas.size();
    }

    @Override
    public T getItem(int position)  {
        return mDatas.get(position);
    }
    @Override
    public long getItemId(int position)    {

        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)    {

        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();

    }

    public abstract void convert(ViewHolder helper, T item);

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent)    {

        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }



}

