package com.tencent.qcloud.tuikit.deskchat.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.MessageReceiptInfo;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.UserBean;

import java.util.List;

/**
 * C2C Chat event listener
 */
public abstract class C2CChatEventListener {

    public void onReadReport(List<MessageReceiptInfo> receiptList) {}

    public void onRecvMessageRevoked(String msgID, UserBean userBean, String reason) {}

    public void onRecvNewMessage(TUIMessageBean message) {}

    public void exitC2CChat(String chatId) {}

    public void onFriendNameChanged(String userId, String newName) {}

    public void onFriendFaceUrlChanged(String userId, String newFaceUrl) {}

    public void onRecvMessageModified(TUIMessageBean messageBean) {}

    public void addMessage(TUIMessageBean messageBean, String chatId) {}
    
    public void onMessageChanged(TUIMessageBean messageBean, int dataChangeType) {}

    public void clearC2CMessage(String userID) {}
}
