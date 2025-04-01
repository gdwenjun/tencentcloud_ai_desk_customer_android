package com.tencentcloud.tencentcloudcustomer.Interface;

import android.view.View;

import com.tencentcloud.tencentcloudcustomer.Model.TencentAiDeskCustomerQuickMessageInfo;

public interface TencentAiDeskCustomerQuickMessageOnClick {
    void onItemClick(View view, int position, TencentAiDeskCustomerQuickMessageInfo data);
}