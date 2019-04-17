package com.dragon.myreadhub.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.dragon.myreadhub.Interfaces.PermissionListener;
import com.dragon.myreadhub.R;
import com.dragon.myreadhub.event.ForceToLoginEvent;
import com.dragon.myreadhub.event.MessageEvent;
import com.dragon.myreadhub.klog.KLog;
import com.dragon.myreadhub.utils.ActivityCollector;
import com.dragon.myreadhub.utils.AndroidVersion;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 应用程序中所有Activity的基类。
 *
 * @author：gonghe
 * @time 2019/4/9
 */
public abstract class BaseActivity extends AppCompatActivity
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
    private View badNetworkView = null;

    /**
     * Activity中当界面上没有任何内容时展示的布局。
     */
    private View noContentView = null;

    private WeakReference<Activity> weakRefActivity = null;

    private Toolbar toolbar = null;

    private static Handler handler;

    private ProgressDialog progressDialog = null;

    private PermissionListener mListener = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activity = this;
        weakRefActivity = new WeakReference<Activity>(this);
        ActivityCollector.add(weakRefActivity);
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        isActive = true;

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        isActive = false;
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        activity = null;
        ActivityCollector.remove(weakRefActivity);
        EventBus.getDefault().unregister(this);
    }

    protected final void setToolBar()
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected final void transparentStatusBar()
    {
        if (AndroidVersion.hasLollipop())
        {
            Window window = this.getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(1280);
            window.setStatusBarColor(0);
        }
    }


    protected final Handler getHandler()
    {
        if (handler == null)
        {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }

    /**
     * 隐藏软键盘。
     */
    protected void hideSoftKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (isShow)
//        {
//            if (getCurrentFocus() == null)
//            {
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//            }
//            else
//            {
//                imm.showSoftInput(getCurrentFocus(), 0);
//            }
//        }
//        else
//        {
//            if (getCurrentFocus() != null)
//            {
//                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }

        try
        {
            View view = this.getCurrentFocus();
            if (view != null)
            {
                IBinder binder = view.getWindowToken();
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        catch (Exception ex)
        {
            KLog.w("BaseActivity", ex.getMessage(), ex);
        }
    }

    /**
     * 显示软键盘
     */
    protected void showSoftKeyboard(EditText editText)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        try
        {
            if (editText != null)
            {
                editText.requestFocus();
                imm.showSoftInput(editText, 0);
            }


        }
        catch (Exception ex)
        {
            KLog.w("BaseActivity", ex.getMessage(), ex);
        }
    }


    /**
     * 当Activity中的加载内容服务器返回失败，通过此方法显示提示界面给用户。
     *
     * @param tip 界面中的提示信息
     */
    protected final void showLoadErrorView(String tip)
    {
        if (TextUtils.isEmpty(tip))
            return;

        if (loadErrorView != null)
        {
            loadErrorView.setVisibility(View.VISIBLE);
            return;
        }

        ViewStub viewStub = (ViewStub) findViewById(R.id.loadErrorView);
        if (viewStub != null)
        {
            loadErrorView = viewStub.inflate();
            TextView loadErrorText = loadErrorView != null ? (TextView) loadErrorView.findViewById(R.id.loadErrorText) : null;
            if (loadErrorText != null)
            {
                loadErrorText.setText(tip);
            }
        }
    }


    /**
     * 当Activity中的内容因为网络原因无法显示的时候，通过此方法显示提示界面给用户。
     *
     * @param listener 重新加载点击事件回调
     */
    protected final void showBadNetworkView(View.OnClickListener listener)
    {
        if (listener == null)
            return;

        if (badNetworkView != null)
        {
            badNetworkView.setVisibility(View.VISIBLE);
            return;
        }

        ViewStub viewStub = (ViewStub) findViewById(R.id.badNetworkView);
        if (viewStub != null)
        {
            badNetworkView = viewStub.inflate();
            View badNetworkRootView = badNetworkView != null ? badNetworkView.findViewById(R.id.badNetworkRootView) : null;
            if (badNetworkRootView != null)
            {
                badNetworkRootView.setOnClickListener(listener);
            }
        }
    }

    /**
     * 当Activity中没有任何内容的时候，通过此方法显示提示界面给用户。
     *
     * @param tip 界面中的提示信息
     */
    protected final void showNoContentView(String tip)
    {
        if (TextUtils.isEmpty(tip))
            return;

        if (noContentView != null)
        {
            noContentView.setVisibility(View.VISIBLE);
            return;
        }

        ViewStub viewStub = (ViewStub) findViewById(R.id.noContentView);
        if (viewStub != null)
        {
            noContentView = viewStub.inflate();
            TextView loadErrorText = noContentView != null ? (TextView) noContentView.findViewById(R.id.noContentText) : null;
            if (loadErrorText != null)
            {
                loadErrorText.setText(tip);
            }
        }
    }

    /**
     * 将load error view进行隐藏。
     */
    protected final void hideLoadErrorView()
    {
        if (loadErrorView != null)
        {
            loadErrorView.setVisibility(View.GONE);
            return;
        }
    }

    /**
     * 将no content view进行隐藏。
     */
    protected final void hideNoContentView()
    {
        if (noContentView != null)
        {
            noContentView.setVisibility(View.GONE);
            return;
        }
    }

    /**
     * 将bad network view进行隐藏。
     */
    protected final void hideBadNetworkView()
    {
        if (badNetworkView != null)
        {
            badNetworkView.setVisibility(View.GONE);
            return;
        }
    }


    public final void showProgressDialog(String title, String message)
    {
        if (progressDialog == null)
        {
            progressDialog = new ProgressDialog(this);
            if (title != null)
            {
                progressDialog.setTitle(title);
            }

            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
        }

        if (progressDialog != null)
        {
            progressDialog.show();
        }
    }

    public final void closeProgressDialog()
    {
        if (progressDialog != null)
        {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent)
    {
        // 判断Activity是否在前台，防止非前台的Activity也处理这个事件，造成打开多个LoginActivity的问题。
        if (messageEvent instanceof ForceToLoginEvent && this.isActive)
        {
            ActivityCollector.finishAll();
//            LoginActivity.Companion.actionStart((Activity) this, false, (Version) null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 检查和处理运行时权限，并将用户授权的结果通过PermissionListener进行回调。
     *
     * @param permissions 要检查和处理的运行时权限数组
     * @param listener    用于接收授权结果的监听器
     */
    protected void handlePermissions(ArrayList<String> permissions, PermissionListener listener)
    {
        if (permissions == null || activity == null)
        {
            return;
        }

        mListener = listener;
        ArrayList<String> requestPermissionList = new ArrayList<>();
        for (String permission : permissions)
        {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissionList.add(permission);
            }
        }

        if (!requestPermissionList.isEmpty())
        {
            ActivityCompat.requestPermissions(activity, requestPermissionList.toArray(new String[0]), 1);
        }
        else
        {
            listener.onGranted();
        }
    }

    protected void permissionsGranted()
    {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case 1:

                if (grantResults.length == 0)
                {
                    return;
                }

                ArrayList<String> deniedPermissions = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++)
                {
                    int grantResult = grantResults[i];
                    String permission = permissions[i];
                    if (grantResult != PackageManager.PERMISSION_GRANTED)
                    {
                        deniedPermissions.add(permission);
                    }
                }

                if (deniedPermissions.isEmpty())
                {
                    if (mListener != null)
                        mListener.onGranted();
                }
                else
                {
                    if (mListener != null)
                        mListener.onDenied(deniedPermissions);
                }
                break;


            default:
                break;
        }
    }

    @CallSuper
    public void startLoading()
    {
        if (loading != null)
        {
            loading.setVisibility(View.VISIBLE);
        }

        hideBadNetworkView();
        hideNoContentView();
        hideLoadErrorView();
    }

    @CallSuper
    public void loadFinished()
    {
        if (loading != null)
        {
            loading.setVisibility(View.GONE);
        }
    }

    @CallSuper
    public void loadFailed(@Nullable String msg)
    {
        if (loading != null)
        {
            loading.setVisibility(View.GONE);
        }
    }

}
