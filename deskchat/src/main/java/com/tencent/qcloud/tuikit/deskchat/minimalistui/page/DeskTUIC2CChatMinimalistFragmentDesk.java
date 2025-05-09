package com.tencent.qcloud.tuikit.deskchat.minimalistui.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.tuikit.deskchat.bean.C2CChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.presenter.C2CChatPresenter;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class DeskTUIC2CChatMinimalistFragmentDesk extends DeskTUIBaseChatMinimalistFragment {
    private static final String TAG = DeskTUIC2CChatMinimalistFragmentDesk.class.getSimpleName();

    private final C2CChatPresenter presenter;

    public DeskTUIC2CChatMinimalistFragmentDesk() {
        presenter = new C2CChatPresenter();
        presenter.initListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TUIChatLog.i(TAG, "oncreate view " + this);

        baseView = super.onCreateView(inflater, container, savedInstanceState);
        if (!(chatInfo instanceof C2CChatInfo)) {
            return baseView;
        }

        initView();

        return baseView;
    }

    @Override
    protected void initView() {
        super.initView();

        setTitleBarClickAction();
        chatView.setPresenter(presenter);
        presenter.setTypingListener(chatView.typingListener);
        presenter.setChatInfo((C2CChatInfo) chatInfo);
        chatView.setChatInfo(chatInfo);
    }

    private void setTitleBarClickAction() {
        chatView.setOnAvatarClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle param = new Bundle();
                param.putString(TUIConstants.TUIChat.CHAT_ID, chatInfo.getId());
                param.putString(TUIConstants.TUIChat.CHAT_BACKGROUND_URI, mChatBackgroundThumbnailUrl);
                TUICore.startActivity("FriendProfileMinimalistActivity", param);
            }
        });
    }

    @Override
    public C2CChatPresenter getPresenter() {
        return presenter;
    }

    @Override
    public ChatInfo getChatInfo() {
        return chatInfo;
    }

    public void setChatInfo(C2CChatInfo chatInfo) {
        this.chatInfo = chatInfo;
    }
}
