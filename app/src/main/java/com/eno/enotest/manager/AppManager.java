package com.eno.enotest.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.eno.enotest.utils.LogUtils;

import java.util.Stack;

/**
 * Created by Hao on 2019-07-08.
 * Email:itzihao@sina.com
 * Activity 管理类
 */
public class AppManager {


    private static Stack<Activity> sActivityStack;
    private volatile static AppManager sAppManager;

    private AppManager() {
        sActivityStack = new Stack<>();
    }

    public static AppManager getAppManager() {
        if (sAppManager == null) {
            synchronized (AppManager.class) {
                sAppManager = new AppManager();
            }
        }
        return sAppManager;
    }

    public void addActivity(Activity activity) {
        if (sActivityStack != null && !sActivityStack.contains(activity)) {
            sActivityStack.push(activity);
            LogUtils.logE(" 当前添加Activity为：" + activity.getClass().getSimpleName() + "--> Activity size : " + sActivityStack.size());
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null && sActivityStack.size() > 0) {
            sActivityStack.remove(activity);
            LogUtils.logE(" 当前移除Activity为：" + activity.getClass().getSimpleName() + "--> Activity size : " + sActivityStack.size());
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?>... cls) {
        boolean needRemoveCurExisting = false;
        int curExistingItemIdx = 0;
        try {
            if (cls.length > 0) {
                for (final Class<?> cl : cls) {
                    for (int i = 0, j = sActivityStack.size(); i < j; i++) {
                        if (sActivityStack.get(i).getClass().equals(cl)) {
                            needRemoveCurExisting = true;
                            curExistingItemIdx = i;
                            break;
                        }
                    }
                    if (needRemoveCurExisting) {
                        needRemoveCurExisting = false;
                        LogUtils.logE("finishActivity Is Name Is :" + sActivityStack.get(curExistingItemIdx).getClass().getSimpleName());
                        sActivityStack.get(curExistingItemIdx).finish();
                        sActivityStack.remove(curExistingItemIdx);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.logE(" finishActivity Exception :" + e);
        }
    }


    public void finishAllActivity() {
        Activity activity;
        while (!sActivityStack.empty()) {
            activity = sActivityStack.pop();
            if (activity != null) {
                activity.finish();
            }
        }


//        if (null != sActivityStack && sActivityStack.size() > 0) {
//            //创建临时集合对象
//            Stack<Activity> activityTemp = new Stack<>();
//            for (int i = sActivityStack.size() - 1; i >= 0; i--) {
//                Activity activity = sActivityStack.get(i);
//                if (activity != null && !activity.isFinishing()) {
//                    LogUtils.logE("Finish activity is Name :" + activity.getClass().getSimpleName());
//                    //添加到临时集合中
//                    activityTemp.add(activity);
//                    //结束Activity
//                    activity.finish();
//                }
//            }
//            sActivityStack.removeAll(activityTemp);
//            sActivityStack.clear();
//        }
    }


    /**
     * 退出应用程序
     *
     * @param context
     * @param isBackGroud
     */
    @SuppressLint("MissingPermission")
    public void AppExit(Context context, boolean isBackGroud) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            if (activityMgr != null) {
                activityMgr.killBackgroundProcesses(context.getPackageName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!isBackGroud) {
                System.exit(0);
            }
        }
    }

}
