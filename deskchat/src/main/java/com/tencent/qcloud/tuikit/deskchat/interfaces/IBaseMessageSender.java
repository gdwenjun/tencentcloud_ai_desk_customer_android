package com.tencent.qcloud.tuikit.deskchat.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;

public interface IBaseMessageSender {
    /**
     *
     * send message
     *
     * @param message 
     * @param receiver  Id
     * @param isGroup 
     */
    String sendMessage(final TUIMessageBean message, String receiver, boolean isGroup, boolean onlineUserOnly);
}
