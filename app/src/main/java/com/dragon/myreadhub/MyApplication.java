package com.dragon.myreadhub;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.dragon.myreadhub.activity.AboutActivity;
import com.dragon.myreadhub.activity.MainActivity;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

public class MyApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();

        //异常上报和升级
        initBugly();

        //初始化全局配置类
        MyReadHub.initialize(this);
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.initMultiDex();
    }

    /**
     * 添加多dex支持
     */
    private void initMultiDex() {
        MultiDex.install(this);
    }

    /**
     * 初始化Bugly
     */
    private void initBugly() {
        Beta.autoInit = true;//true表示app启动自动初始化升级模块; false不会自动初始化;
        Beta.autoCheckUpgrade = true;//true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade
        // ()方法;
        Beta.enableNotification = false;//如果你不想在通知栏显示下载进度，你可以将这个接口设置为false，默认值为true。
        Beta.autoDownloadOnWifi = false;//如果你想在Wifi网络下自动下载，可以将这个接口设置为true，默认值为false。
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.canShowUpgradeActs.add(AboutActivity.class);
        Beta.canShowApkInfo = false;//设置是否显示弹窗中的apk信息
        Bugly.init(getApplicationContext(), BuildConfig.BUGLY_APPKEY, BuildConfig.DEBUG);
        Bugly.setAppChannel(this, BuildConfig.FLAVOR);
    }
}
