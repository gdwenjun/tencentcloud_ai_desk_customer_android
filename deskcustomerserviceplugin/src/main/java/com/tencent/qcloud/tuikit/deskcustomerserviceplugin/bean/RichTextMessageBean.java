package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.deskchat.bean.message.TextMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceMessageParser;

public class RichTextMessageBean extends TextMessageBean {
    private String content;

    @Override
    public String onGetDisplayString() {
        return "Rich Text";
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        content = TUICustomerServiceMessageParser.getRichText(v2TIMMessage);
        setExtra(content);
    }
}
