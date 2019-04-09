package com.dragon.myreadhub.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 应用中所有Activity的管理器，可用于一键杀死所有Activity。
 *
 * @author：gonghe
 * @time 2019/4/9
 */
public class ActivityCollector
{
    private static final String TAG = "ActivityCollector";

    private static ArrayList<WeakReference<Activity>> activityList = new ArrayList<>();

    public static int size()
    {
        return activityList.size();
    }

    public static void add(WeakReference<Activity> weakRefActivity)
    {
        activityList.add(weakRefActivity);
    }

    public static void remove(WeakReference<Activity> weakRefActivity)
    {
        boolean result = activityList.remove(weakRefActivity);
//        LogKt.logDebug("ActivityCollector", "remove activity reference " + result);
    }

    public static void finishAll()
    {
        if (!activityList.isEmpty())
        {
            for (WeakReference<Activity> weakRefActivity : activityList)
            {
                Activity activity = weakRefActivity != null ? weakRefActivity.get() : null;
                if (activity != null && !activity.isFinishing())
                {
                    activity.finish();
                }
            }

            activityList.clear();
        }
    }
}
