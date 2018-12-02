package com.jb.jb_library.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jb.jb_library.helper.FrescoHelper;
import com.jb.jb_library.intf.ViewPagerOnItemClickListener;
import com.jb.jb_library.R;

import java.util.List;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/9/13 17:25
 * @描述： ${TODO} Banner适配器
 */

public class BannerAdapter extends PagerAdapter {
    private List<String>                 mList;
    private ViewPagerOnItemClickListener mViewPagerOnItemClickListener;
    private int[]                        mImages;
    private Context                      mContext;


    public BannerAdapter(Context context, List<String> list) {
        this.mList = list;
        this.mContext = context;
    }

    public BannerAdapter(Context context, int[] images) {
        this.mImages = images;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        } else if (mImages != null) {
            return mImages.length;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = View.inflate(mContext, R.layout.banner_view_layout, null);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.banner_sdv);
        if (mList != null) {
            FrescoHelper.loadUrl(mList.get(position), simpleDraweeView);
        } else if (mImages != null) {
            simpleDraweeView.setImageURI(Uri.parse("res:///" + mImages[position]));
        }

        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = simpleDraweeView.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(simpleDraweeView);
        }

        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPagerOnItemClickListener != null) {
                    mViewPagerOnItemClickListener.onItemClick(position);
                }
            }
        });

        container.addView(simpleDraweeView);
        return simpleDraweeView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public void setViewPagerOnItemClickListener(ViewPagerOnItemClickListener mViewPagerOnItemClickListener) {

        this.mViewPagerOnItemClickListener = mViewPagerOnItemClickListener;
    }
}
