package com.tencent.qcloud.tuikit.deskchat;

import com.tencent.imsdk.BaseConstants;

public class TUIChatConstants {
    public static final int ERR_SVR_COMM_SENSITIVE_IMAGE = BaseConstants.ERR_SVR_COMM_SENSITIVE_IMAGE;
    public static final int ERR_SVR_COMM_SENSITIVE_TEXT = BaseConstants.ERR_SVR_COMM_SENSITIVE_TEXT;
    public static final int ERR_LOCAL_COMM_SENSITIVE_TEXT = BaseConstants.ERR_SDK_BLOCKED_BY_SENSITIVE_WORD;

    public static final String CAMERA_TYPE = "camera_type";

    public static final String BUSINESS_ID_CUSTOM_HELLO = "text_link";
    public static final String BUSINESS_ID_CUSTOM_EVALUATION = "evaluation";
    public static final String BUSINESS_ID_CUSTOM_ORDER = "order";
    public static final String BUSINESS_ID_CUSTOM_TYPING = "user_typing_status";
    public static final String BUSINESS_ID_QUICK_TAP = "quick_tap";
    public static final String FORWARD_SELECT_CONVERSATION_KEY = "forward_select_conversation_key";
    public static final String FORWARD_MERGE_MESSAGE_KEY = "forward_merge_message_key";

    public static final int GET_MESSAGE_FORWARD = 0;
    public static final int GET_MESSAGE_BACKWARD = 1;
    public static final int GET_MESSAGE_TWO_WAY = 2;
    public static final int GET_MESSAGE_LOCATE = 3;

    public static final String CHAT_INFO = "chatInfo";

    public static final String MESSAGE_BEAN = "messageBean";
    public static final String DATA_CHANGE_TYPE = "dataChangeType";

    public static final String OPEN_MESSAGE_SCAN = "open_message_scan";
    public static final String OPEN_MESSAGES_SCAN_FORWARD = "open_messages_scan_forward";

    public static final String FORWARD_MODE = "forward_mode"; // 0,onebyone;  1,merge;  2,new message;
    public static final int FORWARD_MODE_ONE_BY_ONE = 0;
    public static final int FORWARD_MODE_MERGE = 1;
    public static final int FORWARD_MODE_NEW_MESSAGE = 2;

    public static final String SELECT_FRIENDS = "select_friends";
    public static final String GROUP_ID = "group_id";
    public static final String SELECT_FOR_CALL = "isSelectForCall";

    public static final int TYPING_SEND_MESSAGE_INTERVAL = 4;
    public static final int TYPING_PARSE_MESSAGE_INTERVAL = 5;
    public static final int TYPING_TRIGGER_CHAT_TIME = 30; // second

    public static final String EVENT_KEY_MESSAGE_STATUS_CHANGED = "eventKeyMessageStatusChanged";
    public static final String EVENT_SUB_KEY_MESSAGE_SEND = "eventSubKeyMessageSend";

    public static final String EVENT_KEY_OFFLINE_MESSAGE_PRIVATE_RING = "eventKeyOfflineMessagePrivteRing";
    public static final String EVENT_SUB_KEY_OFFLINE_MESSAGE_PRIVATE_RING = "eventSubKeyOfflineMessagePrivteRing";
    public static final String OFFLINE_MESSAGE_PRIVATE_RING = "offlineMessagePrivateRing";

    /**
     *
     * 1: Just a text message with a link
     * 2: The video calling version supported by iOS is no longer compatible
     * 3: unreleased version
     * 4: Android/iOS/Web interoperable version for video call
     */
    public static final int JSON_VERSION_UNKNOWN = 0;
    public static final int JSON_VERSION_1 = 1;
    public static final int JSON_VERSION_4 = 4;
    public static int version = JSON_VERSION_4;

    public static final String CHAT_SETTINGS_SP_NAME = "chat_settings_sp";
    public static final String CHAT_SP_KEY_SPEAKER_MODE_ON = "chat_sp_key_speaker_mode_on";

    public static String covert2HTMLString(String original) {
        return "\"<font color=\"#5B6B92\">" + original + "</font>\"";
    }

    public static final class Group {
        public static final String GROUP_ID = "group_id";
    }

    public static class Selection {

        public static final String USER_ID_SELECT = "user_id_select";
        public static final String USER_NAMECARD_SELECT = "user_namecard_select";
    }

    public static class DataStore {
        public static final String DATA_STORE_NAME = "tuichat_datastore";
    }
}
