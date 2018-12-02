package com.jb.jb_library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jb.jb_library.R;
import com.jb.jb_library.adapter.BannerAdapter;
import com.jb.jb_library.intf.ViewPagerOnItemClickListener;
import com.jb.jb_library.util.DensityUtil;
import com.jb.jb_library.util.UIUtil;

import java.util.List;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/9/13 17:08
 * @描述： ${TODO} 自定义Banner无限轮播控件
 */

public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener, ViewPagerOnItemClickListener {
    //选中显示Indicator
    private int mSelectRes = R.drawable.shape_dots_select;

    //非选中显示Indicator
    private int mUnSelcetRes = R.drawable.shape_dots_default;
    private AutoScrollLoopViewPager      mViewPager;
    private LinearLayout                 mPoiontsContainer;
    private Context                      mContext;
    private ViewPagerOnItemClickListener mListener;
    private int                          mLength;
    private BannerAdapter                mAdapter;
    private boolean                      mIsLoopScroll;
    private String                       mDelayTime;
    private int[]                        mImages;
    private List<String>                 mImageUrls;
    private boolean                      mIsCycle;
    private boolean                      mShowNum;
    private TextView                     mNumTv;

    public BannerView(Context context) {

        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        //默认自动轮播
        mIsLoopScroll = typedArray.getBoolean(R.styleable.BannerView_isLoopSrcoll, true);
        mDelayTime = typedArray.getString(R.styleable.BannerView_delay_time);
        mIsCycle = typedArray.getBoolean(R.styleable.BannerView_isCycle, true);//默认可循环
        mShowNum = typedArray.getBoolean(R.styleable.BannerView_showNum, false);

        typedArray.recycle();
    }

    public void setImagesUrl(List<String> imagesUrl) {
        initLayout();
        initImgFromNet(imagesUrl);
        setData();
    }

    public void setImagesRes(int[] imagesRes) {
        initLayout();
        initImgFromRes(imagesRes);
        setData();
    }


    private void initLayout() {
        View view = View.inflate(mContext, R.layout.layout_custom_banner, this);
        mViewPager = (AutoScrollLoopViewPager) view.findViewById(R.id.banner_viewpager);
        mPoiontsContainer = (LinearLayout) view.findViewById(R.id.banner_points_container);
        mPoiontsContainer.removeAllViews();
    }

    private void initImgFromNet(List<String> imagesUrl) {
        this.mImageUrls = imagesUrl;
        mLength = imagesUrl.size();
        if (!mShowNum) {
            for (int i = 0; i < mLength; i++) {
                View dotView = new View(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        DensityUtil.dp2px(5),
                        DensityUtil.dp2px(5));
                params.leftMargin = 5;
                params.rightMargin = 5;
                dotView.setBackgroundResource(mSelectRes);
                if (i != 0) {
                    params.leftMargin = DensityUtil.dp2px(5);
                    dotView.setBackgroundResource(mUnSelcetRes);
                }
                mPoiontsContainer.addView(dotView, params);
            }
        } else {
            TextView numTv = new TextView(mContext);
            numTv.setTextColor(UIUtil.getColor(R.color.color_888888));
            numTv.setText("1/" + mLength);
            mPoiontsContainer.addView(numTv);
        }
    }

    private void initImgFromRes(int[] imagesRes) {
        this.mImages = imagesRes;
        mLength = imagesRes.length;
        if (!mShowNum) {
            for (int i = 0; i < mLength; i++) {
                View dotView = new View(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        DensityUtil.dp2px(5),
                        DensityUtil.dp2px(5));
                params.leftMargin = 5;
                params.rightMargin = 5;
                dotView.setBackgroundResource(mSelectRes);
                if (i != 0) {
                    params.leftMargin = DensityUtil.dp2px(5);
                    dotView.setBackgroundResource(mUnSelcetRes);
                }
                mPoiontsContainer.addView(dotView, params);
            }
        } else {
            mNumTv = new TextView(mContext);
            mNumTv.setTextColor(UIUtil.getColor(R.color.color_888888));
            mNumTv.setText("1/" + mLength);
            mPoiontsContainer.addView(mNumTv);
        }

    }

    private void setData() {
        if (mAdapter == null) {
            if (mImages != null && mImages.length > 0) {
                mAdapter = new BannerAdapter(mContext, mImages);
            } else {
                mAdapter = new BannerAdapter(mContext, mImageUrls);
            }
        }

        mViewPager.setAdapter(mAdapter);
        if (mIsLoopScroll) {
            mViewPager.setInterval(Long.parseLong(mDelayTime));
            mViewPager.startAutoScroll();
        }

        mViewPager.setCycle(mIsCycle);
        mViewPager.addOnPageChangeListener(this);

        mAdapter.setViewPagerOnItemClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mIsCycle) {
            if (!mShowNum) {
                for (int i = 0; i < mLength; i++) {
                    View childView = mPoiontsContainer.getChildAt(i);
                    if (position == i + 1) {
                        childView.setBackgroundResource(R.drawable.shape_dots_select);
                    } else {
                        childView.setBackgroundResource(R.drawable.shape_dots_default);
                    }
                }
            } else {
                if (position != mLength + 1 && position != 0) {
                    mNumTv.setText(position + "/" + mLength);
                }
            }

        } else {
            if (!mShowNum) {
                for (int i = 0; i < mLength; i++) {
                    View childView = mPoiontsContainer.getChildAt(i);
                    if (position == i) {
                        childView.setBackgroundResource(R.drawable.shape_dots_select);
                    } else {
                        childView.setBackgroundResource(R.drawable.shape_dots_default);
                    }
                }
            } else {
                mNumTv.setText(position + "/" + mLength);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置ViewPager的Item点击回调事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        if (mListener != null) {
            mListener.onItemClick(position);
        }
    }

    /**
     * 设置ViewPager的Item点击回调监听
     */
    public void setViewpagerItemClickListener(ViewPagerOnItemClickListener listener) {
        this.mListener = listener;
    }

    public void startAutoScroll() {
        mViewPager.startAutoScroll();
    }

    public void stopAutoScroll() {
        mViewPager.stopAutoScroll();
    }
}
