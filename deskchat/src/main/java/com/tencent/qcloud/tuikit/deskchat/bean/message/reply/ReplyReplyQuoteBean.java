package com.tencent.qcloud.tuikit.deskchat.bean.message.reply;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.QuoteMessageBean;

public class ReplyReplyQuoteBean extends TextReplyQuoteBean {
    @Override
    public void onProcessReplyQuoteBean(TUIMessageBean messageBean) {
        if (messageBean instanceof QuoteMessageBean) {
            setText(messageBean.getExtra());
        }
    }
}
