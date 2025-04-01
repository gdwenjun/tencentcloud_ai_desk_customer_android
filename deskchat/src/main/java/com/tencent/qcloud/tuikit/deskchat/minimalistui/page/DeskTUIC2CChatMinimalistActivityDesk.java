package com.tencent.qcloud.tuikit.deskchat.minimalistui.page;

import com.tencent.qcloud.deskcore.util.ToastUtil;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.C2CChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class DeskTUIC2CChatMinimalistActivityDesk extends DeskTUIBaseChatMinimalistActivity {
    private static final String TAG = DeskTUIC2CChatMinimalistActivityDesk.class.getSimpleName();

    @Override
    public void initChat(ChatInfo chatInfo) {
        TUIChatLog.i(TAG, "inti chat " + chatInfo);

        if (!(chatInfo instanceof C2CChatInfo)) {
            TUIChatLog.e(TAG, "init C2C chat failed , chatInfo = " + chatInfo);
            ToastUtil.toastShortMessage("init c2c chat failed.");
            return;
        }
        DeskTUIC2CChatMinimalistFragmentDesk chatFragment = new DeskTUIC2CChatMinimalistFragmentDesk();
        chatFragment.setChatInfo((C2CChatInfo) chatInfo);

        getSupportFragmentManager().beginTransaction().replace(R.id.empty_view, chatFragment).commitAllowingStateLoss();
    }
}
