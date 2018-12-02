package com.jb.jb_library.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jb.jb_library.helper.FrescoHelper;
import com.jb.jb_library.util.LayoutUtil;
import com.jb.jb_library.intf.OnItemClickListener;
import com.jb.jb_library.intf.OnItemLongClickListener;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/30 11:11
 * @描述： ${TODO} recycleview的通用ViewHolder
 */

public class RecycleCommonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    /**
     * 这个稀疏数组，可以提高效率的
     */
    private final SparseArray<View>       views;
    private       View                    convertView;
    private       OnItemClickListener     mOnItemClickListener;
    private       OnItemLongClickListener mOnItemLongClickListener;

    /**
     * 如果用到了，比如我们用glide加载图片的时候，还有其他的需要用到上下文的时候
     */
    private Context mContext;

    public RecycleCommonViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        convertView = itemView;
        mContext = itemView.getContext();
        convertView.setOnClickListener(this);
        convertView.setOnLongClickListener(this);
    }

    public static RecycleCommonViewHolder getViewHolder(Context context, ViewGroup parent, int layountId) {
        View itemView = LayoutUtil.inflate(context, parent, layountId);
        RecycleCommonViewHolder holder = new RecycleCommonViewHolder(itemView);
        return holder;
    }

    /**
     * 返回一个具体的view对象 ,借鉴的base-adapter-helper中的方法
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置文本数据
     *
     * @param layoutId
     * @param text
     * @return
     */
    public RecycleCommonViewHolder setText(int layoutId, String text) {
        TextView view = getView(layoutId);
        view.setText(text);
        return this;
    }

    /**
     * 加载显示图片的
     * @param resId
     * @param url
     *//*
    public RecycleCommonViewHolder setImageByUrl(int resId, final String url, int loadId) {
         ImageView imageView = getView(resId);
         ImageView loadingIv = getView(loadId);

        GlideHelper.setImageView(mContext,url,imageView,loadingIv);
        return this;
    }*/

    /**
     * 加载显示图片的
     *
     * @param layoutId
     * @param url
     */
    public RecycleCommonViewHolder setImageByUrl(int layoutId, final String url) {
        SimpleDraweeView draweeView = getView(layoutId);
        FrescoHelper.loadUrl(url, draweeView, true);
        return this;
    }

    public RecycleCommonViewHolder setImageByResource(int layoutId, int resId) {
        ImageView imageView = getView(layoutId);
        imageView.setImageResource(resId);
        return this;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    /**
     * 设置recycleView条目的点击事件
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(getAdapterPosition());
            return true;
        }
        return false;
    }
}
