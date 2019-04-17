package com.dragon.myreadhub.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import com.dragon.myreadhub.Interfaces.PermissionListener;
import com.dragon.myreadhub.R;
import com.dragon.myreadhub.constant.PermissionConstants;
import com.dragon.myreadhub.utils.GlobalUtil;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity
{

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
            finish();

            handler.removeMessages(-1);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        refreshPermissionStatus();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            handler.sendMessage(handler.obtainMessage(-1));
        }

        return super.onTouchEvent(event);
    }


    private void refreshPermissionStatus()
    {
        ArrayList<String> permissions = new ArrayList<>(2);
        permissions.add(PermissionConstants.WRITE_EXTERNAL_STORAGE);

        handlePermissions(permissions, new PermissionListener()
        {
            @Override
            public void onGranted()
            {
                permissionsGranted();
            }

            @Override
            public void onDenied(List<String> deniedPermissions)
            {
                boolean allNeverAskAgain = true;
                for (String deniedPermission : deniedPermissions)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, deniedPermission))
                    {
                        showRequestPermissionDialog();
                        allNeverAskAgain = false;
                        break;
                    }
                }

                // 所有的权限都被勾上不再询问时，跳转到应用设置界面，引导用户手动打开权限
                if (allNeverAskAgain)
                {
                    AlertDialog dialog = new AlertDialog.Builder(activity, R.style.MyReadHubAlertDialogStyle)
                            .setMessage(GlobalUtil.getString(R.string.allow_storage_permission_please))
                            .setPositiveButton(GlobalUtil.getString(R.string.settings), new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", GlobalUtil.getAppPackage(), null);
                            intent.setData(uri);
                            SplashActivity.this.startActivityForResult(intent, 1);
                        }
                    }).setNegativeButton(GlobalUtil.getString(R.string.cancel), null).create();
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void permissionsGranted()
    {
        handler.sendMessageDelayed(handler.obtainMessage(-1), 3000);
    }

    private void showRequestPermissionDialog()
    {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.MyReadHubAlertDialogStyle)
                .setMessage(GlobalUtil.getString(R.string.allow_storage_permission_please))
                .setPositiveButton(GlobalUtil.getString(R.string.ok), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        refreshPermissionStatus();
                    }
                }).create();
        dialog.show();
    }
}
