package com.tencent.qcloud.tuikit.deskchat.classicui.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.component.TitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.ILayout;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.classicui.component.noticelayout.NoticeLayout;
import com.tencent.qcloud.tuikit.deskchat.classicui.page.DeskTUIBaseChatFragment;
import com.tencent.qcloud.tuikit.deskchat.classicui.widget.ChatView;
import com.tencent.qcloud.tuikit.deskchat.classicui.widget.input.InputView;
import com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.MessageRecyclerView;

/**
 *
 * The chat window {@link ChatView} provides functions such as displaying and sending messages.
 * The interface layout is divided into four parts from top to bottom:
 *  TitleBarLayout {@link TitleBarLayout}，
 *  NoticeLayout {@link NoticeLayout}，
 *  MessageRecyclerView {@link MessageRecyclerView}，
 *  InputView {@link InputView},
 *  Each area offers a variety of methods for customization.
 */
public interface IChatLayout extends ILayout {

    InputView getInputLayout();

    MessageRecyclerView getMessageLayout();

    NoticeLayout getNoticeLayout();

    ChatInfo getChatInfo();

    void setChatInfo(ChatInfo chatInfo);

    void exitChat();

    void initDefault(DeskTUIBaseChatFragment fragment);

    void loadMessages(int type);

    void sendMessage(TUIMessageBean msg, boolean retry);

}
