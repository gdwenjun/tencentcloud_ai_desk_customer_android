package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public class TasksBranchMessageReplyQuoteBean extends TUIReplyQuoteBean<TasksBranchMessageBean> {
    private String text;

    public String getText() {
        return text;
    }

    @Override
    public void onProcessReplyQuoteBean(TasksBranchMessageBean messageBean) {
        text = messageBean.getExtra();
    }
}
