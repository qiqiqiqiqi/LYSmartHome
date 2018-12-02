package com.jb.jb_library.adapter;

import com.jb.jb_library.holder.BaseHolder;
import com.jb.jb_library.holder.MonthHolder;

import java.util.List;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/18 15:46
 * @描述： ${TODO}
 */

public class MonthAdapter<MonthBean> extends com.jb.jb_library.adapter.SuperBaseAdapter<MonthBean> {
    public MonthAdapter(List datas) {
        super(datas);
    }

    @Override
    protected BaseHolder getSpecialHolder() {
        return new MonthHolder();
    }
}
