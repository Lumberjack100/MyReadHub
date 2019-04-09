package com.dragon.myreadhub.Interfaces;

import java.util.List;

public interface PermissionListener
{
    void onGranted();

    void onDenied(List<String> deniedPermissions);
}
