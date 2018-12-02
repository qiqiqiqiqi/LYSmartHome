package com.jb.jb_library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import com.jb.jb_library.R;
import com.jb.jb_library.intf.PhotoCallBack;


/**
 * Created by Administrator on 2017/12/21.
 */

public class FilePickDialog extends Dialog implements View.OnClickListener {
    private int firstContentId, secondContentId, thirdContentId;
    private Button first;
    private Button second;
    private Button cancel;
    private Context context;
    public PhotoCallBack callBack;
    private Button three;
    private LinearLayout mVideoLl;
    private Button four;

    public FilePickDialog(Context context, int firstContentId, int secondContentId, int thirdContentId) {
        this(context, R.style.BottomDialogStyle);
        this.context = context;
        this.firstContentId = firstContentId;
        this.secondContentId = secondContentId;
        this.thirdContentId = thirdContentId;
    }

    public FilePickDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_selecter);

        //拿到窗口属性
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        //设置窗口对齐方式
        attributes.gravity = Gravity.BOTTOM;
        //设置窗口尺寸
        // attributes.width = ResUtil.getContext().getResources().getDisplayMetrics().widthPixels;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口属性
        window.setAttributes(attributes);
        //设置窗口的动画
        window.getDecorView().setPadding(0, 0, 0, 0); //设置程序显示的区域

        initView();
        initData();
    }

    private void initView() {
        first = (Button) findViewById(R.id.first);
        second = (Button) findViewById(R.id.second);
        cancel = (Button) findViewById(R.id.cancel);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        mVideoLl = (LinearLayout) findViewById(R.id.video_ll);
    }


    private void initData() {
        first.setText(context.getResources().getString(firstContentId));
        second.setText(context.getResources().getString(secondContentId));
        cancel.setText(context.getResources().getString(thirdContentId));
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        cancel.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
    }

    public void setCallBack(PhotoCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.first) {
            callBack.photoValue(1);

        } else if (i == R.id.second) {
            callBack.photoValue(2);

        } else if (i == R.id.cancel) {
            callBack.photoValue(3);

        } else if (i == R.id.three) {
            callBack.photoValue(4);

        } else if (i == R.id.four) {
            callBack.photoValue(5);

        }
    }

    public void setVideoView() {
        mVideoLl.setVisibility(View.VISIBLE);
    }

    public void photoIsShow(int isShow) {
        first.setVisibility(isShow);
    }
}
