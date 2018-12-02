package com.jb.jb_library.http.model;

import android.support.annotation.NonNull;

import com.jb.jb_library.http.callback.HttpResponseCallback;
import com.jb.jb_library.util.UIUtil;
import com.jb.jb_library.R;
import com.jb.jb_library.util.NetUtil;


/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/16 16:22
 * @描述： ${TODO}
 */

public abstract class HttpModel<RESPONSE> implements IHttpModel<RESPONSE> {
    @Override
    public boolean checkCurrentNetwork(@NonNull String url, @NonNull HttpResponseCallback<RESPONSE> httpResponseCallback) {
        if(!NetUtil.isNetworkConnected(false)){
            httpResponseCallback.onFailure( -1, UIUtil.getString(R.string.text_network_require), null);
            return false;
        }
        return true;
    }
    /**
     * HTTP请求谓词枚举
     */
    public enum HttpMethod {
        GET, POST, PUT, GET_DATA_URL, DELETE
    }

}
