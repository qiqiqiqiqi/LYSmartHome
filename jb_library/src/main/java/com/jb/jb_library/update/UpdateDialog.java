package com.jb.jb_library.update;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jb.jb_library.R;
import com.jb.jb_library.util.LogUtil;
import com.jb.jb_library.util.ScreenSizeUtil;
import com.jb.jb_library.util.ToastUtil;
import com.jb.jb_library.util.UIUtil;

import java.io.File;
import java.io.FileNotFoundException;

public class UpdateDialog extends Dialog implements View.OnClickListener {

    private TextView mFunnctionRemarksTv;
    private TextView mVersionCodeTv;
    private Context mContext;
    private ProgressBar mUpdatePb;
    private TextView mUpdateTv;
    private TextView mCancleTv;
    private DownloadTask mDownloadTask;
    private String mVersionCode;
    private TextView mProgressRateTv;
    private String mUpdateUrl;

    public UpdateDialog(@NonNull Context context) {
        this(context, R.style.UpdateDialogStyle);
        mContext = context;
    }

    public UpdateDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_layout);

        //拿到窗口属性
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        //设置窗口对齐方式
        attributes.gravity = Gravity.CENTER;
        //设置窗口尺寸
        // attributes.width = ResUtil.getContext().getResources().getDisplayMetrics().widthPixels;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.width = ScreenSizeUtil.getInstance(UIUtil.getContext()).getScreenWidth() * 3 / 4;
        //设置窗口属性
        window.setAttributes(attributes);
        //设置窗口的动画
        window.getDecorView().setPadding(0, 0, 0, 0); //设置程序显示的区域
        setCanceledOnTouchOutside(false);
        mUpdatePb = (ProgressBar) findViewById(R.id.update_pb);
        mFunnctionRemarksTv = (TextView) findViewById(R.id.funnction_remarks_tv);
        mVersionCodeTv = (TextView) findViewById(R.id.version_code_tv);
        mProgressRateTv = (TextView) findViewById(R.id.progress_rate_tv);
        mUpdateTv = (TextView) findViewById(R.id.update_tv);
        mCancleTv = (TextView) findViewById(R.id.cancle_tv);
        mUpdateTv.setOnClickListener(this);
        mCancleTv.setOnClickListener(this);
    }

    public void setData(String funcRemarks, String versionName,String versionCode, String url) {
        mVersionCode = versionCode;
        mUpdateUrl = url;
        mFunnctionRemarksTv.setText(funcRemarks);
        mVersionCodeTv.setText(versionName);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.update_tv) {
            mUpdateTv.setVisibility(View.GONE);
            mCancleTv.setVisibility(View.GONE);
            mUpdatePb.setVisibility(View.VISIBLE);
            mProgressRateTv.setVisibility(View.VISIBLE);
            if (mDownloadTask == null) {
                mDownloadTask = new DownloadTask(new DownloadListener() {
                    @Override
                    public void onProgress(int progress) {
                        mProgressRateTv.setText(progress + " %");
                        mUpdatePb.setProgress(progress);
                    }

                    @Override
                    public void onSuccess() {
                        LogUtil.e("下载成功");
                        mDownloadTask = null;
                        dismiss();
                        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                        File file = new File(directory + "/" + mVersionCode + "_" + "TKYRiskInvestigate.apk");
                        installApk(mContext, file);
                    }

                    @Override
                    public void onFailed() {
                        LogUtil.e("下载失败");
                        mDownloadTask = null;
                        ToastUtil.showToast("更新失败");
                    }

                    @Override
                    public void onPaused() {
                        LogUtil.e("下载暂停");
                        mDownloadTask = null;
                    }

                    @Override
                    public void onCanceled() {
                        LogUtil.e("下载取消");
                        mDownloadTask = null;
                    }
                }, mVersionCode);
            }

            //启动下载任务
            mDownloadTask.execute(mUpdateUrl);

        } else if (i == R.id.cancle_tv) {
            dismiss();

        }
    }

    public void cancle() {
        if (mDownloadTask != null)
            mDownloadTask.cancelDownload();
    }

    public void pause() {
        if (mDownloadTask != null)
            mDownloadTask.pauseDownload();

        mDownloadTask = null;
    }


    /**
     * 安装apk
     *
     * @param apkFile
     */
    public void installApk(Context context, File apkFile) {
        try {
            context.openFileOutput(apkFile.getName(), context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(FileProvider.getUriForFile(context, "com.jb.tkyriskinvestigate", apkFile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);

    }


}
