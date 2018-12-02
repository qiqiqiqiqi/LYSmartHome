package com.bl.lysmarthome.base;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.at.smarthome.siplib.core.AtSipKitEnv;
import com.at.smarthome.siplib.server.SocketServer;
import com.bl.lysmarthome.LocalManageUtil;
import com.jb.jb_library.base.JBBaseApplication;
import com.jb.jb_library.constant.Constant;
import com.jb.jb_library.helper.FrescoHelper;
import com.jb.jb_library.util.FileUtil;

import java.io.File;

public class BaseApplication extends JBBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        LocalManageUtil.setApplicationLanguage(this);

        Constant.OPE_PICTURE_CODE.ROOT_DIR = FileUtil.getAbsRootDir(this) + File.separator;

        new Thread(new Runnable() {
            @Override
            public void run() {
                FrescoHelper.init(getApplicationContext(), 512);
            }
        }).start();
        initCrashException();

        AtSipKitEnv.setAPPIDandKEY(this,"1000000007","15ed30e4b3d1611d");
        //设置设备的账号
        AtSipKitEnv.setDeviceAccount(this, "zg_gdsznsq_yitezhineng_ykbxz_s8a8b8u8f8r8n1_g","gateway");
        AtSipKitEnv.setSip(this,"15218706074");
        AtSipKitEnv.setRole(this,"phone");
/*
        //设置对讲界面，或自定义
        AtSipKitEnv.setSipClass(talkscreen.class);
        //启动对讲服务
        Intent intents = new Intent(getApplicationContext(), SipService.class);
        startService(intents);*/
//        启动智能家居服务
        startService(new Intent(this,SocketServer.class));
    }

    private void initCrashException() {
        //当程序发生Uncaught异常的时候,由该类来接管程序,一定要在这里初始化
//        CrashHandler.getInstance().init(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LocalManageUtil.onConfigurationChanged(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        //保存系统选择语言
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(LocalManageUtil.setLocal(base));
        MultiDex.install(base);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }


}
