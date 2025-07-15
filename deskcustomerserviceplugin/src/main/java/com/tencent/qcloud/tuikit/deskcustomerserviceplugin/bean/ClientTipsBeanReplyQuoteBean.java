package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public class ClientTipsBeanReplyQuoteBean extends TUIReplyQuoteBean<ClientTipsMessageBean> {

    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void onProcessReplyQuoteBean(ClientTipsMessageBean messageBean) {
        text = messageBean.getExtra();
    }
}
