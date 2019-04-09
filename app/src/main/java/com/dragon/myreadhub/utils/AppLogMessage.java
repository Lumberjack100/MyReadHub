package com.dragon.myreadhub.utils;

import android.util.Log;
import com.dragon.myreadhub.MyReadHub;

/**
 * 主要功能： 系统日志输出工具类
 *
 * @author：gonghe
 * @time 2019/4/9
 */
public class AppLogMessage
{
    private static final int VERBOSE = 1;

    private static final int DEBUG = 2;

    private static final int INFO = 3;

    private static final int WARN = 4;

    private static final int ERROR = 5;

    private static final int NOTHING = 6;

    private static final int level = MyReadHub.isDebug ? VERBOSE : WARN;


    public static void logVerbose(String tag, String msg)
    {
        if (level <= VERBOSE)
        {
            Log.v(tag, msg != null ? msg : "");
        }
    }


    public static void logDEBUG(String tag, String msg)
    {
        if (level <= DEBUG)
        {
            Log.d(tag, msg != null ? msg : "");
        }
    }


    public static void logINFO(String tag, String msg)
    {
        if (level <= INFO)
        {
            Log.i(tag, msg != null ? msg : "");
        }
    }

    public static void logWARN(String tag, String msg, Throwable throwable)
    {
        if (level <= WARN)
        {
            if (throwable == null)
            {
                Log.w(tag, msg != null ? msg : "");

            }
            else
            {
                Log.w(tag, msg != null ? msg : "", throwable);

            }
        }
    }

    public static void logERROR(String tag, String msg, Throwable throwable)
    {
        if (level <= ERROR)
        {
            Log.e(tag, msg != null ? msg : "", throwable);
        }
    }

}
