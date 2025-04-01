package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.TUICustomerServicePluginService;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceMessageParser;

public class BotBranchMessageBean extends TUIMessageBean {
    private BotBranchBean botBranchBean;

    public BotBranchBean getBotBranchBean() {
        return botBranchBean;
    }

    @Override
    public String onGetDisplayString() {
        return getExtra();
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        botBranchBean = TUICustomerServiceMessageParser.getBotBranchInfo(v2TIMMessage);
        if (botBranchBean != null) {
            setExtra(botBranchBean.getTitle());
        } else {
            String text = TUICustomerServicePluginService.getAppContext().getString(com.tencent.qcloud.tuikit.deskcommon.R.string.timcommon_no_support_msg);
            setExtra(text);
        }
    }

    @Override
    public Class<? extends TUIReplyQuoteBean<?>> getReplyQuoteBeanClass() {
        return BranchMessageReplyQuoteBean.class;
    }
}
