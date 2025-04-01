package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.TUICustomerServicePluginService;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceMessageParser;

public class TasksCollectionMessageBean extends TUIMessageBean {
    private TasksCollectionBean collectionBean;

    public TasksCollectionBean getCollectionBean() {
        return collectionBean;
    }

    @Override
    public String onGetDisplayString() {
        return getExtra();
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        collectionBean = TUICustomerServiceMessageParser.getTasksCollectionInfo(v2TIMMessage);
        if (collectionBean != null) {
            setExtra(collectionBean.getHead());
        } else {
            String text = TUICustomerServicePluginService.getAppContext().getString(com.tencent.qcloud.tuikit.deskcommon.R.string.timcommon_no_support_msg);
            setExtra(text);
        }
    }

    @Override
    public Class<? extends TUIReplyQuoteBean<?>> getReplyQuoteBeanClass() {
        return TasksCollectionMessageReplyQuoteBean.class;
    }
}
