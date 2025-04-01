package com.tencent.qcloud.tuikit.deskcommon.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.ChatFace;

public interface OnFaceInputListener {

    void onEmojiClicked(String emojiKey);

    void onDeleteClicked();

    void onSendClicked();

    void onFaceClicked(ChatFace chatFace);
}
