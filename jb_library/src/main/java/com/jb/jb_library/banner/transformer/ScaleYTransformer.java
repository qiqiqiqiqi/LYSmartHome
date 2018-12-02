package com.jb.jb_library.banner.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.jb.jb_library.util.DensityUtil;

/**
 * Created by zhouwei on 17/5/26.
 */

public class ScaleYTransformer implements ViewPager.PageTransformer {
    private final float MIN_SCALE = 0.8F;
    private float mTranslationX = DensityUtil.dp2px(30);

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            page.setScaleY(MIN_SCALE);
            page.setTranslationX(-mTranslationX);
        } else if (position <= 1) {
            //
            float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
            page.setScaleY(scale);
            page.setTranslationX((mTranslationX - page.getWidth()) * position);
        } else {
            page.setScaleY(MIN_SCALE);
            page.setTranslationX(mTranslationX);
        }

        float alpha = 0.0f;
        if (0.0f <= position && position <= 1.0f) {
            alpha = 1.0f - position;
        } else if (-1.0f <= position && position < 0.0f) {
            alpha = position + 1.0f;
        }

        RelativeLayout selectView = (RelativeLayout) page;
        selectView.getChildAt(1).setAlpha(alpha + 0.5f);
    }

}
