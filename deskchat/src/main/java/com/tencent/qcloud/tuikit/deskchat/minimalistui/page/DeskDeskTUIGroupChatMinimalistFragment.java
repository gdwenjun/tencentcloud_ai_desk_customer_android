package com.tencent.qcloud.tuikit.deskchat.minimalistui.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskchat.TUIChatConstants;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.GroupChatInfo;
import com.tencent.qcloud.tuikit.deskchat.presenter.GroupChatPresenter;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class DeskDeskTUIGroupChatMinimalistFragment extends DeskTUIBaseChatMinimalistFragment {
    private static final String TAG = DeskDeskTUIGroupChatMinimalistFragment.class.getSimpleName();

    private final GroupChatPresenter presenter;

    public DeskDeskTUIGroupChatMinimalistFragment() {
        presenter = new GroupChatPresenter();
        presenter.initListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TUIChatLog.i(TAG, "oncreate view " + this);

        baseView = super.onCreateView(inflater, container, savedInstanceState);
        if (!(chatInfo instanceof GroupChatInfo)) {
            return baseView;
        }

        initView();
        return baseView;
    }

    @Override
    protected void initView() {
        super.initView();
        chatView.setPresenter(presenter);
        presenter.setGroupInfo((GroupChatInfo) chatInfo);
        chatView.setChatInfo(chatInfo);
        setTitleBarClickAction();
    }

    private void setTitleBarClickAction() {
        chatView.setOnAvatarClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle param = new Bundle();
                param.putString(TUIChatConstants.GROUP_ID, chatInfo.getId());
                param.putString(TUIConstants.TUIChat.CHAT_BACKGROUND_URI, mChatBackgroundThumbnailUrl);
                TUICore.startActivity("GroupInfoMinimalistActivity", param);
            }
        });
    }

    @Override
    protected void onUserIconLongClicked(TUIMessageBean messageBean) {
        String resultId = messageBean.getV2TIMMessage().getSender();
        String resultName = messageBean.getV2TIMMessage().getNickName();
        chatView.getInputLayout().addInputText(resultName, resultId);
    }

    @Override
    public GroupChatPresenter getPresenter() {
        return presenter;
    }

    @Override
    public ChatInfo getChatInfo() {
        return this.chatInfo;
    }

    public void setChatInfo(GroupChatInfo groupChatInfo) {
        this.chatInfo = groupChatInfo;
    }
}
