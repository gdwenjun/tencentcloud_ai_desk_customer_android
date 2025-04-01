package com.tencent.qcloud.tuikit.deskchat.classicui.page;

import com.tencent.qcloud.deskcore.util.ToastUtil;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.GroupChatInfo;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class DeskDeskTUIGroupChatActivity extends DeskTUIBaseChatActivity {
    private static final String TAG = DeskDeskTUIGroupChatActivity.class.getSimpleName();

    @Override
    public void initChat(ChatInfo chatInfo) {
        TUIChatLog.i(TAG, "inti chat " + chatInfo);

        if (!(chatInfo instanceof GroupChatInfo)) {
            TUIChatLog.e(TAG, "init group chat failed , chatInfo = " + chatInfo);
            ToastUtil.toastShortMessage("init group chat failed.");
            return;
        }
        GroupChatInfo groupChatInfo = (GroupChatInfo) chatInfo;

        DeskDeskTUIGroupChatFragment chatFragment = new DeskDeskTUIGroupChatFragment();
        chatFragment.setChatInfo(groupChatInfo);

        getSupportFragmentManager().beginTransaction().replace(R.id.empty_view, chatFragment).commitAllowingStateLoss();
    }
}