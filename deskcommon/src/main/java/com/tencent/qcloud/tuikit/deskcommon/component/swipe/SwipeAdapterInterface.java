package com.tencent.qcloud.tuikit.deskcommon.component.swipe;

public interface SwipeAdapterInterface {
    int getSwipeLayoutResourceId(int position);

    void notifyDatasetChanged();

    void notifySwipeItemChanged(int position);
}
