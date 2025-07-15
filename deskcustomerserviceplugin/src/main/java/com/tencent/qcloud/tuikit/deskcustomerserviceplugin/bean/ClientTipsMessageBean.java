package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceMessageParser;

public class ClientTipsMessageBean extends TUIMessageBean {

    private ClientTipsBean clientTipsBean;

    @Override
    public String onGetDisplayString() {
        return getExtra();
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        clientTipsBean = TUICustomerServiceMessageParser.getClientTipsInfo(v2TIMMessage);
        setExtra(clientTipsBean.getContent());
    }

    @Override
    public Class<? extends TUIReplyQuoteBean<?>> getReplyQuoteBeanClass() {
        return ClientTipsBeanReplyQuoteBean.class;
    }
}
