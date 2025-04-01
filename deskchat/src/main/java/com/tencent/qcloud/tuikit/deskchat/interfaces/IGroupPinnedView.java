package com.tencent.qcloud.tuikit.deskchat.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;

import java.util.List;

public interface IGroupPinnedView {
    void onPinnedListChanged(List<TUIMessageBean> pinnedMessages);
}
