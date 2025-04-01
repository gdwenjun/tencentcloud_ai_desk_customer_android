package com.tencent.qcloud.deskcore.permission;

public abstract class PermissionCallback {
    public abstract void onGranted();

    public void onRequesting() {}

    public void onDenied() {}
}
