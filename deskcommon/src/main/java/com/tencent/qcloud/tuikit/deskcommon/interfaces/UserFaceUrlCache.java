package com.tencent.qcloud.tuikit.deskcommon.interfaces;

public interface UserFaceUrlCache {

    String getCachedFaceUrl(String userID);

    void pushFaceUrl(String userID, String faceUrl);
}
