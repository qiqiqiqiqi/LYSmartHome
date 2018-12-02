package com.jb.jb_library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import com.jb.jb_library.util.LayoutUtil;
import com.jb.jb_library.util.StatusBarUtil;
import com.jb.jb_library.util.UIUtil;
import com.jb.jb_library.view.DiffStateView;
import com.jb.jb_library.R;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/26 9:36
 * @描述： ${TODO} activity基类，实现通用简单功能
 */

public abstract class JBBaseActivity extends AppCompatActivity {

    public DiffStateView mDiffStateView;
    private ActivityManager mActivityManager;
    public Context mContext;
    public Activity mActivity;
    public View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.base_activity);
      /*  //手机版本大于21时,调用爆炸的动画
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
            //退出时使用
            getWindow().setExitTransition(explode);
            //第一次进入时使用
            getWindow().setEnterTransition(explode);
            //再次进入时使用
            getWindow().setReenterTransition(explode);
        }*/
        StatusBarUtil.setColor(this, UIUtil.getColor(R.color.color_A37A43), 5);
        this.mContext = this;
        this.mActivity = this;
        initTitle();
        mDiffStateView = (DiffStateView) findViewById(R.id.diff_state_view_fl);
        mContentView = getTargetView();

        initViewRefreshListener();

        mActivityManager = ActivityManager.getAppManager();
        mActivityManager.addActivity(this);
    }

    /**
     * 初始化操作
     */
    protected abstract void init();

    @CallSuper
    @Override
    public void onResume() {
        // 竖屏显示
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //隐藏软键盘
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onResume();
    }

    public View getTargetView() {
        if (setContentLayout() == 0) {
            throw new NullPointerException("ContentView is null");
        } else {
            View contentView = LayoutUtil.inflate(mContext, setContentLayout());
            mDiffStateView.addView(contentView);
            findView(contentView);
            init();
            return contentView;
        }
    }

    /**
     * 设置内容布局文件资源文件ID
     *
     * @return
     */
    protected abstract int setContentLayout();

    /**
     * 初始化顶部标题状态栏
     */
    protected abstract void initTitle();

    /**
     * 查找控件
     *
     * @param contentView
     */
    protected abstract void findView(View contentView);

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
    public void emptyRetryRefreshListener() {

    }

    /**
     * 错误视图刷新的监听，子类只需重写该方法
     */
    public void errorRetryRefreshListener() {

    }

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

    public void showTimeOut() {
        mDiffStateView.setViewState(DiffStateView.VIEW_STATE_TIMEOUT);
    }

    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(@NonNull Class<?> targetActivity) {

       /* if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            startActivity(new Intent(this, targetActivity), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
        }*/
        startActivity(new Intent(this, targetActivity));
    }

    /**
     * 跳转到指定的Activity
     *
     * @param data           Activity之间传递数据
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(@NonNull String name, @NonNull Bundle data, @NonNull Class<?> targetActivity) {
        final Intent intent = new Intent();
        intent.putExtra(name, data);
        intent.setClass(this, targetActivity);

       /* if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
        }*/
        startActivity(intent);

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
        Intent intent = new Intent(this, targetActivity);
        if (null != extras && !extras.isEmpty()) {
            intent.putExtra(name, extras);
        }
       /* if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            startActivityForResult(intent, requestCode, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
        }*/
            startActivityForResult(intent, requestCode);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDiffStateView != null) {
            mDiffStateView.releaseVaryView(); //销毁后释放View
        }

    }

    @Override
    public void finish() {
        super.finish();
        // 需要在startAtivity方法或者是finish方法调用之后立即执行
        //若进入b或者是离开a时不需要动画效果，则可以传值为0
/*
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            overridePendingTransition(0, R.anim.anim_exit);
        }
*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 需要在startAtivity方法或者是finish方法调用之后立即执行
        //若进入b或者是离开a时不需要动画效果，则可以传值为0
/*
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            overridePendingTransition(0, R.anim.anim_exit);
        }
*/
    }
}
