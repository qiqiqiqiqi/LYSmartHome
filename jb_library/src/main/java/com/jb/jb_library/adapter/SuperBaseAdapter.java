package com.jb.jb_library.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.jb.jb_library.holder.BaseHolder;

import java.util.List;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/18 16:11
 * @描述： ${TODO}
 */

public abstract class SuperBaseAdapter<T> extends BaseAdapter {
    public SuperBaseAdapter(List datas) {
        super(datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder baseHolder = null;
        if(convertView == null){
            baseHolder = getSpecialHolder();
        }else{
            baseHolder = (BaseHolder) convertView.getTag();
        }
        baseHolder.setDataAndRefreshUI(mDatas.get(position),position);

        return baseHolder.mHolderView;
    }

    protected abstract BaseHolder getSpecialHolder();
}
