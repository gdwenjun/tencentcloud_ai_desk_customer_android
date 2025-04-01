package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public class CardMessageReplyQuoteBean extends TUIReplyQuoteBean<CardMessageBean> {
    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void onProcessReplyQuoteBean(CardMessageBean messageBean) {
        text = messageBean.getExtra();
    }
}
