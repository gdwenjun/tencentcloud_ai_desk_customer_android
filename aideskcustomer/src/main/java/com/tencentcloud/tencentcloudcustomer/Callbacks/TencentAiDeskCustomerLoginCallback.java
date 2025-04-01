package com.tencentcloud.tencentcloudcustomer.Callbacks;

public abstract class TencentAiDeskCustomerLoginCallback {
    public abstract void onSuccess();
    public abstract void onError(int code, String desc);
}