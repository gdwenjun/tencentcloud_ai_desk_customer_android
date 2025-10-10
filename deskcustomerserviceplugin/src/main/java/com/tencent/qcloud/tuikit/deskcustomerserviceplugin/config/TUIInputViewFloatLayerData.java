package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config;

import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.InputViewFloatLayerProxy;

public class TUIInputViewFloatLayerData {
    private String content;
    private int iconResourceId = -1;
    private String presetId = "";
    private InputViewFloatLayerProxy.OnItemClickListener OnItemClickListener;
    private boolean isDefault = false;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    private boolean isVisible = true;

    public boolean isDefault() {
        return isDefault;
    }

    public String getPresetId() {
        return presetId;
    }

    public void setPresetId(String id) {
        this.presetId = id;
    }
    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int value) {
        this.iconResourceId = value;
    }

    public InputViewFloatLayerProxy.OnItemClickListener getOnItemClickListener() {
        return OnItemClickListener;
    }

    public void setOnItemClickListener(InputViewFloatLayerProxy.OnItemClickListener onItemClickListener) {
        OnItemClickListener = onItemClickListener;
    }
}
