package com.jb.jb_library.holder;

import android.view.View;

/**
 * @创建者 zhangbo
 * @创建时间 2016/5/3 15:36
 * @描述 ${TODO} 抽取一个基类的holder
 * @更新描述 ${TODO}
 */
public abstract class BaseHolder<HOLDERTYPE> {

    public View mHolderView;

    public BaseHolder() {
        mHolderView = initHolderView();
        mHolderView.setTag(this);
    }

    /**
     * 初始化HolderView,留给子类实现
     *
     * @return
     */
    public abstract View initHolderView() ;

    /**
     * 用于设置数据和刷新UI，供子类实现
     * @param data
     */
    public abstract void setDataAndRefreshUI(HOLDERTYPE data,int position);

}
