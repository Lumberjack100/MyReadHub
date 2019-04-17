package com.dragon.myreadhub.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.dragon.myreadhub.MyReadHub;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 应用程序全局的通用工具类，功能比较单一，经常被复用的功能，应该封装到此工具类当中，从而给全局代码提供方面的操作。
 *
 * @author：gonghe
 * @time 2019/4/17
 */
public class GlobalUtil
{
    private static final String TAG = "GlobalUtil";

    private Toast toast = null;

    /**
     * 获取当前应用程序的包名。
     *
     * @return 当前应用程序的包名。
     */
    public static String getAppPackage()
    {
        return MyReadHub.getContext().getPackageName();
    }

    /**
     * 获取当前应用程序的名称。
     *
     * @return 当前应用程序的名称。
     */
    public static String getAppName()
    {
        return MyReadHub.getContext().getResources().getString(MyReadHub.getContext().getApplicationInfo().labelRes);
    }

    /**
     * 获取当前应用程序的版本名。
     *
     * @return 当前应用程序的版本名。
     */
    public static String getAppVersionName()
    {
        String verName = "";
        try
        {
            verName = MyReadHub.getContext().getPackageManager().getPackageInfo(getAppPackage(), 0).versionName;
        }
        catch (NameNotFoundException e)
        {
            AppLogUtil.logERROR("AppApplicationMgr-->>getVerName()", "获取本地Apk版本名称失败！", e);
            e.printStackTrace();
        }
        return verName;
    }


    /**
     * 获取当前应用程序的版本号。
     *
     * @return 当前应用程序的版本号。
     */
    public static int getAppVersionCode()
    {
        int verCode = -1;
        try
        {
            verCode = MyReadHub.getContext().getPackageManager().getPackageInfo(getAppPackage(), 0).versionCode;
        }
        catch (NameNotFoundException e)
        {
            AppLogUtil.logERROR("AppApplicationMgr-->>getVerCode()", "获取本地Apk版本号失败！", e);
            e.printStackTrace();
        }
        return verCode;
    }


    /**
     * 获取当前时间的字符串，格式为yyyyMMddHHmmss。
     *
     * @return 当前时间的字符串。
     */
    public static String getCurrentDateString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        return sdf.format(new Date());
    }

    /**
     * 获取资源文件中定义的字符串。
     *
     * @param resId 字符串资源id
     * @return 字符串资源id对应的字符串内容。
     */
    public static String getString(int resId)
    {
        return MyReadHub.getContext().getResources().getString(resId);
    }

    /**
     * 获取指定资源名的资源id。
     *
     * @param name 资源名
     * @param type 资源类型
     * @return 指定资源名的资源id。
     */
    public static int getResourceId(String name, String type)
    {
        return MyReadHub.getContext().getResources().getIdentifier(name, type, getAppPackage());
    }

    /**
     * 获取AndroidManifest.xml文件中，<application>标签下的meta-data值。
     *
     * @param key <application>标签下的meta-data健
     */
    public static String getApplicationMetaData(String key)
    {
        String value = "";

        try
        {
            ApplicationInfo applicationInfo = MyReadHub.getContext().getPackageManager().getApplicationInfo(MyReadHub.getContext().getPackageName(), PackageManager.GET_META_DATA);
            if (null != applicationInfo)
            {
                Bundle metaData = applicationInfo.metaData;
                if (null != metaData)
                {
                    value = metaData.getString(key);
                    if (null == value || value.length() == 0)
                    {
                        value = "";
                    }
                }
            }
        }
        catch (NameNotFoundException ex)
        {
            ex.printStackTrace();
        }

        return value;
    }

    /**
     * 应用是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isInstalled(String packageName)
    {
        boolean installed = false;
        if (TextUtils.isEmpty(packageName))
        {
            return false;
        }
        List<ApplicationInfo> installedApplications = MyReadHub.getContext().getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo in : installedApplications)
        {
            if (packageName.equals(in.packageName))
            {
                installed = true;
                break;
            }
            else
            {
                installed = false;
            }
        }
        return installed;
    }
}
