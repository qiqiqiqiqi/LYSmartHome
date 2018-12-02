package com.bl.lysmarthome.ui.activity;

import android.view.View;

import com.bl.lysmarthome.R;
import com.jb.jb_library.base.JBBaseActivity;
import com.jb.jb_library.helper.TitleBarHelper;


/**
 * Created by Administrator on 2018/11/28.
 */

public class DoorLockControlActivity extends JBBaseActivity implements View.OnClickListener {

    @Override
    protected void init() {
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_door_control;
    }

    @Override
    protected void initTitle() {
        new TitleBarHelper(mActivity).setMiddleTitleText("DOOR CONTROL").setLeftImageRes(R.mipmap.back)
                .setLeftClickListener(this);
    }

    @Override
    protected void findView(View contentView) {
    }

    @Override
    public void onClick(View v) {

    }
}
