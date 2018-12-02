package com.jb.jb_library.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/10/28 12:00
 * @描述： ${TODO} Activity基本判断的工具类
 */

public class ActivityUtil {
    /**
     * 判断程序是否在前台.
     */
    public static boolean isAppInForeground(Context context) {
        boolean result = false;
        String packageName = context.getPackageName();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task_list = activityManager.getRunningTasks(1);
        if (task_list.size() > 0) {
            if (task_list.get(0).topActivity.getPackageName().trim().equals(packageName)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 判断程序是否在后台运行
     *
     * @param ctx
     * @return
     */
    public static boolean isAppOnBackground(Context ctx) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
        if (appProcesses != null) {
            String package_name = ctx.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(package_name) && appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测该Context是否在栈顶
     *
     * @param activity
     * @return
     */
    public static boolean isTop(Context activity) {
        boolean isTop = false;
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(Integer.MAX_VALUE);
        if (runningTasks != null && runningTasks.size() > 0) {
            ActivityManager.RunningTaskInfo taskInfo = runningTasks.get(0);
            String temp = taskInfo.topActivity.getClassName();
            if (activity.getClass().getName().equals(temp)) {
                return true;
            }
        }
        return isTop;
    }

    /**
     * 判断Activity是否位于前台
     *
     * @param ctx 上下文
     * @param cls 目标类
     * @return
     */
    public static boolean isActivityForeground(Context ctx, Class<?> cls) {
        boolean result = false;
        String clsName = cls.getName();
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task_list = activityManager.getRunningTasks(1);
        if (task_list.size() > 0) {
            String topName = task_list.get(0).topActivity.getClassName();
            if (clsName.equals(topName)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 切换应用到前台
     *
     * @param activity
     */
    public static void moveTaskToFront(Activity activity) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 13) {
            ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
            am.moveTaskToFront(activity.getTaskId(), 0);
        }
    }

    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */
    public static void startActivity(Activity activity, @NonNull Class<?> targetActivity) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            activity.startActivity(new Intent(activity, targetActivity), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {
            activity.startActivity(new Intent(activity, targetActivity));
        }
    }

    /**
     * 跳转到指定的Activity
     *
     * @param data           Activity之间传递数据
     * @param targetActivity 要跳转的目标Activity
     */
    public static void startActivity(Activity activity, @NonNull String name, @NonNull Bundle data, @NonNull Class<?> targetActivity) {
        final Intent intent = new Intent();
        intent.putExtra(name, data);
        intent.setClass(activity, targetActivity);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {
            activity.startActivity(intent);
        }

    }

    /**
     * 跳转到指定的Activity,并返回相应的结果,不需要传递参数
     *
     * @param targetActivity 要跳转的目标Activity
     * @param requestCode    请求码
     */
    public static void startActivityForResult(Activity activity, Class<?> targetActivity, int requestCode) {
        startActivityForResult(activity, targetActivity, null, null, requestCode);
    }

    /**
     * 跳转到指定的Activity,并返回相应的结果,需要传递参数
     *
     * @param targetActivity 要跳转的目标Activity
     * @param extras         Activity之间传递数据
     * @param requestCode    请求码
     */
    public static void startActivityForResult(Activity activity, Class<?> targetActivity, String name, Bundle extras, int requestCode) {
        Intent intent = new Intent(activity, targetActivity);
        if (null != extras && !extras.isEmpty()) {
            intent.putExtra(name, extras);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { //手机版本大于21时
            activity.startActivityForResult(intent, requestCode, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }
}
