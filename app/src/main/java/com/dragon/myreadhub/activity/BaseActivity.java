package com.dragon.myreadhub.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import com.dragon.myreadhub.Interfaces.PermissionListener;
import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

/**
 * 应用程序中所有Activity的基类。
 *
 * @author：gonghe
 * @time 2019/4/9
 */
public abstract class BaseCompatActivity extends AppCompatActivity
{
    /**
     * 判断当前Activity是否在前台。
     */
    protected boolean isActive = false;

    /**
     * 当前Activity的实例。
     */
    protected Activity activity = null;

    /**
     * Activity中显示加载等待的控件。
     */
    protected ProgressBar loading = null;

    /**
     * Activity中由于服务器异常导致加载失败显示的布局。
     */
    private View loadErrorView = null;

    /**
     * Activity中由于网络异常导致加载失败显示的布局。
     */
    private View badNetworkView= null;

    /**
     * Activity中当界面上没有任何内容时展示的布局。
     */
    private View noContentView= null;

    private WeakReference<Activity> weakRefActivity= null;

    Toolbar toolbar= null;

    private ProgressDialog progressDialog= null;

    private PermissionListener mListener= null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activity = this;
        weakRefActivity =new WeakReference<Activity>(this);
//        ActivityCollector.add(weakRefActivity);
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }


}
