package com.jb.jb_library.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jb.jb_library.base.JBBaseApplication;


/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/16 9:35
 * @描述： ${TODO}
 */

public final class KeyboardUtil {
    /**
     * 显示软键盘
     *
     * @param view 控件
     */
    public static void showSoftKeyboard(@NonNull View view) {
        showSoftKeyboard(JBBaseApplication.getContext(), view);
    }

    /**
     * 显示软键盘
     *
     * @param context 应用程序上下文
     * @param view    控件
     */
    public static void showSoftKeyboard(@NonNull Context context, @NonNull View view) {
        if (context != null && view != null) {
            try {
                final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param view 控件
     */
    public static void hideSoftKeyboard(View view) {
        hideSoftKeyboard(JBBaseApplication.getContext(), view);
    }

    /**
     * 隐藏软键盘
     *
     * @param context 当前Activity
     * @param view    控件
     */
    public static void hideSoftKeyboard(@NonNull Context context, View view) {
        if (context != null && view != null) {
            try {
                final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
