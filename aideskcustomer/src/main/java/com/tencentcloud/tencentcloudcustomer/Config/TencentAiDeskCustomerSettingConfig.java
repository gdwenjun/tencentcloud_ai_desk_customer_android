package com.tencentcloud.tencentcloudcustomer.Config;

import com.tencent.qcloud.deskcore.interfaces.TUILoginConfig;

public class TencentAiDeskCustomerSettingConfig extends TUILoginConfig {
    private static final TencentAiDeskCustomerSettingConfig sConfigs = new TencentAiDeskCustomerSettingConfig();

    private TencentAiDeskCustomerSettingConfig() {}

    public static TencentAiDeskCustomerSettingConfig getConfigs() {
        return sConfigs;
    }
}
