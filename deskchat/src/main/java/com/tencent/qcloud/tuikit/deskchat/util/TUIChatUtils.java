package com.tencent.qcloud.tuikit.deskchat.util;

import static com.tencent.qcloud.deskcore.TUIConstants.TUIConversation.CONVERSATION_C2C_PREFIX;
import static com.tencent.qcloud.deskcore.TUIConstants.TUIConversation.CONVERSATION_GROUP_PREFIX;

import android.text.TextUtils;
import android.view.View;

import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.deskcore.util.ErrorMessageConverter;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuikit.deskchat.config.TUIChatConfigs;
import com.tencent.qcloud.tuikit.deskchat.interfaces.ChatEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TUIChatUtils {

    public static <T> void callbackOnError(IUIKitCallback<T> callBack, String module, int errCode, String desc) {
        if (callBack != null) {
            callBack.onError(module, errCode, ErrorMessageConverter.convertIMError(errCode, desc));
        }
    }

    public static <T> void callbackOnError(IUIKitCallback<T> callBack, int errCode, String desc, T data) {
        if (callBack != null) {
            callBack.onError(errCode, ErrorMessageConverter.convertIMError(errCode, desc), data);
        }
    }

    public static <T> void callbackOnError(IUIKitCallback<T> callBack, int errCode, String desc) {
        if (callBack != null) {
            callBack.onError(null, errCode, ErrorMessageConverter.convertIMError(errCode, desc));
        }
    }

    public static <T> void callbackOnSuccess(IUIKitCallback<T> callBack, T data) {
        if (callBack != null) {
            callBack.onSuccess(data);
        }
    }

    public static void callbackOnProgress(IUIKitCallback callBack, Object data) {
        if (callBack != null) {
            callBack.onProgress(data);
        }
    }

    public static String getConversationIdByChatId(String id, boolean isGroup) {
        String conversationIdPrefix = isGroup ? CONVERSATION_GROUP_PREFIX : CONVERSATION_C2C_PREFIX;
        return conversationIdPrefix + id;
    }

    public static boolean isC2CChat(int chatType) {
        return chatType == V2TIMConversation.V2TIM_C2C;
    }

    public static boolean isGroupChat(int chatType) {
        return chatType == V2TIMConversation.V2TIM_GROUP;
    }

    public static boolean isCommunityGroup(String groupID) {
        if (TextUtils.isEmpty(groupID)) {
            return false;
        }

        return groupID.startsWith("@TGS#_");
    }

    public static boolean isTopicGroup(String groupID) {
        
        if (!isCommunityGroup(groupID)) {
            return false;
        }
        return groupID.contains("@TOPIC#_");
    }

    public static String getGroupIDFromTopicID(String topicID) {
        
        int index = topicID.indexOf("@TOPIC#_");
        return topicID.substring(0, index);
    }

    public static long getServerTime() {
        return V2TIMManager.getInstance().getServerTime();
    }

    public static boolean chatEventOnUserIconClicked(View view, TUIMessageBean messageBean) {
        ChatEventListener chatEventListener = TUIChatConfigs.getChatEventConfig().getChatEventListener();
        if (chatEventListener != null) {
            return chatEventListener.onUserIconClicked(view, messageBean);
        }
        return false;
    }

    public static boolean chatEventOnUserIconLongClicked(View view, TUIMessageBean messageBean) {
        ChatEventListener chatEventListener = TUIChatConfigs.getChatEventConfig().getChatEventListener();
        if (chatEventListener != null) {
            return chatEventListener.onUserIconLongClicked(view, messageBean);
        }
        return false;
    }

    public static boolean chatEventOnMessageClicked(View view, TUIMessageBean messageBean) {
        ChatEventListener chatEventListener = TUIChatConfigs.getChatEventConfig().getChatEventListener();
        if (chatEventListener != null) {
            return chatEventListener.onMessageClicked(view, messageBean);
        }
        return false;
    }

    public static boolean chatEventOnMessageLongClicked(View view, TUIMessageBean messageBean) {
        ChatEventListener chatEventListener = TUIChatConfigs.getChatEventConfig().getChatEventListener();
        if (chatEventListener != null) {
            return chatEventListener.onMessageLongClicked(view, messageBean);
        }
        return false;
    }

    public static void notifyProcessMessage(List<TUIMessageBean> messageBeans) {
        Map<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUIChat.Event.MessageStatus.MESSAGE_LIST, messageBeans);
        TUICore.notifyEvent(TUIConstants.TUIChat.Event.MessageStatus.KEY, TUIConstants.TUIChat.Event.MessageStatus.SUB_KEY_PROCESS_MESSAGE, param);
    }
}
