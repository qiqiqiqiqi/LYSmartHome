package com.jb.jb_library.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import com.jb.jb_library.R;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/11/3 14:07
 * @描述： ${TODO} 时间倒计时工具类
 */

public class CountDownTimerUtil extends CountDownTimer {
    private TextView mTextView;
    public  boolean  mIsStart;

    public CountDownTimerUtil(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long l) {
        mIsStart = true;
        //        mTextView.setClickable(false); //设置不可点击
        mTextView.setText(l / 1000 + "s");  //设置倒计时时间
        mTextView.setBackgroundResource(R.mipmap.code_bg);
        mTextView.setTextColor(UIUtil.getColor(R.color.color_64b2ff));
        SpannableString spannableString = new SpannableString(mTextView.getText().toString());  //获取按钮上的文字
        ForegroundColorSpan span = new ForegroundColorSpan(Color.WHITE);
        /**
         * public void setSpan(Object what, int start, int end, int flags) {
         * 主要是start跟end，start是起始位置,无论中英文，都算一个。
         * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
         */
        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        //        mTextView.setClickable(true);//重新获得点击
        mTextView.setBackgroundResource(R.drawable.shape_common_btn_select);
        mTextView.setTextColor(UIUtil.getColor(R.color.white));
        mIsStart = false;
    }
}
