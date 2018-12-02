package com.jb.jb_library.intf;

/**
 * @创建者： zhangbo
 * @创建时间： 2017/3/17 15:59
 * @描述： ${TODO}
 */

public interface UploadListener {
    void uploadLength(long current, long total, boolean done);
}
