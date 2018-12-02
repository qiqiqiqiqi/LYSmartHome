package com.jb.jb_library.pulltorefresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/10/31 10:09
 * @描述： ${TODO}
 */

public class PullableRecycleView extends RecyclerView implements Pullable{
    public PullableRecycleView(Context context) {
        super(context);
    }

    public PullableRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    boolean canPullDown = true;
    boolean canPullUp = true;

    public void setCanPullDown(boolean canPullDown) {
        this.canPullDown = canPullDown;
    }

    public void setCanPullUp(boolean canPullUp) {
        this.canPullUp = canPullUp;
    }

    @Override
    public boolean canPullDown() {
        if (canPullDown && !canScrollVertically(-1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        if (canPullUp && !canScrollVertically(1))
            return true;
        else
            return false;
    }
}
