package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config;

import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.InputViewFloatLayerProxy;

public class TUIInputViewFloatLayerData {
    private String content;
    private int iconResourceId = -1;
    private InputViewFloatLayerProxy.OnItemClickListener OnItemClickListener;

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
