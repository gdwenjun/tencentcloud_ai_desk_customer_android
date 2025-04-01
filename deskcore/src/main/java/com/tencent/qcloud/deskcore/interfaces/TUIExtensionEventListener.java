package com.tencent.qcloud.deskcore.interfaces;

import java.util.Map;

public abstract class TUIExtensionEventListener {
    public void onClicked(Map<String, Object> param) {}

    public void onLongPressed(Map<String, Object> param) {}

    public void onTouched(Map<String, Object> param) {}

    public void onSwiped(int direction, Map<String, Object> param) {}
}
