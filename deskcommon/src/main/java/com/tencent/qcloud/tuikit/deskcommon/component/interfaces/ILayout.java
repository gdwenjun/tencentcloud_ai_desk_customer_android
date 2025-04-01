package com.tencent.qcloud.tuikit.deskcommon.component.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.component.TitleBarLayout;

public interface ILayout {
    /**
     * get title bar
     *
     * @return
     */
    TitleBarLayout getTitleBar();

    /**
     * Set the parent container of this Layout
     *
     * @param parent
     */
    void setParentLayout(Object parent);
}
