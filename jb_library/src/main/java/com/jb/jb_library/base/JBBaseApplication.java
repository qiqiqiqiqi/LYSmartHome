package com.jb.jb_library.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.jb.jb_library.http.model.OkHttpModel;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/15 13:45
 * @描述： ${TODO}
 */

public class JBBaseApplication extends Application{
    private static Context mContext;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        // 1.上下文
        mContext = getApplicationContext();
        // 2.主线程handler
        mHandler = new Handler();
        OkHttpModel.getInstance();
    }

    /**得到上下文*/
    public static Context getContext() {
        return mContext;
    }


    /**得到主线程的handler*/
    public static Handler getHandler() {
        return mHandler;
    }
}
