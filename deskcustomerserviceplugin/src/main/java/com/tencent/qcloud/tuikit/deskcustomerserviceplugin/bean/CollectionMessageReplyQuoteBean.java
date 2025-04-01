package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public class CollectionMessageReplyQuoteBean extends TUIReplyQuoteBean<CollectionMessageBean> {
    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void onProcessReplyQuoteBean(CollectionMessageBean messageBean) {
        text = messageBean.getExtra();
    }
}
