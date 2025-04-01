package com.tencent.qcloud.tuikit.deskchat.component.imagevideoscan;

public interface OnViewPagerListener {
    void onInitComplete();

    void onPageRelease(boolean isNext, int position);

    void onPageSelected(int position, boolean isBottom, boolean isLeftScroll);
}
