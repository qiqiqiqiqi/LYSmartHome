package com.jb.jb_library.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jb.jb_library.R;
import com.jb.jb_library.holder.RecycleCommonViewHolder;

import java.util.List;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/30 11:07
 * @描述： ${TODO} recycleview 通用的Adapter,只支持单一布局,支持上拉加载更多
 */

public abstract class BaseRefreshRecycleViewAdapter<T> extends RecyclerView.Adapter<RecycleCommonViewHolder>{

    public List<T> mDatas;
    //布局id
    private int     mLayoutId;

    protected boolean mLoadMore;
    protected boolean mLoadFinish; // 加载完成
    public Context mContext;
    private SwipeRefreshLayout mRefreshLayout;
    public Activity mActivity;

    /**
     * 构造函数,传入数据的list和布局,默认不支持上拉加载更多
     * @param datas
     * @param layoutId
     */
    public BaseRefreshRecycleViewAdapter(Context context, RecyclerView recyclerView, List<T> datas, int layoutId, SwipeRefreshLayout refreshLayout) {
        this(context,recyclerView,datas,false,layoutId,refreshLayout);
    }

    /**
     * 构造函数,传入数据的list和布局
     *
     * @param datas
     * @param layoutId
     */
    public BaseRefreshRecycleViewAdapter(Context context, RecyclerView recyclerView, List<T> datas, boolean loadMore, int layoutId, SwipeRefreshLayout refreshLayout) {
        this.mContext = context;
        mDatas = datas;
        this.mLayoutId = layoutId;
        this.mLoadMore = loadMore;
        this.mRefreshLayout = refreshLayout;
        setSpanCount(recyclerView);
    }

    @Override
    public RecycleCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecycleCommonViewHolder.getViewHolder(mContext,parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecycleCommonViewHolder holder, int position) {
        if(getItemViewType(position) == R.layout.item_rcy_footer){
            checkLoadStatus(holder);
        }else{
            convert(holder, mDatas.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        int layoutId = 0;

        if(mLoadMore && position + 1 == getItemCount()){
            layoutId = R.layout.item_rcy_footer;
        }else{
            layoutId = mLayoutId;
        }

        return layoutId;
    }


    @Override
    public int getItemCount() {
        if(mDatas != null)
            return mLoadMore?mDatas.size()+1:mDatas.size();
        return 0;
    }

    /**
     * 留给调用者去实现
     *
     * @param holder
     * @param t
     */
    public abstract void convert(RecycleCommonViewHolder holder, T t) ;

    /**
     * 设置每个条目占用的列数
     * @param recyclerView recycleView
     */
    private void setSpanCount(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                   return setItemOccupyArea(gridLayoutManager,type);
                }
            });
        }
    }

    public int setItemOccupyArea(GridLayoutManager gridLayoutManager, int type) {
        // 若是最后一个 且需要加载更多，则强制让最后一个条目占满横屏
        if (type == R.layout.item_rcy_footer) {
            return gridLayoutManager.getSpanCount();
        } else {
            return 1;
        }
    }

    public void checkLoadStatus(RecycleCommonViewHolder holder) {
        TextView tv = holder.getView(R.id.tv_item_footer_load_more);
        ProgressBar pb = holder.getView(R.id.pb_item_footer_loading);
        if(mLoadFinish){
            tv.setText("已经到底了");
            tv.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
        }else if(mRefreshLayout != null && !mRefreshLayout.isRefreshing()){ //没有正在下拉刷新数据
            if(getItemCount()> 1 && mLoadMore){
                tv.setText("正在加载中...");
                tv.setVisibility(View.VISIBLE);
                pb.setVisibility(View.VISIBLE);
            }else {
                tv.setVisibility(View.GONE);
                pb.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 加载更多
     * @param datas
     */
    public void loadMore(List<T> datas){
        if(mLoadMore && !mLoadFinish){
            if(datas == null || datas.size() == 0){ // 结束标志
                mLoadFinish = true;
            }else {
                mDatas.addAll(datas);
            }
            notifyDataSetChanged();
        }
    }

    public boolean isLoadFinish() {
        return mLoadFinish;
    }
}
