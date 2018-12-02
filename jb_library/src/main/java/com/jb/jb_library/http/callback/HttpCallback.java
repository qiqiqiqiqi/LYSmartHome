package com.jb.jb_library.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import com.jb.jb_library.util.LogUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/16 10:41
 * @描述： ${TODO}
 */

public abstract class HttpCallback implements Callback {
    /**
     * 请求成功标识
     */
    private static final int     WHAT_SUCCESS = 11;
    /**
     * 请求失败标识
     */
    private static final int     WHAT_FAILURE = 22;
    /**
     * 消息处理器
     */
    private              Handler handler      = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                if (msg.what == WHAT_SUCCESS) {
                    if (msg.obj != null) {
                        onSuccess(msg.arg1, msg.obj.toString());
                    }
                } else if (msg.what == WHAT_FAILURE) {
                    if (msg.obj != null) {
                        onFailure(msg.arg1, msg.obj.toString());
                    }
                }
            }
        }
    };

    /**
     * 当请求成功时会调用此方法
     *
     * @param statusCode HTTP响应状态码
     * @param response   返回的字符串数据
     */
    public abstract void onSuccess(int statusCode, @NonNull String response);

    /**
     * 当请求失败时会调用此方法
     *
     * @param statusCode HTTP响应状态码
     * @param message    异常或错误消息
     */
    public abstract void onFailure(int statusCode, @NonNull String message);


    @Override
    public void onFailure(Call call, IOException e) {
        LogUtil.e("IOException:" + e.toString());
        Message msg = Message.obtain();
        msg.obj = e.toString();
        msg.arg1 = 1;
        msg.what = WHAT_FAILURE;
        handler.sendMessage(msg);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        // 这个方法不是在主线程中调用，不能在回调中更新UI
        final String responseString = response.body().string();
        final int code = response.code(); //code >= 200 && code < 300即为成功,否则为异常
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            onSuccess(code, responseString);
        } else {
            Message msg = Message.obtain();
            msg.obj = responseString;
            msg.arg1 = code;
            msg.what = WHAT_SUCCESS;
            handler.sendMessage(msg);
        }
    }
}
