package com.jb.jb_library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jb.jb_library.R;
import com.jb.jb_library.util.UIUtil;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/11/8 8:51
 * @描述： ${TODO} 不同状态的视图（加载，空，错误，数据）
 */

public class DiffStateView extends FrameLayout implements View.OnClickListener {

    public static final int VIEW_STATE_DATA = 0;//显示数据的状态

    public static final int VIEW_STATE_ERROR = 1;//错误视图的状态

    public static final int VIEW_STATE_EMPTY = 2;//空视图状态

    public static final int VIEW_STATE_LOADING = 3;//加载中视图的状态

    public static final int VIEW_STATE_NO_NETWROK = 4;//没有网络的状态

    public static final int VIEW_STATE_TIMEOUT = 5;//超时的状态

    private LayoutInflater mInflater;
    private View mDataView;

    private View mLoadingView;

    private View mErrorView;

    private View mEmptyView;

    private int mViewState = VIEW_STATE_DATA; //视图状态默认是显示数据加载的状态

    private TextView mLoadingPromotTv;
    private ImageView mAnimationIv;
    private Animation mAnimation;
    private ImageView mNoNetworkIv;
    private TextView mNoNetworkTv;
    private ImageView mEmptyIv;
    private TextView mEmptyMsgTv;
    private ViewRefreshListener mListener;
    private View mNoNetworkView;
    private View mTimeOutView;
    private View mCurrView;

    public DiffStateView(Context context) {
        this(context, null);
    }

    public DiffStateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mInflater = LayoutInflater.from(getContext());
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiffStateView);

        int loadingViewResId = a.getResourceId(R.styleable.DiffStateView_loading_view, -1);
        if (loadingViewResId > -1) {
            mLoadingView = mInflater.inflate(loadingViewResId, this, false);
            mLoadingPromotTv = (TextView) mLoadingView.findViewById(R.id.loading_promot_tv);
            mAnimationIv = (ImageView) mLoadingView.findViewById(R.id.animation_iv);
            addView(mLoadingView);
            mLoadingView.setVisibility(GONE);
        }

        int emptyViewResId = a.getResourceId(R.styleable.DiffStateView_empty_view, -1);
        if (emptyViewResId > -1) {
            mEmptyView = mInflater.inflate(emptyViewResId, this, false);
            mEmptyView.findViewById(R.id.refresh_empty_view_ll).setOnClickListener(this);
            mEmptyIv = (ImageView) mEmptyView.findViewById(R.id.refresh_empty_iv);
            mEmptyMsgTv = (TextView) mEmptyView.findViewById(R.id.refresh_empty_tv);
            addView(mEmptyView);
            mEmptyView.setVisibility(GONE);
        }

        int noNetworkViewResId = a.getResourceId(R.styleable.DiffStateView_no_network_view, -1);
        if (noNetworkViewResId > -1) {
            mNoNetworkView = mInflater.inflate(noNetworkViewResId, this, false);
            mNoNetworkView.findViewById(R.id.refresh_not_network_view_ll).setOnClickListener(this);
            mNoNetworkIv = (ImageView) mNoNetworkView.findViewById(R.id.not_network_iv);
            mNoNetworkTv = (TextView) mNoNetworkView.findViewById(R.id.not_network_msg_tv);
            addView(mNoNetworkView);
            mNoNetworkView.setVisibility(GONE);
        }

        int timeOutViewResId = a.getResourceId(R.styleable.DiffStateView_time_out_view, -1);
        if (timeOutViewResId > -1) {
            mTimeOutView = mInflater.inflate(timeOutViewResId, this, false);
            mTimeOutView.findViewById(R.id.layout_time_out_view_rl).setOnClickListener(this);
            addView(mTimeOutView);
            mTimeOutView.setVisibility(GONE);
        }

        int errorViewResId = a.getResourceId(R.styleable.DiffStateView_error_view, -1);
        if (errorViewResId > -1) {
            mErrorView = mInflater.inflate(errorViewResId, this, false);
            mErrorView.findViewById(R.id.layout_error_view_rl).setOnClickListener(this);
            addView(mErrorView);
            mErrorView.setVisibility(GONE);
        }


        a.recycle();
    }


    @Override
    public void addView(View child) {
        if (isValidContentView(child))
            mDataView = child;
        mCurrView = mDataView;
        super.addView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isValidContentView(child))
            mDataView = child;
        mCurrView = mDataView;
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isValidContentView(child))
            mDataView = child;
        mCurrView = mDataView;
        super.addView(child, width, height);
    }

    /**
     * 判断当前的view是否是未定义的view
     *
     * @param view
     * @return
     */
    private boolean isValidContentView(View view) {
        if (mDataView != null && mDataView != view) {
            return false;
        }

        return view != mLoadingView && view != mErrorView && view != mEmptyView && view != mNoNetworkView && view != mTimeOutView;
    }

    public void setViewState(int state) {
        if (state != mViewState) {
            mViewState = state;
            setView(mViewState);
        }
    }

    public void setView(int viewState) {
        switch (viewState) {
            case VIEW_STATE_LOADING:
                if (mLoadingView == null) {
                    return;
                }

                if (mCurrView != mLoadingView) {
                    mCurrView.setVisibility(GONE);
                }
                mLoadingView.setVisibility(View.VISIBLE);
                mCurrView = mLoadingView;
                if (mAnimation == null) {
                    mAnimation = AnimationUtils.loadAnimation(UIUtil.getContext(), R.anim.loading_anim);
                    LinearInterpolator lin = new LinearInterpolator();
                    //LinearInterpolator为匀速效果，Accelerateinterpolator为加速效果、DecelerateInterpolator为减速效果
                    mAnimation.setInterpolator(lin);
                }

                startProgressLoading();

                break;

            case VIEW_STATE_EMPTY:
                if (mEmptyView == null) {
                    return;
                }

                if (mCurrView != mEmptyView) {
                    mCurrView.setVisibility(GONE);
                }

                mEmptyView.setVisibility(View.VISIBLE);
                if (mCurrView == mLoadingView)
                    stopProgressLoading();

                mCurrView = mEmptyView;
                break;

            case VIEW_STATE_ERROR:

                if (mErrorView == null) {
                    return;
                }

                if (mCurrView != mErrorView) {
                    mCurrView.setVisibility(GONE);
                }

                mErrorView.setVisibility(View.VISIBLE);
                if (mCurrView == mLoadingView)
                    stopProgressLoading();

                mCurrView = mErrorView;
                break;

            case VIEW_STATE_NO_NETWROK:

                if (mNoNetworkView == null) {
                    return;
                }

                if (mCurrView != mNoNetworkView) {
                    mCurrView.setVisibility(GONE);
                }

                mNoNetworkView.setVisibility(View.VISIBLE);
                if (mCurrView == mLoadingView)
                    stopProgressLoading();

                mCurrView = mNoNetworkView;
                break;

            case VIEW_STATE_TIMEOUT:

                if (mTimeOutView == null) {
                    return;
                }

                if (mCurrView != mTimeOutView) {
                    mCurrView.setVisibility(GONE);
                }

                mTimeOutView.setVisibility(View.VISIBLE);

                if (mCurrView == mLoadingView)
                    stopProgressLoading();

                mCurrView = mTimeOutView;
                break;


            case VIEW_STATE_DATA:
            default:
                if (mDataView == null) {
                    return;
                }

                if (mCurrView != mDataView) {
                    mCurrView.setVisibility(GONE);
                }

                mDataView.setVisibility(View.VISIBLE);

                if (mCurrView == mLoadingView)
                    stopProgressLoading();

                mCurrView = mDataView;
                break;
        }
    }

    @Nullable
    public View getView(int state) {
        switch (state) {
            case VIEW_STATE_LOADING:
                return mLoadingView;

            case VIEW_STATE_DATA:
                return mDataView;

            case VIEW_STATE_EMPTY:
                return mEmptyView;

            case VIEW_STATE_ERROR:
                return mErrorView;

            default:
                return null;
        }
    }

    private void stopProgressLoading() {
        if (mAnimation != null && mAnimation.hasStarted()) {
            mAnimationIv.clearAnimation();
        }
    }

    private void startProgressLoading() {
        if (mAnimation != null && mAnimation.hasStarted() && mAnimationIv != null) {
            mAnimationIv.clearAnimation();
            mAnimationIv.startAnimation(mAnimation);
        } else if (mAnimation != null && mAnimationIv != null) {
            mAnimationIv.startAnimation(mAnimation);
        }
    }

    public void releaseVaryView() {
        mDataView = null;
        mEmptyView = null;
        mErrorView = null;
        mLoadingView = null;
        mNoNetworkView = null;
        mTimeOutView = null;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.refresh_not_network_view_ll) {
            mListener.errorViewRefresh();
        } else if (i == R.id.refresh_empty_view_ll) {
            mListener.emptyViewRefresh();
        } else if (i == R.id.layout_time_out_view_rl) {
            mListener.errorViewRefresh();
        }else if (i == R.id.layout_error_view_rl) {
            mListener.errorViewRefresh();
        }
    }

    public void setViewRefreshListener(ViewRefreshListener listener) {
        this.mListener = listener;
    }

    public interface ViewRefreshListener {
        void errorViewRefresh();

        void emptyViewRefresh();
    }

    public void setErrorImageView(int resId) {
        mNoNetworkIv.setImageResource(resId);
    }

    public void setErrorPromotMsg(String text) {
        mNoNetworkTv.setText(text);
    }

    public void setErrorPromotMsg(int resId) {
        mNoNetworkTv.setText(resId);
    }

    public void setEmptyImageView(int resId) {
        mEmptyIv.setImageResource(resId);
    }

    public void setEmptyPromotMsg(String text) {
        mEmptyMsgTv.setText(text);
    }

    public void setEmptyPromotMsg(int resId) {
        mEmptyMsgTv.setText(resId);
    }

    public void setLoadingPromotMsg(String text) {
        mLoadingPromotTv.setText(text);
    }

    public void setLoadingPromotMsg(int resId) {
        mLoadingPromotTv.setText(resId);
    }
}
