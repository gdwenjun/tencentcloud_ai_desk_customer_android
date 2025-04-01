package com.tencent.qcloud.tuikit.deskchat.minimalistui.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.component.noticelayout.NoticeLayout;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.page.DeskTUIBaseChatMinimalistFragment;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.input.InputView;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.MessageRecyclerView;

/**
 *
 * The chat window {@link com.tencent.qcloud.tuikit.deskchat.classicui.widget.ChatView} provides functions such as displaying and sending messages.
 * The interface layout is divided into four parts from top to bottom:
 *  TitleBarLayout {@link com.tencent.qcloud.deskcore.component.TitleBarLayout}，
 *  NoticeLayout {@link com.tencent.qcloud.tuikit.deskchat.minimalistui.component.noticelayout.NoticeLayout}，
 *  MessageRecyclerView {@link com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.MessageRecyclerView}，
 *  InputView {@link com.tencent.qcloud.tuikit.deskchat.classicui.widget.input.InputView},
 *  Each area offers a variety of methods for customization.
 */
public interface IChatLayout {

    InputView getInputLayout();

    MessageRecyclerView getMessageLayout();

    NoticeLayout getNoticeLayout();

    ChatInfo getChatInfo();

    void setChatInfo(ChatInfo chatInfo);

    void exitChat();

    void initDefault(DeskTUIBaseChatMinimalistFragment fragment);

    void loadMessages(int type);

    void sendMessage(TUIMessageBean msg, boolean retry);
}
