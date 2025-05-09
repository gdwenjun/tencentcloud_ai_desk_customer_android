package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceMessageParser;

public class ThinkingMessageBean extends TUIMessageBean {
    private ThinkingBean thinkingBean;
    @Override
    public String onGetDisplayString() {
        return thinkingBean.getThinkingStatus() == 0 ? "Thinking" : "Thinking" + thinkingBean.getThinkingStatus();
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        thinkingBean = TUICustomerServiceMessageParser.getThinkingInfo(v2TIMMessage);
        TUIChatLog.i("ThinkingMessageBean", " ThinkingStatus = " + thinkingBean.getThinkingStatus());
    }

    public ThinkingBean getThinkingBean() {
        return thinkingBean;
    }
}
