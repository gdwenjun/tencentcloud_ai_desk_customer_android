package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public class BotBranchMessageReplyQuoteBean extends TUIReplyQuoteBean<BotBranchMessageBean> {
    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void onProcessReplyQuoteBean(BotBranchMessageBean messageBean) {
        text = messageBean.getExtra();
    }
}
