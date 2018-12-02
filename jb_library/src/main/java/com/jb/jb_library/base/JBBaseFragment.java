package com.jb.jb_library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jb.jb_library.R;
import com.jb.jb_library.util.LayoutUtil;
import com.jb.jb_library.view.DiffStateView;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/9/2 15:19
 * @描述： ${TODO} fragment基类，实现通用简单功能
 */

public abstract class JBBaseFragment extends Fragment {
    public DiffStateView mDiffStateView;
    public Context mContext;
    private View mView;
    public Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getContext();
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView != null) {
            showDataView();
            return mView;
        }

        mView = LayoutUtil.inflate(mContext, R.layout.base_fragment);
        mDiffStateView = (DiffStateView) mView.findViewById(R.id.diff_state_view_fl);
        getTargetView();
        init();
        initViewRefreshListener();
        return mView;
    }

    protected abstract void init();


    public void getTargetView() {
        if (setContentLayout() == 0) {
            throw new NullPointerException("ContentView is null");
        } else {
            View contentView = LayoutUtil.inflate(mContext, setContentLayout());
            mDiffStateView.addView(contentView);
            findView(contentView);
        }
    }


    /**
     * 设置内容布局文件资源文件ID
     *
     * @return
     */
    protected abstract int setContentLayout();


    /**
     * 查找控件
     *
     * @param contentView
     */
    protected abstract void findView(View contentView);


    public void showDataView() {
        mDiffStateView.setViewState(DiffStateView.VIEW_STATE_DATA);
    }

    public void showLoadingView() {
        mDiffStateView.setViewState(DiffStateView.VIEW_STATE_LOADING);
    }

    public void showEmptyView() {
        mDiffStateView.setViewState(DiffStateView.VIEW_STATE_EMPTY);
    }

    public void showErrorView() {
        mDiffStateView.setViewState(DiffStateView.VIEW_STATE_ERROR);
    }

    public void showNoNetwork() {
        mDiffStateView.setViewState(DiffStateView.VIEW_STATE_NO_NETWROK);
    }


    private void initViewRefreshListener() {
        mDiffStateView.setViewRefreshListener(new DiffStateView.ViewRefreshListener() {
            @Override
            public void errorViewRefresh() {
                errorRetryRefreshListener();
            }

            @Override
            public void emptyViewRefresh() {
                emptyRetryRefreshListener();
            }
        });
    }

    /**
     * 空视图刷新的监听,子类只需重写该方法
     */
    protected void emptyRetryRefreshListener() {

    }

    /**
     * 错误视图刷新的监听，子类只需重写该方法
     */
    protected void errorRetryRefreshListener() {

    }

    /**
     * 跳转到指定的Activity,并返回相应的结果,不需要传递参数
     *
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(Class<?> targetActivity) {
      /*  if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            startActivity(new Intent(mActivity, targetActivity), ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
        } else {
        }*/
        startActivity(new Intent(mActivity, targetActivity));
    }


    /**
     * 跳转到指定的Activity,并返回相应的结果,不需要传递参数
     *
     * @param targetActivity 要跳转的目标Activity
     * @param requestCode    请求码
     */
    protected final void startActivityForResult(Class<?> targetActivity, int requestCode) {
        startActivityForResult(targetActivity, null, null, requestCode);
    }

    /**
     * 跳转到指定的Activity,并返回相应的结果,需要传递参数
     *
     * @param targetActivity 要跳转的目标Activity
     * @param extras         Activity之间传递数据
     * @param requestCode    请求码
     */
    protected final void startActivityForResult(Class<?> targetActivity, String name, Bundle extras, int requestCode) {
        Intent intent = new Intent(mContext, targetActivity);
        if (null != extras && !extras.isEmpty()) {
            intent.putExtra(name, extras);
        }
        /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            startActivityForResult(intent, requestCode, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
        } else {
        }*/
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDiffStateView != null) {
            mDiffStateView.releaseVaryView(); //销毁后释放View
        }
    }
}
