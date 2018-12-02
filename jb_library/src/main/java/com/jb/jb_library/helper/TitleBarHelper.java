package com.jb.jb_library.helper;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jb.jb_library.R;
import com.jb.jb_library.util.StringUtil;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/9/9 16:56
 * @描述： ${TODO} 顶部标题状态栏的设置
 */

public class TitleBarHelper {

    private TextView mSyncTv;
    private RelativeLayout mOfflineRl;
    private ImageView mLeftIv;
    private ImageView mLeftVerticalIv;
    private ImageView mCenterIv;
    private ImageView mRightIv;
    private ImageView mRightVerticalIv;
    private TextView mLeftTv;
    private TextView mLeftVerticalTv;
    private TextView mRightTv;
    private TextView mRightVerticalTv;
    private TextView mCenterTv;
    private RelativeLayout mLeftRl;
    private RelativeLayout mRightRl;
    public RelativeLayout mTitleRl;
    private TextView mRightTvTwo;
    private TextView mRightTvOne;

    public TitleBarHelper(Activity activity) {

        mTitleRl = (RelativeLayout) activity.findViewById(R.id.titlebar_rl);
        mLeftIv = (ImageView) mTitleRl.findViewById(R.id.titlebar_left_iv);
        mLeftVerticalIv = (ImageView) mTitleRl.findViewById(R.id.titlebar_left_vertical_iv);
        mCenterIv = (ImageView) mTitleRl.findViewById(R.id.titlebar_center_iv);
        mRightIv = (ImageView) mTitleRl.findViewById(R.id.titlebar_right_iv);
        mRightVerticalIv = (ImageView) mTitleRl.findViewById(R.id.titlebar_right_vertical_iv);

        mLeftTv = (TextView) mTitleRl.findViewById(R.id.titlebar_left_tv);
        mLeftVerticalTv = (TextView) mTitleRl.findViewById(R.id.titlebar_left_vertical_tv);

        mRightTv = (TextView) mTitleRl.findViewById(R.id.titlebar_right_tv);
        mRightVerticalTv = (TextView) mTitleRl.findViewById(R.id.titlebar_right_vertical_tv);
        mCenterTv = (TextView) mTitleRl.findViewById(R.id.titlebar_center_tv);

        mLeftRl = (RelativeLayout) mTitleRl.findViewById(R.id.titlebar_left_rl);
        mRightRl = (RelativeLayout) mTitleRl.findViewById(R.id.titlebar_right_rl);
        mOfflineRl = (RelativeLayout) mTitleRl.findViewById(R.id.offline_rl);
        mRightTvOne = (TextView) mTitleRl.findViewById(R.id.titlebar_right_tv_one);
        mRightTvTwo = (TextView) mTitleRl.findViewById(R.id.titlebar_right_tv_two);
        mSyncTv = (TextView) mTitleRl.findViewById(R.id.titlebar_sync_tv);
    }

    public TitleBarHelper(View view) {

        mTitleRl = (RelativeLayout) view.findViewById(R.id.titlebar_rl);
        mLeftIv = (ImageView) mTitleRl.findViewById(R.id.titlebar_left_iv);
        mLeftVerticalIv = (ImageView) mTitleRl.findViewById(R.id.titlebar_left_vertical_iv);
        mCenterIv = (ImageView) mTitleRl.findViewById(R.id.titlebar_center_iv);
        mRightIv = (ImageView) mTitleRl.findViewById(R.id.titlebar_right_iv);
        mRightVerticalIv = (ImageView) mTitleRl.findViewById(R.id.titlebar_right_vertical_iv);
        mLeftTv = (TextView) mTitleRl.findViewById(R.id.titlebar_left_tv);
        mLeftVerticalTv = (TextView) mTitleRl.findViewById(R.id.titlebar_left_vertical_tv);
        mRightTv = (TextView) mTitleRl.findViewById(R.id.titlebar_right_tv);
        mRightVerticalTv = (TextView) mTitleRl.findViewById(R.id.titlebar_right_vertical_tv);
        mCenterTv = (TextView) mTitleRl.findViewById(R.id.titlebar_center_tv);
        mLeftRl = (RelativeLayout) mTitleRl.findViewById(R.id.titlebar_left_rl);
        mRightRl = (RelativeLayout) mTitleRl.findViewById(R.id.titlebar_right_rl);
        mOfflineRl = (RelativeLayout) mTitleRl.findViewById(R.id.offline_rl);
        mRightTvOne = (TextView) mTitleRl.findViewById(R.id.titlebar_right_tv_one);
        mRightTvTwo = (TextView) mTitleRl.findViewById(R.id.titlebar_right_tv_two);
        mSyncTv = (TextView) mTitleRl.findViewById(R.id.titlebar_sync_tv);
    }

    /**
     * 隐藏标题状态栏
     */
    public void setHideTitleBar() {
        mTitleRl.setVisibility(View.GONE);
    }

    public void setShowTitleBar() {
        mTitleRl.setVisibility(View.VISIBLE);
    }

    /**
     * title 的背景色
     */

    public TitleBarHelper setTitleBgRes(int resid) {

        mTitleRl.setBackgroundResource(resid);

        return this;
    }

    /**
     * title的文本
     *
     * @param text
     * @return
     */
    public TitleBarHelper setMiddleTitleText(String text) {
        mCenterTv.setVisibility(StringUtil.isEmpty(text) ? View.GONE
                : View.VISIBLE);
        mCenterTv.setText(text);
        return this;
    }

    public TitleBarHelper setMiddleImageRes(int resId) {
        mCenterIv.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mCenterIv.setImageResource(resId);
        return this;
    }

    /**
     * 左边的图片按钮
     *
     * @param resId
     * @return
     */
    public TitleBarHelper setLeftImageRes(int resId) {

        mLeftIv.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mLeftIv.setBackgroundResource(resId);
        return this;
    }

    /**
     * 左边边竖直文本
     *
     * @param resId
     * @return
     */
    public TitleBarHelper setLeftImageVerticalRes(int resId) {

        mLeftVerticalIv.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mLeftVerticalIv.setBackgroundResource(resId);
        return this;
    }

    /**
     * 左边文字按钮
     *
     * @param text
     * @return
     */
    public TitleBarHelper setLeftText(String text) {

        mLeftTv.setVisibility(StringUtil.isEmpty(text) ? View.GONE : View.VISIBLE);
        mLeftTv.setText(text);

        return this;
    }

    /**
     * 左边竖直文本
     *
     * @param text
     * @return
     */
    public TitleBarHelper setLeftVerticalText(String text) {

        mLeftVerticalTv.setVisibility(StringUtil.isEmpty(text) ? View.GONE : View.VISIBLE);
        mLeftVerticalTv.setText(text);

        return this;
    }


    /**
     * 右边的图片按钮
     *
     * @param resId
     * @return
     */
    public TitleBarHelper setRightImageRes(int resId) {

        mRightIv.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mRightIv.setBackgroundResource(resId);

        return this;
    }

    /**
     * 右边竖直文本
     *
     * @param resId
     * @return
     */
    public TitleBarHelper setRightImageVerticalRes(int resId) {

        mRightVerticalIv.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mRightVerticalIv.setBackgroundResource(resId);
        return this;
    }

    /**
     * 右边文字按钮
     *
     * @param text
     * @return
     */
    public TitleBarHelper setRightText(String text) {

        mRightTv.setVisibility(StringUtil.isEmpty(text) ? View.GONE : View.VISIBLE);
        mRightTv.setText(text);

        return this;
    }

    public TitleBarHelper setRightTextOne(String text) {

        mRightTvOne.setVisibility(StringUtil.isEmpty(text) ? View.GONE : View.VISIBLE);
        mRightTvOne.setText(text);

        return this;
    }

    public TitleBarHelper setRightTextTwo(String text) {

        mRightTvTwo.setVisibility(StringUtil.isEmpty(text) ? View.GONE : View.VISIBLE);
        mRightTvTwo.setText(text);

        return this;
    }

    /**
     * 右边竖直文本
     *
     * @param text
     * @return
     */
    public TitleBarHelper setRightVerticalText(String text) {

        mRightVerticalTv.setVisibility(StringUtil.isEmpty(text) ? View.GONE : View.VISIBLE);
        mRightVerticalTv.setText(text);

        return this;
    }

    public TitleBarHelper setRightSyncText(String text) {
        mSyncTv.setVisibility(StringUtil.isEmpty(text) ? View.GONE : View.VISIBLE);
        mSyncTv.setText(text);

        return this;
    }

    public TitleBarHelper setSyncClickListener(View.OnClickListener listener) {
        mSyncTv.setOnClickListener(listener);
        return this;
    }


    /**
     * 设置左边点击事件
     */
    public TitleBarHelper setLeftClickListener(View.OnClickListener listener) {
        mLeftRl.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置右边点击事件
     */
    public TitleBarHelper setRightClickListener(View.OnClickListener listener) {
        mRightRl.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置右边点击事件
     */
    public TitleBarHelper setRightOneTvClickListener(View.OnClickListener listener) {
        mRightTvOne.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置右边点击事件
     */
    public TitleBarHelper setRightTwoTvClickListener(View.OnClickListener listener) {
        mRightTvTwo.setOnClickListener(listener);
        return this;
    }

    public TitleBarHelper showOfflineFl() {
        mOfflineRl.setVisibility(View.VISIBLE);
        return this;
    }

    public TitleBarHelper hideOfflineFl() {
        mOfflineRl.setVisibility(View.GONE);
        return this;
    }

    public TitleBarHelper hideTitleBar() {
        mTitleRl.setVisibility(View.GONE);
        return this;
    }

    public TitleBarHelper showTitleBar() {
        mTitleRl.setVisibility(View.VISIBLE);
        return this;
    }


    public boolean offlineIsShow() {
        if (mOfflineRl.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }
}
