package com.jb.jb_library.intf;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/17 14:07
 * @描述： ${TODO}
 */

public interface ResponseResult {
    void resSuccess(Object object); //成功

    void resFailure(int code, String message); //失败
}
