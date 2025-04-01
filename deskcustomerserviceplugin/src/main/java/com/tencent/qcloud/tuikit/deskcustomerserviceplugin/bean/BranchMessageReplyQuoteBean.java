package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public class BranchMessageReplyQuoteBean extends TUIReplyQuoteBean<BranchMessageBean> {
    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void onProcessReplyQuoteBean(BranchMessageBean messageBean) {
        text = messageBean.getExtra();
    }
}
