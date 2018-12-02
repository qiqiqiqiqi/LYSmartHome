package com.jb.jb_library.holder;

import android.view.View;
import android.widget.TextView;

import com.jb.jb_library.bean.MonthBean;
import com.jb.jb_library.util.LayoutUtil;
import com.jb.jb_library.util.StringUtil;
import com.jb.jb_library.util.UIUtil;
import com.jb.jb_library.R;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/18 16:15
 * @描述： ${TODO}
 */

public class MonthHolder extends BaseHolder<MonthBean> {

    private TextView mDayTV;

    @Override
    public View initHolderView() {
        View dateView = LayoutUtil.inflate(R.layout.item_date_picker);
        mDayTV = (TextView) dateView.findViewById(R.id.day_tv);
        return dateView;
    }

    @Override
    public void setDataAndRefreshUI(MonthBean data,int position) {
        if (StringUtil.isEmpty(data.getDayOfMonth())) {
            int day = data.getDay();
            if (day == 0) {
                mDayTV.setText("");
            }else{
                mDayTV.setText("" + data.getDay());
            }
        } else
            mDayTV.setText("" + data.getDayOfMonth());
        if (data.isChecked()) {
            mDayTV.setBackgroundResource(R.drawable.shape_circle_yellow);
        } else {
            if (data.isCheckCurrTime()) {
                mDayTV.setBackgroundResource(R.drawable.shape_circle_red);
            } else {
                mDayTV.setBackgroundColor(UIUtil.getColor(R.color.white));
            }
        }
    }
}
