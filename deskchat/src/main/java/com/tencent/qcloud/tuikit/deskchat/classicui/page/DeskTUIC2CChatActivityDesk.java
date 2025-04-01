package com.tencent.qcloud.tuikit.deskchat.classicui.page;



import com.tencent.qcloud.deskcore.util.ToastUtil;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.C2CChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.presenter.C2CChatPresenter;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class DeskTUIC2CChatActivityDesk extends DeskTUIBaseChatActivity {
    private static final String TAG = DeskTUIC2CChatActivityDesk.class.getSimpleName();

    private DeskTUIC2CChatFragment chatFragment;

    @Override
    public void initChat(ChatInfo chatInfo) {



        TUIChatLog.i(TAG, "inti chat " + chatInfo);

        if (!(chatInfo instanceof C2CChatInfo)) {
            TUIChatLog.e(TAG, "init C2C chat failed , chatInfo = " + chatInfo);
            ToastUtil.toastShortMessage("init c2c chat failed.");
            return;
        }

        chatFragment = new DeskTUIC2CChatFragment();
        chatFragment.setChatInfo((C2CChatInfo) chatInfo);
        chatFragment.setActivityInstance(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.empty_view, chatFragment).commitAllowingStateLoss();

    }



    @Override
    protected void onDestroy() {
        C2CChatPresenter chatPresenter = null;
        if (chatFragment != null) {
            chatPresenter = chatFragment.getPresenter();
        }
        if (chatPresenter != null) {
            chatPresenter.removeC2CChatEventListener();
        }

        super.onDestroy();
    }
}
