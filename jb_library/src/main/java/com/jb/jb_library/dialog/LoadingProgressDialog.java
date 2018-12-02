package com.jb.jb_library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.jb.jb_library.R;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/10/19 17:14
 * @描述： ${TODO}
 */

public class LoadingProgressDialog extends Dialog {

    private ImageView mLoadingIv;
    private Context   mContext;
    private Animation mAnimation;
    private TextView mPromotTv;

    public LoadingProgressDialog(Context context) {
        this(context, R.style.LoadingDialogStyle);
    }

    public LoadingProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_progress_dialog);
        mLoadingIv = (ImageView) findViewById(R.id.loading_iv);
        mPromotTv = (TextView) findViewById(R.id.promot_tv);
        setCancelable(true);// 可以用“返回键”取消
        setOnCancelListener(new OnCancelListener() {//取消监听
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mLoadingIv.clearAnimation();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        if (mAnimation == null) {
            mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.loading_anim);
            LinearInterpolator lin = new LinearInterpolator();
            mAnimation.setInterpolator(lin);
        }
        mLoadingIv.startAnimation(mAnimation);
    }

    @Override
    public void dismiss() {
        if (mLoadingIv != null) {
            mLoadingIv.clearAnimation();
        }
        super.dismiss();
    }


    public void showPromotText() {
        mPromotTv.setVisibility(View.VISIBLE);
    }

    public void setPromotText(String text) {
        mPromotTv.setText(text);
    }


}
