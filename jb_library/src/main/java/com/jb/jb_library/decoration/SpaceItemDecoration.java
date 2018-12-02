package com.jb.jb_library.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jb.jb_library.util.LogUtil;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/9/19 9:26
 * @描述： ${TODO} RecycleView的条目间隔
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        int itemPosition = parent.getChildAdapterPosition(view);
        LogUtil.e("itemPosition::"+itemPosition);
        if(itemPosition != 0){
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
        }
    }

}
