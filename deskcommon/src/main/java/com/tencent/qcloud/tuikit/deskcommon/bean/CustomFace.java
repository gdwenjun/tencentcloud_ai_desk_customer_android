package com.tencent.qcloud.tuikit.deskcommon.bean;

/**
 *
 * Custom expression attribute class
 */
public class CustomFace extends ChatFace {
    /**
     *
     * @param assetPath
     */
    public void setAssetPath(String assetPath) {
        this.faceUrl = "file:///android_asset/" + assetPath;
    }
}
