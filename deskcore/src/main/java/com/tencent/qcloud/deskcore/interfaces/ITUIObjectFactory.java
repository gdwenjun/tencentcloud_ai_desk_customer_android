package com.tencent.qcloud.deskcore.interfaces;

import java.util.Map;

public interface ITUIObjectFactory {
    Object onCreateObject(String objectName, Map<String, Object> param);
}
