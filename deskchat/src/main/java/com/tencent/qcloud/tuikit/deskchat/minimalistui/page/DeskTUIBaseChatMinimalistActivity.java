package com.tencent.qcloud.tuikit.deskchat.minimalistui.page;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.tencent.imsdk.v2.V2TIMGroupAtInfo;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.util.ToastUtil;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.component.activities.BaseMinimalistLightActivity;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.C2CChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.DraftInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.GroupChatInfo;
import com.tencent.qcloud.tuikit.deskchat.util.ChatMessageBuilder;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;
import java.util.List;

public abstract class DeskTUIBaseChatMinimalistActivity extends BaseMinimalistLightActivity {
    private static final String TAG = DeskTUIBaseChatMinimalistActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TUIChatLog.i(TAG, "onCreate " + this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.desk_chat_activity);
        initChat(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        TUIChatLog.i(TAG, "onNewIntent");
        super.onNewIntent(intent);
        initChat(intent);
    }

    @Override
    protected void onResume() {
        TUIChatLog.i(TAG, "onResume");
        super.onResume();
    }

    private void initChat(Intent intent) {
        Bundle bundle = intent.getExtras();
        TUIChatLog.i(TAG, "bundle: " + bundle + " intent: " + intent);

        ChatInfo chatInfo = getChatInfo(intent);
        TUIChatLog.i(TAG, "start chatActivity chatInfo: " + chatInfo);

        if (chatInfo != null) {
            initChat(chatInfo);
        } else {
            ToastUtil.toastShortMessage("init chat failed , chatInfo is empty.");
            TUIChatLog.e(TAG, "init chat failed , chatInfo is empty.");
            finish();
        }
    }

    public abstract void initChat(ChatInfo chatInfo);

    private ChatInfo getChatInfo(Intent intent) {
        int chatType = intent.getIntExtra(TUIConstants.TUIChat.CHAT_TYPE, ChatInfo.TYPE_INVALID);
        ChatInfo chatInfo;
        if (chatType == ChatInfo.TYPE_C2C) {
            chatInfo = new C2CChatInfo();
        } else if (chatType == ChatInfo.TYPE_GROUP) {
            chatInfo = new GroupChatInfo();
        } else {
            return null;
        }
        chatInfo.setType(chatType);
        chatInfo.setId(intent.getStringExtra(TUIConstants.TUIChat.CHAT_ID));
        chatInfo.setChatName(intent.getStringExtra(TUIConstants.TUIChat.CHAT_NAME));
        DraftInfo draftInfo = new DraftInfo();
        draftInfo.setDraftText(intent.getStringExtra(TUIConstants.TUIChat.DRAFT_TEXT));
        draftInfo.setDraftTime(intent.getLongExtra(TUIConstants.TUIChat.DRAFT_TIME, 0));
        chatInfo.setDraft(draftInfo);
        chatInfo.setTopChat(intent.getBooleanExtra(TUIConstants.TUIChat.IS_TOP_CHAT, false));
        V2TIMMessage v2TIMMessage = (V2TIMMessage) intent.getSerializableExtra(TUIConstants.TUIChat.LOCATE_MESSAGE);
        TUIMessageBean messageInfo = ChatMessageBuilder.buildMessage(v2TIMMessage);
        chatInfo.setLocateMessage(messageInfo);
        chatInfo.setAtInfoList((List<V2TIMGroupAtInfo>) intent.getSerializableExtra(TUIConstants.TUIChat.AT_INFO_LIST));
        chatInfo.setFaceUrl(intent.getStringExtra(TUIConstants.TUIChat.FACE_URL));

        if (chatType == ChatInfo.TYPE_GROUP) {
            GroupChatInfo groupChatInfo = (GroupChatInfo) chatInfo;
            groupChatInfo.setGroupName(intent.getStringExtra(TUIConstants.TUIChat.GROUP_NAME));
            groupChatInfo.setGroupType(intent.getStringExtra(TUIConstants.TUIChat.GROUP_TYPE));
            groupChatInfo.setNotice(intent.getStringExtra(TUIConstants.TUIChat.NOTICE));
            groupChatInfo.setIconUrlList((List<Object>) intent.getSerializableExtra(TUIConstants.TUIChat.FACE_URL_LIST));
        }

        if (TextUtils.isEmpty(chatInfo.getId())) {
            return null;
        }
        return chatInfo;
    }
}
