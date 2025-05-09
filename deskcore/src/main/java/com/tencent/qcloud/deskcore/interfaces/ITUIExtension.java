package com.tencent.qcloud.deskcore.interfaces;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ITUIExtension {
    @Deprecated
    default Map<String, Object> onGetExtensionInfo(String key, Map<String, Object> param) {
        return new HashMap<>();
    }

    default boolean onRaiseExtension(String extensionID, View parentView, Map<String, Object> param) {
        return false;
    }
    
    default List<TUIExtensionInfo> onGetExtension(String extensionID, Map<String, Object> param) {
        return new ArrayList<>();
    }
}
