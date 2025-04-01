package com.tencent.qcloud.tuikit.deskcommon.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.IUIKitCallback;

public abstract class ChatInputMoreListener {
    public String sendMessage(TUIMessageBean msg, IUIKitCallback<TUIMessageBean> callback) {
        return null;
    }
}
