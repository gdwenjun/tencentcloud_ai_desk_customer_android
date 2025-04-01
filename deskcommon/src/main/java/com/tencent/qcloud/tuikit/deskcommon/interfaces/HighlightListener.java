package com.tencent.qcloud.tuikit.deskcommon.interfaces;

public interface HighlightListener {

    void onHighlightStart();

    void onHighlightEnd();

    void onHighlightUpdate(int color);
}