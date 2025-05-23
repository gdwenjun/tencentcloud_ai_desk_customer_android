package com.tencent.qcloud.tuikit.deskchat.bean.message.reply;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.CustomOrderMessageBean;

public class CustomOrderMessageReplyQuoteBean extends TextReplyQuoteBean {
    @Override
    public void onProcessReplyQuoteBean(TUIMessageBean messageBean) {
        if (messageBean instanceof CustomOrderMessageBean) {
            setText(((CustomOrderMessageBean) messageBean).getDescription());
        }
    }
}
