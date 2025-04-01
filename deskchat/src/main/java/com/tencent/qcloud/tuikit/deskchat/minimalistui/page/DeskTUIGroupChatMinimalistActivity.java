package com.tencent.qcloud.tuikit.deskchat.minimalistui.page;

import com.tencent.qcloud.deskcore.util.ToastUtil;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.GroupChatInfo;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class DeskTUIGroupChatMinimalistActivity extends DeskTUIBaseChatMinimalistActivity {
    private static final String TAG = DeskTUIGroupChatMinimalistActivity.class.getSimpleName();

    @Override
    public void initChat(ChatInfo chatInfo) {
        TUIChatLog.i(TAG, "inti chat " + chatInfo);

        if (!(chatInfo instanceof GroupChatInfo)) {
            TUIChatLog.e(TAG, "init group chat failed , chatInfo = " + chatInfo);
            ToastUtil.toastShortMessage("init group chat failed.");
            return;
        }

        DeskDeskTUIGroupChatMinimalistFragment chatFragment = new DeskDeskTUIGroupChatMinimalistFragment();
        chatFragment.setChatInfo((GroupChatInfo) chatInfo);
        getSupportFragmentManager().beginTransaction().replace(R.id.empty_view, chatFragment).commitAllowingStateLoss();
    }
}