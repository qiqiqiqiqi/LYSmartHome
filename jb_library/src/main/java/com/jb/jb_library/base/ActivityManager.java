package com.jb.jb_library.base;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/15 14:44
 * @描述： ${TODO} 处理activity的安全退出
 */

public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static   ActivityManager instance;

    private ActivityManager() {
    }

    public static ActivityManager getAppManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack();
        }
        activityStack.add(activity);
    }

    /**
     * 获得当前的activity
     * @return
     */
    public Activity currentActivity() {
        Activity activity = (Activity) activityStack.lastElement();
        return activity;
    }

    public void finishActivity() {
        Activity activity = (Activity) activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void reLongin(Class<?> cls) {
        int i = 0;
        for (int size = activityStack.size(); i < size; i++) {
            if ((activityStack.get(i) != null) && (!((Activity) activityStack.get(i)).getClass().equals(cls))) {
                ((Activity) activityStack.get(i)).finish();
            }
        }
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
