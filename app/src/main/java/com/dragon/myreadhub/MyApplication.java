package com.dragon.myreadhub;

import android.app.Application;
import com.dragon.myreadhub.utils.CrashUtils;

public class MyApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();

        //初始化全局配置类
        MyReadHub.initialize(this);

        // crash handler
        CrashUtils.init();
    }
}
