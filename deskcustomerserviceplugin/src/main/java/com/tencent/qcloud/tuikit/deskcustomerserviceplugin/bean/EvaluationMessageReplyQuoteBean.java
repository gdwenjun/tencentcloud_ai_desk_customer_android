package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public class EvaluationMessageReplyQuoteBean extends TUIReplyQuoteBean<EvaluationMessageBean> {
    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void onProcessReplyQuoteBean(EvaluationMessageBean messageBean) {
        text = messageBean.getExtra();
    }
}
