package com.tencent.qcloud.tuikit.deskchat.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.MessageRepliesBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;

import java.util.Map;

public interface IReplyMessageHandler {
    void updateData(TUIMessageBean messageBean);

    void onRepliesMessageFound(Map<MessageRepliesBean.ReplyBean, TUIMessageBean> messageBeanMap);
}
