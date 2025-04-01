package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public class StreamTextMessageReplyQuoteBean extends TUIReplyQuoteBean<StreamTextMessageBean> {
    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void onProcessReplyQuoteBean(StreamTextMessageBean messageBean) {
        text = messageBean.getExtra();
    }
}
