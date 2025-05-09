package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public class ThinkingBeanReplyQuoteBean extends TUIReplyQuoteBean<ThinkingMessageBean> {

    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void onProcessReplyQuoteBean(ThinkingMessageBean messageBean) {
        text = messageBean.getExtra();
    }
}
