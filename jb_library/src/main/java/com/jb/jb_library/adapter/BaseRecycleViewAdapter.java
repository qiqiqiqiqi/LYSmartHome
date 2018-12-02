package com.jb.jb_library.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jb.jb_library.holder.RecycleCommonViewHolder;

import java.util.List;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/10/31 17:17
 * @描述： ${TODO} recycleview 通用的Adapter,只支持单一布局
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecycleCommonViewHolder> {
    public  Activity mActivity;
    private List<T>  mDatas;
    //布局id
    public  int      mLayoutId;

    public BaseRecycleViewAdapter(Activity activity, List<T> datas, int layoutId) {
        this.mActivity = activity;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
    }

    @Override
    public RecycleCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecycleCommonViewHolder.getViewHolder(mActivity, parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecycleCommonViewHolder holder, int position) {
        convert(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mLayoutId;
    }

    /**
     * 留给调用者去实现
     *
     * @param holder
     * @param t
     */
    public abstract void convert(RecycleCommonViewHolder holder, T t, int position);
}
