package com.jb.jb_library.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/18 16:10
 * @描述： ${TODO}
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    public List<T> mDatas;

    public BaseAdapter(List<T> datas) {
        mDatas = datas;
    }


    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if(mDatas != null){
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
