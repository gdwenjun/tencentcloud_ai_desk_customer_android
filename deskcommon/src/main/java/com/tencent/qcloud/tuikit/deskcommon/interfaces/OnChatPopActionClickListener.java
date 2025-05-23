package com.tencent.qcloud.tuikit.deskcommon.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;

public abstract class OnChatPopActionClickListener {
    public void onCopyClick(TUIMessageBean msg) {}

    public void onSendMessageClick(TUIMessageBean msg, boolean retry) {}

    public void onDeleteMessageClick(TUIMessageBean msg) {}

    public void onRevokeMessageClick(TUIMessageBean msg) {}

    public void onMultiSelectMessageClick(TUIMessageBean msg) {}

    public void onForwardMessageClick(TUIMessageBean msg) {}

    public void onReplyMessageClick(TUIMessageBean msg) {}

    public void onQuoteMessageClick(TUIMessageBean msg) {}

    public void onInfoMessageClick(TUIMessageBean msg) {}

    public void onSpeakerModeSwitchClick(TUIMessageBean msg) {}
}
