package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.deskchat.bean.MessageTyping;
import com.tencent.qcloud.tuikit.deskchat.bean.message.MessageTypingBean;

public class CustomerServiceTypingMessageBean extends MessageTypingBean {
    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        messageTyping = new MessageTyping();
        messageTyping.setTypingStatus(true);
    }
}
