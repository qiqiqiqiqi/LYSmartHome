package com.jb.jb_library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jb.jb_library.util.ScreenSizeUtil;
import com.jb.jb_library.R;
import com.jb.jb_library.util.StringUtil;
import com.jb.jb_library.util.ToastUtil;

/**
 * @创建者： zhangbo
 * @创建时间： 2017/3/28 15:10
 * @描述： ${TODO}
 */

public class RenameDialog extends Dialog implements View.OnClickListener {

    private EditText mRenameEt;
    private TextView mCancleTv;
    private TextView mEnsureTv;
    private Context  mContext;

    public RenameDialog(Context context) {
        this(context, R.style.NormalDialogStyle);
        this.mContext = context;
    }

    public RenameDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rename);
        //拿到窗口属性
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        //设置窗口对齐方式
        attributes.gravity = Gravity.CENTER;
        //设置窗口尺寸
        // attributes.width = ResUtil.getContext().getResources().getDisplayMetrics().widthPixels;
        attributes.width = ScreenSizeUtil.getInstance(mContext).getScreenWidth() * 2 / 3;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口属性
        window.setAttributes(attributes);
        setCancelable(false);
        initView();
        initListener();
    }

    private void initListener() {
        mCancleTv.setOnClickListener(this);
        mEnsureTv.setOnClickListener(this);
    }

    private void initView() {
        mRenameEt = (EditText) findViewById(R.id.rename_et);
        mCancleTv = (TextView) findViewById(R.id.cancle_tv);
        mEnsureTv = (TextView) findViewById(R.id.ensure_tv);
    }

    private BtnListener mListener;

    public void setBtnListener(BtnListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.cancle_tv) {
            mListener.cancleListener();
            dismiss();

        } else if (i == R.id.ensure_tv) {
            String name = mRenameEt.getText().toString().trim();
            if (StringUtil.isEmpty(name)) {
                ToastUtil.showToast("请输入文件名");
                return;
            }
            mListener.ensureListener(name);
            dismiss();

        }
    }

    public interface BtnListener {
        void cancleListener();

        void ensureListener(String name);
    }
}
