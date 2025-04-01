package com.tencent.qcloud.deskcore.interfaces;

import java.util.Map;

public interface ITUINotification {
    void onNotifyEvent(String key, String subKey, Map<String, Object> param);
}
