package com.tencent.qcloud.tuikit.deskchat.bean.message.reply;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.CustomEvaluationMessageBean;

public class CustomEvaluationMessageReplyQuoteBean extends TextReplyQuoteBean {
    @Override
    public void onProcessReplyQuoteBean(TUIMessageBean messageBean) {
        if (messageBean instanceof CustomEvaluationMessageBean) {
            setText(((CustomEvaluationMessageBean) messageBean).getContent());
        }
    }
}
