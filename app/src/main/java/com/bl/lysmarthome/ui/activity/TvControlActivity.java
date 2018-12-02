package com.bl.lysmarthome.ui.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bl.lysmarthome.R;
import com.jb.jb_library.banner.BannerView;
import com.jb.jb_library.banner.holder.HolderCreator;
import com.jb.jb_library.banner.holder.ViewHolder;
import com.jb.jb_library.base.JBBaseActivity;
import com.jb.jb_library.helper.TitleBarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/28.
 */

public class TvControlActivity extends JBBaseActivity implements View.OnClickListener {
    public int[] BANNER = new int[]{R.mipmap.img_tv, R.mipmap.img_tv, R.mipmap.img_tv, R.mipmap.img_tv, R.mipmap.img_tv};
    private BannerView mBannerView;

    @Override
    protected void init() {
        List<Integer> bannerList = new ArrayList<>();
        for (int i = 0; i < BANNER.length; i++) {
            bannerList.add(BANNER[i]);
        }

        mBannerView.setPages(bannerList, new HolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });


        mBannerView.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_tv_control;
    }

    @Override
    protected void initTitle() {
        new TitleBarHelper(mActivity).setMiddleTitleText("TV CONTROL").setLeftImageRes(R.mipmap.back)
                .setLeftClickListener(this);
    }

    @Override
    protected void findView(View contentView) {
        mBannerView = (BannerView) findViewById(R.id.banner_view);
    }

    @Override
    public void onClick(View v) {

    }

    public class BannerViewHolder implements ViewHolder<Integer> {
        private ImageView mImageView;
        private RelativeLayout mBannerContainerRl;
        private TextView mDesTv;

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item_two, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            mDesTv = (TextView) view.findViewById(R.id.tv_des);
            mBannerContainerRl = (RelativeLayout) view.findViewById(R.id.rl_banner_container);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data, View view) {
            // 数据绑定
            mImageView.setImageResource(data);
            mBannerContainerRl.setAlpha(0.5f);
        }
    }

}
