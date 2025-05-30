package com.tencent.qcloud.tuikit.deskchat.bean.message.reply;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.CustomLinkMessageBean;

public class CustomLinkReplyQuoteBean extends TextReplyQuoteBean {
    @Override
    public void onProcessReplyQuoteBean(TUIMessageBean messageBean) {
        if (messageBean instanceof CustomLinkMessageBean) {
            setText(((CustomLinkMessageBean) messageBean).getText());
        }
    }
}
