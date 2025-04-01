package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public class TasksCollectionMessageReplyQuoteBean extends TUIReplyQuoteBean<TasksCollectionMessageBean> {

    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void onProcessReplyQuoteBean(TasksCollectionMessageBean messageBean) {
        text = messageBean.getExtra();
    }
}
