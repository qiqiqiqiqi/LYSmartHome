package com.jb.jb_library.crop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.jb.jb_library.util.BitmapUtil;
import com.jb.jb_library.util.FileUtil;
import com.jb.jb_library.R;
import com.jb.jb_library.base.JBBaseActivity;
import com.jb.jb_library.constant.Constant;
import com.jb.jb_library.dialog.RenameDialog;
import com.jb.jb_library.helper.TitleBarHelper;
import com.jb.jb_library.view.CommonClipImageView;

import java.io.File;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/10/19 8:48
 * @描述： ${TODO} 图像裁剪activity
 */
public class ClipViewActivity extends JBBaseActivity implements View.OnClickListener {

    private CommonClipImageView mClipView;
    String imgUrl;
    Bitmap bitmap;
    boolean flag;
    int size = 800;
    private Bitmap mBitmap = null;
    private Button mCancleCut;
    private Button mEnsureCut;
    private boolean mIsHeaderPhoto;

    @Override
    protected void init() {
        getPreData();
        setClickEvent();
        initData();
    }

    private void getPreData() {
        Intent intent = getIntent();
        imgUrl = getIntent().getStringExtra("value");
        flag = getIntent().getBooleanExtra("flag", false);
        mIsHeaderPhoto = intent.getBooleanExtra("header_photo", true);
        File file = new File(imgUrl);
        String fileName = file.getName();
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_clip_photo;
    }

    @Override
    protected void initTitle() {
        new TitleBarHelper(this).setMiddleTitleText("剪切").setLeftImageRes(R.mipmap.back)
                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
    }


    @Override
    protected void findView(View contentView) {
        // TODO Auto-generated method stub
        mClipView = (CommonClipImageView) contentView.findViewById(R.id.src_pic);
        mCancleCut = (Button) contentView.findViewById(R.id.cancle_cut);
        mEnsureCut = (Button) contentView.findViewById(R.id.ensure_cut);
    }

    private void initData() {
        if (!TextUtils.isEmpty(imgUrl)) {
            bitmap = BitmapUtil.decodeFile(imgUrl, size);
            mClipView.setImageBitmap(bitmap);
        }
    }


    private void saveData(boolean isSave) {
        if (isSave) {
            mBitmap = mClipView.clip();
        } else {
            mBitmap = bitmap;
        }

        if (!mIsHeaderPhoto) {
            RenameDialog renameDialog = new RenameDialog(mContext);
            renameDialog.setBtnListener(new RenameDialog.BtnListener() {
                @Override
                public void cancleListener() {
                    imgUrl = FileUtil.getAbsRootDir(mContext)
                            + File.separator + System.currentTimeMillis() + ".jpg";
                    saveAndJumpOpe();
                }

                @Override
                public void ensureListener(String name) {
                    imgUrl = FileUtil.getAbsRootDir(mContext)
                            + File.separator + name + ".jpg";
                    saveAndJumpOpe();
                }
            });
            renameDialog.show();
        } else {
            imgUrl = FileUtil.getAbsRootDir(mContext)
                    + File.separator + System.currentTimeMillis() + ".jpg";
            saveAndJumpOpe();
        }
    }

    private void saveAndJumpOpe() {
        BitmapUtil.saveBitMap(this, imgUrl, mBitmap);
        if (mBitmap != null) {
            try {
                mBitmap.recycle();
                bitmap.recycle();
            } catch (Exception e) {
            }
        }
        Intent intent = new Intent();
        intent.putExtra("value", imgUrl);
        setResult(-1, intent);
        finish();
    }


    private void setClickEvent() {
        mCancleCut.setOnClickListener(this);
        mEnsureCut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cancle_cut) {
            saveData(false);
        } else if (view.getId() == R.id.ensure_cut) {
            saveData(true);
        }
    }
}
