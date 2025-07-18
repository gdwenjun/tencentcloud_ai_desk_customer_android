package com.tencent.qcloud.deskcore;

import com.tencent.imsdk.BaseConstants;

/**
 * TUI constants
 */
public final class TUIConstants {
    public static final class GroupType {
        public static final String TYPE = "type";
        public static final String IS_GROUP = "isGroup";

        public static final String TYPE_PRIVATE = "Private";
        public static final String TYPE_WORK = "Work";
        public static final String TYPE_PUBLIC = "Public";

        public static final String TYPE_CHAT_ROOM = "ChatRoom";
        public static final String TYPE_MEETING = "Meeting";
        public static final String TYPE_COMMUNITY = "Community";
    }

    public static final class DeviceInfo {
        public static final int BRAND_XIAOMI = 2000;
        public static final int BRAND_HUAWEI = 2001;
        public static final int BRAND_GOOGLE_ELSE = 2002;
        public static final int BRAND_MEIZU = 2003;
        public static final int BRAND_OPPO = 2004;
        public static final int BRAND_VIVO = 2005;
        public static final int BRAND_HONOR = 2006;
    }

    public static final class Service {
        public static final String TUI_CHAT = "TUIChatService";
        public static final String TUI_CONVERSATION = "TUIConversationService";
        public static final String TUI_CONTACT = "TUIContactService";
        public static final String TUI_SEARCH = "TUISearchService";
        public static final String TUI_GROUP = "TUIGroupService";
        public static final String TUI_CALLING = "TUICallingService";
        public static final String TUI_AUDIO_RECORD = "TUIAudioMessageRecordService";
        public static final String TUI_LIVE = "TUILiveService";
        public static final String TUI_BEAUTY = "TUIBeauty";
        public static final String TUI_OFFLINEPUSH = "TUIOfflinePushService";
        public static final String TIM_PUSH_PLUGIN = "TIMPushService";
        public static final String TUI_COMMUNITY = "TUICommunityService";
        public static final String TUI_DEMO = "TIMAppService";
        public static final String TUI_POLL = "TUIPollService";
        public static final String TUI_GROUP_NOTE = "TUIGroupNoteService";
        public static final String TUI_CONVERSATION_GROUP = "TUIConversationGroupService";
        public static final String TUI_CONVERSATION_MARK = "TUIConversationMarkService";
        public static final String TUI_PRIVACY = "PrivacyService";
        public static final String USB_CAMERA = "USBCameraService";
        public static final String TUI_CUSTOMER_PLUGIN = "TUICustomerPlugin";
    }

    public static final class ObjectFactory {
        public static final String FACTORY_CONVERSATION_GROUP = "objectConversationGroup";
        public static final String FACTORY_CONVERSATION_MARK = "objectConversationMark";
    }

    public static final class TUICore {
        public static final String LANGUAGE_EVENT = "TUIThemeManager";
        public static final String LANGUAGE_EVENT_SUB_KEY = "onInitLanguage";
        public static final String LANGUAGE_CHANGED_EVENT_SUB_KEY = "onLanguageChanged";
    }

    public static final class TUILogin {
        // User login status change broadcast
        public static final String EVENT_LOGIN_STATE_CHANGED = "eventLoginStateChanged";
        // User kicked offline
        public static final String EVENT_SUB_KEY_USER_KICKED_OFFLINE = "eventSubKeyUserKickedOffline";
        // User ticket expired
        public static final String EVENT_SUB_KEY_USER_SIG_EXPIRED = "eventSubKeyUserSigExpired";
        // Changes in user personal information
        public static final String EVENT_SUB_KEY_USER_INFO_UPDATED = "eventSubKeyUserInfoUpdated";

        // Imsdk initialize state change
        public static final String EVENT_IMSDK_INIT_STATE_CHANGED = "eventIMSDKInitStateChanged";
        // Init
        public static final String EVENT_SUB_KEY_START_INIT = "eventSubKeyStartInit";
        // Uint
        public static final String EVENT_SUB_KEY_START_UNINIT = "eventSubKeyStartUnInit";
        // Login success
        public static final String EVENT_SUB_KEY_USER_LOGIN_SUCCESS = "eventSubKeyUserLoginSuccess";
        // Logout success
        public static final String EVENT_SUB_KEY_USER_LOGOUT_SUCCESS = "eventSubKeyUserLogoutSuccess";

        public static final String SELF_ID = "selfId";
        public static final String SELF_SIGNATURE = "selfSignature";
        public static final String SELF_FACE_URL = "selfFaceUrl";
        public static final String SELF_NICK_NAME = "selfNickName";
        public static final String SELF_LEVEL = "selfLevel";
        public static final String SELF_GENDER = "selfGender";
        public static final String SELF_ROLE = "selfRole";
        public static final String SELF_BIRTHDAY = "selfBirthday";
        public static final String SELF_ALLOW_TYPE = "selfAllowType";
    }

    public static final class TUIChat {
        public static final String SERVICE_NAME = TUIConstants.Service.TUI_CHAT;

        // Send message
        public static final String METHOD_SEND_MESSAGE = "sendMessage";
        // Exit chat
        public static final String METHOD_EXIT_CHAT = "exitChat";
        // Get a message digest to display in the conversation list
        public static final String METHOD_GET_DISPLAY_STRING = "getDisplayString";
        // add message to chat list
        public static final String METHOD_ADD_MESSAGE_TO_CHAT = "addMessageToChat";
        // Set chat extension
        public static final String METHOD_SET_CHAT_EXTENSION = "setChatExtension";

        // More actions
        public static final String EXTENSION_INPUT_MORE_VIDEO_CALL = "inputMoreVideoCall";
        public static final String EXTENSION_INPUT_MORE_AUDIO_CALL = "inputMoreAudioCall";
        public static final String EXTENSION_INPUT_MORE_GROUP_NOTE = "inputMoreGroupNote";
        public static final String EXTENSION_INPUT_MORE_POLL = "inputMoreGroupPoll";

        // Message actions
        public static final String EVENT_KEY_RECEIVE_MESSAGE = "eventReceiveMessage";
        public static final String EVENT_SUB_KEY_CONVERSATION_ID = "eventConversationID";

        public static final String EVENT_KEY_INPUT_MORE = "eventKeyInputMore";
        public static final String EVENT_SUB_KEY_ON_CLICK = "eventSubKeyOnClick";

        public static final String EVENT_KEY_MESSAGE_EVENT = "eventKeyMessageEvent";
        public static final String EVENT_SUB_KEY_SEND_MESSAGE_SUCCESS = "eventSubKeySendMessageSuccess";
        public static final String EVENT_SUB_KEY_SEND_MESSAGE_FAILED = "eventSubKeySendMessageFailed";
        public static final String EVENT_SUB_KEY_REPLY_MESSAGE_SUCCESS = "eventSubKeyReplyMessageSuccess";
        public static final String EVENT_SUB_KEY_DISPLAY_MESSAGE_BEAN = "eventSubKeyDisplayMessageBean";
        public static final String EVENT_SUB_KEY_MESSAGE_BOTTOM_CHANGED = "eventSubKeyMessageBottomChanged";
        public static final String EVENT_SUB_KEY_MESSAGE_INFO_CHANGED = "eventSubKeyMessageInfoChanged";

        public static final String C2C_CHAT_ACTIVITY_NAME = "TUIC2CChatActivity";

        public static final String GROUP_CHAT_ACTIVITY_NAME = "TUIGroupChatActivity";
        public static final String CHAT_ID = "chatId";
        public static final String CHAT_NAME = "chatName";
        public static final String CHAT_TYPE = "chatType";
        public static final String GROUP_NAME = "groupName";
        public static final String GROUP_TYPE = "groupType";
        public static final String DRAFT_TEXT = "draftText";
        public static final String DRAFT_TIME = "draftTime";
        public static final String IS_TOP_CHAT = "isTopChat";
        public static final String LOCATE_MESSAGE = "locateMessage";
        public static final String AT_INFO_LIST = "atInfoList";
        public static final String FACE_URL = "faceUrl";
        public static final String FACE_URL_LIST = "faceUrlList";
        public static final String JOIN_TYPE = "joinType";
        public static final String MEMBER_COUNT = "memberCount";
        public static final String RECEIVE_OPTION = "receiveOption";
        public static final String NOTICE = "notice";
        public static final String OWNER = "owner";
        public static final String MEMBER_DETAILS = "memberDetails";
        public static final String IS_GROUP_CHAT = "isGroupChat";
        public static final String V2TIMMESSAGE = "v2TIMMessage";
        public static final String MESSAGE_BEAN = "messageBean";
        public static final String GROUP_APPLY_NUM = "groupApplicaitonNumber";
        public static final String CONVERSATION_ID = "conversationID";
        public static final String IS_TYPING_MESSAGE = "isTypingMessage";
        public static final String CALL_BACK = "callback";
        public static final String PLUGIN_ITEM_VIEW = "pluginItemView";
        public static final String PLUGIN_BEAN_OBJECT = "pluginBeanObject";

        // Send custom message fields
        public static final String MESSAGE_CONTENT = "messageContent";
        public static final String MESSAGE_DESCRIPTION = "messageDescription";
        public static final String MESSAGE_EXTENSION = "messageExtension";

        // More input button extension field
        public static final String CONTEXT = "context";
        public static final String INPUT_MORE_ICON = "icon";
        public static final String INPUT_MORE_TITLE = "title";
        public static final String INPUT_MORE_ACTION_ID = "actionId";
        public static final String INPUT_MORE_VIEW = "inputMoreView";

        // Background
        public static final String CHAT_CONVERSATION_BACKGROUND_URL =
            "https://im.sdk.qcloud.com/download/tuikit-resource/conversation-backgroundImage/backgroundImage_%s_full.png";
        public static final String CHAT_CONVERSATION_BACKGROUND_THUMBNAIL_URL =
            "https://im.sdk.qcloud.com/download/tuikit-resource/conversation-backgroundImage/backgroundImage_%s.png";
        public static final int CHAT_CONVERSATION_BACKGROUND_COUNT = 7;
        public static final String CHAT_CONVERSATION_BACKGROUND_DEFAULT_URL = "chat/conversation/background/default/url";
        public static final int CHAT_REQUEST_BACKGROUND_CODE = 1001;
        public static final String METHOD_UPDATE_DATA_STORE_CHAT_URI = "updateDatastoreChatUri";
        public static final String CHAT_BACKGROUND_URI = "chatBackgroundUri";

        // Chat plugin
        public static final String ENABLE_VIDEO_CALL = "enableVideoCall";
        public static final String ENABLE_AUDIO_CALL = "enableAudioCall";
        public static final String ENABLE_LINK = "enableLink";
        public static final String ENABLE_ROOM = "enableRoom";

        // Translation
        public static final String CHAT_RECYCLER_VIEW = "chatRecyclerView";
        public static final String THEME_STYLE_CLASSIC = "classicTheme";
        public static final String THEME_STYLE_MINIMALIST = "minimalistTheme";
        public static final String FRAGMENT = "fragment";
        public static final String ACTIVITY = "activity";

        public static class Extension {
            // UI extension for the input area at the bottom of the chat page
            public static class InputMore {
                public static final String CLASSIC_EXTENSION_ID = "ChatInputMoreExtensionClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ChatInputMoreExtensionMinimalistID";
                public static final String FILTER_VOICE_CALL = "ChatInputMoreExtensionFilterAudioCall";
                public static final String FILTER_VIDEO_CALL = "ChatInputMoreExtensionFilterVideoCall";
                public static final String FILTER_ROOM = "ChatInputMoreExtensionFilterRoom";
                public static final String FILTER_POLL = "ChatInputMoreExtensionFilterPoll";
                public static final String FILTER_GROUP_NOTE = "ChatInputMoreExtensionFilterGroupNote";
                public static final String CONTEXT = "ChatContext";
                public static final String USER_ID = "ChatInputMoreUserID";
                public static final String GROUP_ID = "ChatInputMoreGroupID";
                public static final String INPUT_MORE_LISTENER = "ChatInputMoreListener";
            }

            // Message long press pop-up window extension
            public static class MessagePopMenu {
                public static final String CLASSIC_EXTENSION_ID = "ChatMessagePopMenuExtensionClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ChatMessagePopMenuExtensionMinimalistID";
                public static final String MESSAGE_BEAN = "ChatMessageBean";
                public static final String ON_POP_CLICK_LISTENER = "ChatOnPopClickListener";
            }

            // UI extension on the right side of navigation bar in chat page
            public static class ChatNavigationMoreItem {
                public static final String CLASSIC_EXTENSION_ID = "ChatNavigationMoreItemExtensionClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ChatNavigationMoreItemExtensionMinimalistID";

                public static final String FILTER_VOICE_CALL = "ChatNavigationMoreItemFilterAudioCall";
                public static final String FILTER_VIDEO_CALL = "ChatNavigationMoreItemFilterVideoCall";

                public static final String CHAT_BACKGROUND_URI = "ChatBackgroundUri";
                public static final String USER_ID = "ChatUserID";
                public static final String GROUP_ID = "ChatGroupID";
                public static final String TOPIC_ID = "ChatTopicID";
                public static final String CONTEXT = "ChatContext";
                public static final String GROUP_TYPE = "ChatGroupType";
            }

            // Message content at bottom
            public static class MessageBottom {
                public static final String CLASSIC_EXTENSION_ID = "ChatMessageBottomExtensionClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ChatMessageBottomExtensionMinimalistID";
            }

            public static class ChatUserIconClickedProcessor {
                public static final String CLASSIC_EXTENSION_ID = "ChatUserIconClickedProcessorExtensionClassicID";
                public static final String USER_ID = "ChatUserID";
            }

            public static class InputViewFloatLayer {
                public static final String CLASSIC_EXTENSION_ID = "InputViewFloatLayerExtensionClassicID";
            }

            public static class MultiSelectMessageBar {
                public static final String CLASSIC_EXTENSION_ID = "MultiSelectMessageBarExtensionClassicID";
                public static final String USER_ID = "ChatUserID";
                public static final String ENABLE_FORWARD_MESSAGE = "EnableForwardMessage";
            }

            public static class ChatView {
                public static final String GET_CONFIG_PARAMS = "GetConfigParams";
                public static final String MESSAGE_NEED_READ_RECEIPT = "MessageNeedReadReceipt";
                public static final String ENABLE_VIDEO_CALL = "EnableVideoCall";
                public static final String ENABLE_AUDIO_CALL = "EnableAudioCall";
                public static final String ENABLE_CUSTOM_HELLO_MESSAGE = "EnableCustomHelloMessage";
            }

            public static final class MessagePopMenuTopAreaExtension {
                public static final String EXTENSION_ID = "TUIChatPopMenuTopAreaExtension";
                public static final String VIEW_TYPE = "TUIChatExtensionChatPopViewType";
                public static final int VIEW_TYPE_CLASSIC = 0;
                public static final int VIEW_TYPE_MINIMALIST = 1;
                public static final String CHAT_POP_MENU = "TUIChatExtensionPopMenu";
                public static final String MESSAGE = "TUIChatExtensionMessage";
            }

            public static final class MessageReactPreviewExtension {
                public static final String EXTENSION_ID = "TUIChatExtensionMessageReactPreview";
                public static final String VIEW_TYPE = "TUIChatExtensionPopViewType";
                public static final int VIEW_TYPE_CLASSIC = 0;
                public static final int VIEW_TYPE_MINIMALIST = 1;
                public static final String CHAT_POP_MENU = "TUIChatExtensionPopMenu";
                public static final String MESSAGE = "TUIChatExtensionPopMessage";
            }

            public static final class ChatViewTopAreaExtension {
                public static final String EXTENSION_ID = "TUIChatTopAreaExtension";
                public static final String VIEW_TYPE = "TUIChatExtensionViewType";
                public static final int VIEW_TYPE_CLASSIC = 0;
                public static final int VIEW_TYPE_MINIMALIST = 1;
                public static final String CHAT_ID = "TUIChatID";
                public static final String IS_GROUP = "TUIChatIsGroup";
            }
        }

        public static class Method {
            // Register a message type with Chat
            public static class RegisterCustomMessage {
                public static final String CLASSIC_SERVICE_NAME = TUIChat.Service.CLASSIC_SERVICE_NAME;
                public static final String MINIMALIST_SERVICE_NAME = TUIChat.Service.MINIMALIST_SERVICE_NAME;
                public static final String METHOD_NAME = "ChatRegisterCustomMessageName";
                public static final String MESSAGE_BUSINESS_ID = "ChatMessageBusinessID";
                public static final String MESSAGE_BEAN_CLASS = "ChatMessageBeanClass";
                public static final String MESSAGE_VIEW_HOLDER_CLASS = "ChatMessageViewHolderClass";
                public static final String MESSAGE_REPLY_BEAN_CLASS = "ChatMessageReplyBeanClass";
                public static final String MESSAGE_REPLY_VIEW_CLASS = "ChatMessageReplyViewClass";
                public static final String IS_NEED_EMPTY_VIEW_GROUP = "ChatMessageIsNeedEmptyViewGroup";
            }

            // Get message display string
            public static class GetMessagesDisplayString {
                public static final String METHOD_NAME = "GetLastMsgDisplayString";
                public static final String MESSAGE_MAP = "messageMap";
                public static final String DISPLAY_STRING_MAP = "displayStringMap";
                public static final String CONVERSATION_ID = "conversationID";
                public static final String MESSAGE = "message";
            }

            // Get message display string
            public static class GetTUIMessageBean {
                public static final String METHOD_NAME = "GetTUIMessageBean";
                public static final String V2TIM_MESSAGE = "v2TIMMessage";
            }
        }

        public static class ObjectFactory {
            public static final String OBJECT_FACTORY_NAME = "ChatObjectFactory";

            public static class ChatFragment {
                public static final String OBJECT_NAME = "ChatFragment";
                public static final int CHAT_TYPE_C2C = 0;
                public static final int CHAT_TYPE_GROUP = 1;
                public static final String CHAT_ID = "chatId";
                public static final String CHAT_TITLE = "chatTitle";
                public static final String CHAT_TYPE = "chatType";
                public static final String ENABLE_ROOM = "enableRoom";
                public static final String ENABLE_AUDIO_CALL = "enableAudioCall";
                public static final String ENABLE_VIDEO_CALL = "enableVideoCall";
                public static final String ENABLE_CUSTOM_HELLO_MESSAGE = "enableCustomHelloMessage";
                public static final String ENABLE_POLL = "enablePoll";
                public static final String ENABLE_GROUP_NOTE = "enableGroupNote";
                public static final String ENABLE_ALBUM = "enableAlbum";
                public static final String ENABLE_FILE = "enableFile";
                public static final String ENABLE_RECORD_VIDEO = "enableRecordVideo";
                public static final String ENABLE_TAKE_PHOTO = "enableTakePhoto";
            }
        }

        public static class Service {
            public static final String CHAT_SERVICE_NAME = TUIConstants.Service.TUI_CHAT;
            public static final String CLASSIC_SERVICE_NAME = "ChatClassicService";
            public static final String MINIMALIST_SERVICE_NAME = "ChatMinimalistService";
        }

        public static class Event {
            public static class MessageStatus {
                public static final String KEY = "ChatMessageStatusEvent";
                public static final String SUB_KEY_PROCESS_MESSAGE = "ChatMessageStatusEventSubKeyProcessMessage";
                public static final String MESSAGE_LIST = "ChatMessageStatusEventMessageList";
            }

            public static class MessageDisplayString {
                public static final String KEY = "ChatMessageDisplayString";
                public static final String SUB_KEY_PROCESS_MESSAGE = "ChatMessageDisplayStringUpdate";
                public static final String CONVERSATION_ID = "ChatMessageDisplayConversationID";
                public static final String MESSAGE_BEAN = "ChatMessageDisplayStringMessageBean";
            }
        }
    }

    public static final class TUIConversation {
        public static final String SERVICE_NAME = Service.TUI_CONVERSATION;

        public static final String METHOD_IS_TOP_CONVERSATION = "isTopConversation";
        public static final String METHOD_SET_TOP_CONVERSATION = "setTopConversation";
        public static final String METHOD_GET_TOTAL_UNREAD_COUNT = "getTotalUnreadCount";
        public static final String METHOD_UPDATE_TOTAL_UNREAD_COUNT = "updateTotalUnreadCount";
        public static final String METHOD_DELETE_CONVERSATION = "deleteConversation";
        public static final String METHOD_CLEAR_CONVERSATION_MESSAGE = "clearConversationMessage";

        public static final String EVENT_UNREAD = "eventTotalUnreadCount";
        public static final String EVENT_SUB_KEY_UNREAD_CHANGED = "unreadCountChanged";

        public static final String EXTENSION_CLASSIC_SEARCH = "extensionClassicSearch";
        public static final String EXTENSION_MINIMALIST_SEARCH = "extensionMinimalistSearch";

        public static final String CHAT_ID = "chatId";
        public static final String CONVERSATION_ID = "conversationId";
        public static final String IS_SET_TOP = "isSetTop";
        public static final String IS_TOP = "isTop";
        public static final String IS_GROUP = "isGroup";
        public static final String TOTAL_UNREAD_COUNT = "totalUnreadCount";
        public static final String CONTEXT = "context";
        public static final String SEARCH_VIEW = "searchView";
        public static final String KEY_CONVERSATION_INFO = "keyConversationInfo";

        public static final String CONVERSATION_C2C_PREFIX = "c2c_";
        public static final String CONVERSATION_GROUP_PREFIX = "group_";

        public static final String EVENT_KEY_MESSAGE_SEND_FOR_CONVERSATION = "eventKeyMessageSendForConversation";
        public static final String EVENT_SUB_KEY_MESSAGE_SEND_FOR_CONVERSATION = "eventSubKeyMessageSendForConversation";

        public static class Extension {
            // Conversation List Header Extension
            public static class ConversationListHeader {
                public static final String CLASSIC_EXTENSION_ID = "TUIConversationListHeaderClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "TUIConversationListHeaderMinimalistID";
                public static final String HEADER_CONTAINER = "TUIConversationListHeaderContainer";
            }

            // Conversation popmenu Extension
            public static class ConversationPopMenu {
                public static final String CLASSIC_EXTENSION_ID = "ConversationPopMenuExtensionClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ConversationPopMenuExtensionMinimalistID";
            }

            // Conversation group Extension
            public static class ConversationGroupBean {
                public static final String CLASSIC_EXTENSION_ID = "ConversationGroupBeanExtensionClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ConversationGroupBeanExtensionMinimalistID";
                public static final String KEY_DATA = "conversationGroupBeanData";
            }

            public static class ConversationMarkBean {
                public static final String CLASSIC_EXTENSION_ID = "ConversationMarkBeanExtensionClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ConversationMarkBeanExtensionMinimalistID";
                public static final String KEY_DATA = "conversationMarkBeanData";
            }
        }
    }

    public static final class TUIContact {
        public static final String SERVICE_NAME = Service.TUI_CONTACT;

        public static final String EVENT_FRIEND_STATE_CHANGED = "eventFriendStateChanged";
        public static final String EVENT_FRIEND_INFO_CHANGED = "eventFriendInfoChanged";
        public static final String EVENT_USER = "eventUser";
        public static final String EVENT_SUB_KEY_FRIEND_REMARK_CHANGED = "eventFriendRemarkChanged";
        public static final String EVENT_SUB_KEY_FRIEND_DELETE = "eventSubKeyFriendDelete";
        public static final String EVENT_SUB_KEY_CLEAR_MESSAGE = "eventSubKeyC2CClearMessage";

        public static final String FRIEND_ID_LIST = "friendIdList";
        public static final String FRIEND_ID = "friendId";
        public static final String USER_ID = "userID";
        public static final String FRIEND_REMARK = "friendRemark";

        public static final String GROUP_TYPE_KEY = "type";
        public static final String COMMUNITY_SUPPORT_TOPIC_KEY = "communitySupportTopic";
        public static final String CONTEXT = "context";

        public static final int GROUP_TYPE_PRIVATE = 0;
        public static final int GROUP_TYPE_PUBLIC = 1;
        public static final int GROUP_TYPE_CHAT_ROOM = 2;
        public static final int GROUP_TYPE_COMMUNITY = 3;

        public static final String GROUP_FACE_URL = "https://im.sdk.qcloud.com/download/tuikit-resource/group-avatar/group_avatar_%s.png";
        public static final int GROUP_FACE_COUNT = 24;

        public static class Extension {
            public static class FriendProfileItem {
                public static final String CLASSIC_EXTENSION_ID = "ContactFriendProfileItemClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ContactFriendProfileItemMinimalistID";
                public static final String USER_ID = "ContactFriendProfileUserID";
            }

            public static class FriendProfileWarningButton {
                public static final String EXTENSION_ID = "ContactFriendProfileWarningButtonExtensionID";
            }

            public static class ContactItem {
                public static final String CLASSIC_EXTENSION_ID = "ContactItemClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ContactItemMinimalistID";
            }
        }

        public static class StartActivity {
            // Group members selector
            public static class GroupMemberSelect {
                public static final String CLASSIC_ACTIVITY_NAME = "StartGroupMemberSelectActivity";
                public static final String MINIMALIST_ACTIVITY_NAME = "StartGroupMemberSelectMinimalistActivity";
                public static final String GROUP_ID = "ContactGroupMemberSelectGroupID";
                public static final String MEMBER_LIMIT = "ContactGroupMemberSelectMemberLimit";
                public static final String SELECT_FOR_CALL = "ContactGroupMemberSelectForCall";
                public static final String SELECTED_LIST = "ContactGroupMemberSelectedList";
                public static final String SELECT_FRIENDS = "ContactGroupMemberSelectFriends";
                public static final String DATA_LIST = "ContactGroupMemberSelectDataList";
                public static final String PAGE_TITLE = "ContactGroupMemberSelectTitle";
                public static final String USER_NAME_CARD_SELECT = "ContactGroupMemberSelectNameCardSelected";
                public static final String USER_ID_SELECT = "ContactGroupMemberSelectUserIDSelected";
            }
        }
    }

    public static final class TUICalling {
        public static final String SERVICE_NAME = Service.TUI_CALLING;

        public static final String METHOD_NAME_CALL = "call";
        public static final String METHOD_NAME_RECEIVEAPNSCALLED = "receiveAPNSCalled";
        public static final String METHOD_NAME_ENABLE_FLOAT_WINDOW = "methodEnableFloatWindow";
        public static final String METHOD_NAME_ENABLE_MULTI_DEVICE = "methodEnableMultiDeviceAbility";
        public static final String METHOD_NAME_ENABLE_INCOMING_BANNER = "methodEnableIncomingBanner";
        public static final String METHOD_NAME_ENABLE_VIRTUAL_BACKGROUND = "methodEnableVirtualBackground";

        public static final String PARAM_NAME_TYPE = "type";
        public static final String PARAM_NAME_USERIDS = "userIDs";
        public static final String PARAM_NAME_GROUPID = "groupId";
        public static final String PARAM_NAME_CALLMODEL = "call_model_data";
        public static final String PARAM_NAME_ENABLE_FLOAT_WINDOW = "enableFloatWindow";
        public static final String PARAM_NAME_ENABLE_MULTI_DEVICE = "enableMultiDeviceAbility";
        public static final String PARAM_NAME_ENABLE_INCOMING_BANNER = "enableIncomingBanner";
        public static final String PARAM_NAME_ENABLE_VIRTUAL_BACKGROUND = "enableVirtualBackground";

        public static final String METHOD_START_CALL = "startCall";

        public static final String CUSTOM_MESSAGE_BUSINESS_ID = "av_call";
        public static final Double CALL_TIMEOUT_BUSINESS_ID = 1.0;

        public static final String CALL_ID = "callId";
        public static final String SENDER = "sender";
        public static final String GROUP_ID = "groupId";
        public static final String INVITED_LIST = "invitedList";
        public static final String DATA = "data";

        public static final String TYPE_AUDIO = "audio";
        public static final String TYPE_VIDEO = "video";

        public static final int ACTION_ID_AUDIO_CALL = 1;
        public static final int ACTION_ID_VIDEO_CALL = 2;

        public static final String EVENT_KEY_CALLING = "calling";
        public static final String EVENT_KEY_NAME = "event_name";
        public static final String EVENT_ACTIVE_HANGUP = "active_hangup";

        public static final String SERVICE_NAME_AUDIO_RECORD = Service.TUI_AUDIO_RECORD;
        public static final String METHOD_NAME_START_RECORD_AUDIO_MESSAGE = "methodStartRecordAudioMessage";
        public static final String METHOD_NAME_STOP_RECORD_AUDIO_MESSAGE = "methodStopRecordAudioMessage";

        public static final String EVENT_KEY_RECORD_AUDIO_MESSAGE = "eventRecordAudioMessage";
        public static final String EVENT_SUB_KEY_RECORD_START = "eventSubKeyStartRecordAudioMessage";
        public static final String EVENT_SUB_KEY_RECORD_STOP = "eventSubKeyStopRecordAudioMessage";

        public static final String PARAM_NAME_SDK_APP_ID = "sdkappid";
        public static final String PARAM_NAME_AUDIO_SIGNATURE = "signature";
        public static final String PARAM_NAME_AUDIO_PATH = "path";

        // Error Code
        public static final int ERROR_NONE = 0; // init success or record success
        public static final int ERROR_INVALID_PARAM = -1001; // param is invalid
        public static final int ERROR_STATUS_IN_CALL = -1002; // recording rejected, currently in call
        public static final int ERROR_STATUS_IS_AUDIO_RECORDING = -1003; // recording rejected, the current recording is not finished
        public static final int ERROR_MIC_PERMISSION_REFUSED = -1004; // recording rejected, failed to obtain microphone permission
        public static final int ERROR_REQUEST_AUDIO_FOCUS_FAILED = -1005; // recording rejected, failed to obtain audio focus
        public static final int ERROR_RECORD_INIT_FAILED = -2001; // -1, init failed(onLocalRecordBegin)
        public static final int ERROR_PATH_FORMAT_NOT_SUPPORT = -2002; // -2, file format is invalid(onLocalRecordBegin)
        public static final int ERROR_RECORD_FAILED = -2003; // -1, record failed(onLocalRecordComplete)
        public static final int ERROR_NO_MESSAGE_TO_RECORD = -2004; // -3, The audio data has not arrived(onLocalRecordComplete)
        public static final int ERROR_SIGNATURE_ERROR = -3001; // -4, signature error(onLocalRecordBegin)
        public static final int ERROR_SIGNATURE_EXPIRED = -3002; // -5, signature expired(onLocalRecordBegin)
        // TRTC-SDK MIC Error Code
        public static final int ERR_MIC_START_FAIL = -1302; // start microphone failed
        public static final int ERR_MIC_NOT_AUTHORIZED = -1317; // microphone authorize failed
        public static final int ERR_MIC_SET_PARAM_FAIL = -1318; // microphone param is invalid
        public static final int ERR_MIC_OCCUPY = -1319; // microphone is occupied

        public static class ObjectFactory {
            public static final String FACTORY_NAME = "TUICallingObjectFactory";

            public static class RecentCalls {
                public static final String OBJECT_NAME = "TUICallingRecentCallsFragment";
                public static final String UI_STYLE = "TUICallingRecentCallsFragmentUIStyle";
                public static final String UI_STYLE_CLASSIC = "ClassicStyle";
                public static final String UI_STYLE_MINIMALIST = "MinimalistStyle";
            }
        }
    }

    public static final class USBCamera {
        public static final String SERVICE_NAME         = Service.USB_CAMERA;
        public static final String KEY_USB_CAMERA       = "keyUSBCamera";
        public static final String SUB_KEY_OPEN_CAMERA  = "eventSubOpenUSBCamera";
        public static final String SUB_KEY_CLOSE_CAMERA = "eventSubCloseUSBCamera";
        public static final String PARAM_TX_CLOUD_VIEW  = "TXCloudView";
    }

    public static final class TUILive {
        public static final String SERVICE_NAME = Service.TUI_LIVE;

        public static final String METHOD_LOGIN = "methodLogin";
        public static final String METHOD_LOGOUT = "methodLogout";
        public static final String METHOD_START_ANCHOR = "methodStartAnchor";
        public static final String METHOD_START_AUDIENCE = "methodStartAudience";

        public static final String CUSTOM_MESSAGE_BUSINESS_ID = "group_live";
        public static final String SDK_APP_ID = "sdkAppId";
        public static final String USER_ID = "userId";
        public static final String USER_SIG = "userSig";

        public static final String GROUP_ID = "groupId";
        public static final String ROOM_ID = "roomId";
        public static final String ROOM_NAME = "roomName";
        public static final String ROOM_STATUS = "roomStatus";
        public static final String ROOM_COVER = "roomCover";
        public static final String USE_CDN_PLAY = "use_cdn_play";
        public static final String ANCHOR_ID = "anchorId";
        public static final String ANCHOR_NAME = "anchorName";
        public static final String PUSHER_NAME = "pusherName";
        public static final String COVER_PIC = "coverPic";
        public static final String PUSHER_AVATAR = "pusherAvatar";

        public static final int ACTION_ID_LIVE = 0;
    }

    public static final class TUIGroup {
        public static final String SERVICE_NAME = Service.TUI_GROUP;

        public static final String EVENT_GROUP = "eventGroup";
        public static final String EVENT_SUB_KEY_EXIT_GROUP = "eventExitGroup";
        public static final String EVENT_SUB_KEY_MEMBER_KICKED_GROUP = "eventMemberKickedGroup";
        public static final String EVENT_SUB_KEY_GROUP_RECYCLE = "eventMemberGroupRecycle";
        public static final String EVENT_SUB_KEY_GROUP_DISMISS = "eventMemberGroupDismiss";
        public static final String EVENT_SUB_KEY_JOIN_GROUP = "eventSubKeyJoinGroup";
        public static final String EVENT_SUB_KEY_INVITED_GROUP = "eventSubKeyInvitedGroup";
        public static final String EVENT_SUB_KEY_GROUP_INFO_CHANGED = "eventSubKeyGroupInfoChanged";
        public static final String EVENT_SUB_KEY_CLEAR_MESSAGE = "eventSubKeyGroupClearMessage";
        public static final String EVENT_SUB_KEY_GROUP_MEMBER_SELECTED = "eventSubKeyGroupMemberSelected";

        public static final String GROUP_ID = "groupId";
        public static final String GROUP_NAME = "groupName";
        public static final String GROUP_FACE_URL = "groupFaceUrl";
        public static final String GROUP_OWNER = "groupOwner";
        public static final String GROUP_INTRODUCTION = "groupIntroduction";
        public static final String GROUP_NOTIFICATION = "groupNotification";
        public static final String GROUP_MEMBER_ID_LIST = "groupMemberIdList";

        public static final String SELECT_FRIENDS = "select_friends";
        public static final String SELECT_FOR_CALL = "isSelectForCall";
        public static final String USER_DATA = "userData";
        public static final String IS_SELECT_MODE = "isSelectMode";
        public static final String EXCLUDE_LIST = "excludeList";
        public static final String SELECTED_LIST = "selectedList";
        public static final String CONTENT = "content";
        public static final String TYPE = "type";
        public static final String TITLE = "title";
        public static final String LIST = "list";
        public static final String LIMIT = "limit";
        public static final String FILTER = "filter";
        public static final String JOIN_TYPE_INDEX = "joinTypeIndex";

        public static class Extension {
            public static class GroupProfileItem {
                public static final String CLASSIC_EXTENSION_ID = "GroupProfileItemClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "GroupProfileItemMinimalistID";
                public static final String GROUP_ID = "GroupProfileGroupID";
                public static final String CONTEXT = "GroupProfileContext";
            }

            public static class GroupProfileWarningButton {
                public static final String EXTENSION_ID = "GroupProfileWarningButtonExtensionID";
            }
        }

        public static class Event {
            public static class GroupApplication {
                public static final String KEY_GROUP_APPLICATION = "groupApplication";
                public static final String SUB_KEY_GROUP_APPLICATION_NUM_CHANGED = "groupApplicationNumChanged";
            }
        }
    }

    public static final class TUIBeauty {
        public static final String SERVICE_NAME = Service.TUI_BEAUTY;

        public static final String PARAM_NAME_CONTEXT = "context";
        public static final String PARAM_NAME_LICENSE_KEY = "licenseKey";
        public static final String PARAM_NAME_LICENSE_URL = "licenseUrl";
        public static final String PARAM_NAME_FRAME_WIDTH = "frameWidth";
        public static final String PARAM_NAME_FRAME_HEIGHT = "frameHeight";
        public static final String PARAM_NAME_SRC_TEXTURE_ID = "srcTextureId";

        public static final String METHOD_PROCESS_VIDEO_FRAME = "processVideoFrame";
        public static final String METHOD_INIT_XMAGIC = "setLicense";
        public static final String METHOD_DESTROY_XMAGIC = "destroy";
    }

    public static final class TUIOfflinePush {
        public static final String SERVICE_NAME = Service.TUI_OFFLINEPUSH;
        public static final String METHOD_UNREGISTER_PUSH = "unRegiterPush";

        public static final String EVENT_NOTIFY = "offlinePushNotifyEvent";
        public static final String EVENT_NOTIFY_NOTIFICATION = "notifyNotificationEvent";
        public static final String NOTIFICATION_INTENT_KEY = "notificationIntentKey";
        public static final String NOTIFICATION_EXT_KEY = "ext";

        public static final String NOTIFICATION_BROADCAST_ACTION = "com.tencent.tuiofflinepush.BROADCAST_PUSH_RECEIVER";
    }

    public static final class TIMPush {
        public static final String SERVICE_NAME = Service.TIM_PUSH_PLUGIN;
        public static final String CONTEXT = "context";

        public static final String METHOD_REGISTER_PUSH = "registerTIMPush";
        public static final String METHOD_REGISTER_PUSH_WITH_JSON = "registerTIMPushWithJsonParam";
        public static final String REGISTER_PUSH_WITH_JSON_KEY = "registerTIMPushwithJsonParamkey";
        public static final String METHOD_UNREGISTER_PUSH = "unRegiterTIMPush";
        public static final String METHOD_GET_PUSH_TOKEN = "getPushToken";
        public static final String METHOD_SET_PUSH_TOKEN = "setPushToken";
        public static final String METHOD_PUSH_BUSSINESS_ID_KEY = "pushBussinessIdKey";
        public static final String METHOD_PUSH_TOKEN_KEY = "pushTokenKey";

        public static final String METHOD_SET_PUSH_BRAND_ID = "setTIMPushBrandId";
        public static final String METHOD_GET_PUSH_BRAND_ID = "getTIMPushBrandId";
        public static final String PUSH_BRAND_ID_KEY = "TIMPushBrandIdKey";
        public static final String METHOD_CONFIG_FCM_PRIVATE_RING = "configTIMPushFCMPrivateRing";
        public static final String CONFIG_FCM_CHANNEL_ID_KEY = "configTIMPushFCMChannelIdKey";
        public static final String CONFIG_FCM_PRIVATE_RING_NAME_KEY = "configTIMPushFCMPrivateRingNameKey";
        public static final String CONFIG_ENABLE_FCM_PRIVATE_RING_KEY = "configEnableTIMPushFCMPrivateRingKey";
        public static final String METHOD_CUSTOM_TIMPUSH_CONFINGS = "customTIMPushConfigs";
        public static final String CUSTOM_TIMPUSH_CONFINGS_KEY = "customTIMPushConfigsKey";

        public static final String METHOD_CHECK_PUSH_STATUS = "checkTIMPushStatus";
        public static final String CHECK_PUSH_STATUS_RESULT_LEY = "checkTIMPushStatusResultKey";

        public static final String METHOD_DISABLE_AUTO_REGISTER_PUSH = "disableAutoRegisterPush";
        public static final String DISABLE_AUTO_REGISTER_PUSH_KEY = "disableAutoRegisterPushKey";
        public static final String EVENT_NOTIFY = "TIMPushNotifyEvent";
        public static final String EVENT_NOTIFY_NOTIFICATION = "TIMPushNotifyEvent";
        public static final String NOTIFICATION_INTENT_KEY = "TIMPushNotifyIntentKey";
        public static final String NOTIFICATION_EXT_KEY = "ext";
        public static final String NOTIFICATION_BROADCAST_ACTION = "com.tencent.TIMPush.BROADCAST_PUSH_RECEIVER";

        public static final String METHOD_XIAOMI_CLICK_INTENT = "xiaomiClickIntent";
        public static final String XIAOMI_CLICK_INTENT_KEY = "xiaomiClickIntentKey";

        public static final String METHOD_SET_PUSH_MESSAGE_DATA = "setPushMessageData";
        public static final String PUSH_MESSAGE_DATA_KEY = "pushMessageDataKey";

        public static final String NOTIFICATION_UI_ID = "notificationUIId";
        public static final String NOTIFICATION_CREATED_BY_IM_KEY = "notificationCreatedByIMKey";
        public static final String EVENT_IM_LOGIN_AFTER_APP_WAKEUP_KEY = "eventIMloginAfterAppWakeUpKey";
        public static final String EVENT_IM_LOGIN_AFTER_APP_WAKEUP_SUB_KEY = "eventIMloginAfterAppWakeUpSubKey";
        public static final String EVENT_IM_LOGIN_AFTER_APP_WAKEUP_PUSH_MESSAGE_KEY = "eventIMloginAfterAppWakeUpPushMessageKey";
        public static final String BROADCAST_IM_LOGIN_AFTER_APP_WAKEUP = "com.tencent.TIMPush.BROADCAST_IM_LOGIN_AFTER_APP_WAKEUP";
        public static final String METHOD_REPORT_NOTIFICATION_RECIEVED = "reportNotificationRecieved";
        public static final String METHOD_REPORT_NOTIFICATION_CLICKED = "reportNotificationClicked";
        public static final String METHOD_SET_CUSTOM_CONFIG_JSON = "setCustomConfigJson";
        public static final String SET_CUSTOM_CONFIG_JSON_KEY = "setCustomConfigJsonKey";
        public static final String METHOD_REGISTER_TPUSH = "registerTPush";
        public static final String METHOD_SET_REGISTRATION_ID = "setRegistrationID";
        public static final String METHOD_GET_REGISTRATION_ID = "getRegistrationID";
        public static final String PARAM_SDK_APPID_KEY = "paramSdkAppId";
        public static final String PARAM_REGISTRATION_ID_KEY = "paramRegistrationID";
        public static final String PARAM_APP_SECRET_KEY = "paramAppSecret";
        public static final String METHOD_SET_CUSTOM_CONFIG_FILE = "setCustomConfigFile";
        public static final String SET_CUSTOM_CONFIG_FILE_KEY = "setCustomConfigFileKey";
        public static final String METHOD_SET_CUSTOM_FCM_RING = "setCustomFCMRing";
        public static final String METHOD_DO_BACKGROUND = "doBackground";
        public static final String METHOD_DO_FOREGROUND = "doForeground";
        public static final String APP_UNREAD_COUNT_KEY = "appUnreadCountKey";


        public static class XiaoMi {
            public static final String XIAOMI_SERVICE_NAME = "TIMXiaoMiPushPlugin";
            public static final String METHOD_REGISTER_XIAOMI_PUSH = "registerTIMXiaoMiPush";
            public static final String XIAOMI_APPID = "xiaoMiAppId";
            public static final String XIAOMI_APPKEY = "xiaomiAppKey";
            public static final String METHOD_SET_XIAOMI_PUSH_REGION = "setXiaoMiPushRegion";
            public static final String XIAOMI_PUSH_REGION_KEY = "xiaoMiPushRegionKey";
            public static final int XIAOMI_REGION_EUROPE = 0;
            public static final int XIAOMI_REGION_RUSSIA = 1;
            public static final int XIAOMI_REGION_INDIA = 2;
            public static final int XIAOMI_REGION_ELSE = 3;
        }

        public static class HuaWei {
            public static final String HUAWEI_SERVICE_NAME = "TIMHuaWeiPushPlugin";
            public static final String METHOD_REGISTER_HUAWEI_PUSH = "registerTIMHuaWeiPush";
            public static final String METHOD_INIT_PUSH_FOR_UNI = "initPushForUni";
        }

        public static class FCM {
            public static final String FCM_SERVICE_NAME = "TIMFCMPushPlugin";
            public static final String METHOD_REGISTER_FCM_PUSH = "registerTIMFCMPush";
            public static final String ENABLE_FCM_PRIVATE_RING = "enableFCMPrivateRing";
            public static final String FCM_CHANNEL_ID = "fcmChannelId";
            public static final String FCM_CHANNEL_SOUND_NAME = "fcmChannelSoundName";
        }

        public static class Honor {
            public static final String HONOR_SERVICE_NAME = "TIMHonorPushPlugin";
            public static final String METHOD_REGISTER_HONOR_PUSH = "registerTIMHonorPush";
        }

        public static class MeiZu {
            public static final String MEIZU_SERVICE_NAME = "TIMMeiZuPushPlugin";
            public static final String METHOD_REGISTER_MEIZU_PUSH = "registerTIMMeiZuPush";
            public static final String MEIZU_APPID = "meiZuAppId";
            public static final String MEIZU_APPKEY = "meiZuAppKey";
            public static final String METHOD_IS_MEIZU_DEVICE = "isMeiZuDeviceMethod";
            public static final String IS_MEIZU_DEVICE_KEY = "isMeiZuDeviceKey";
        }

        public static class OPPO {
            public static final String OPPO_SERVICE_NAME = "TIMOPPOPushPlugin";
            public static final String METHOD_REGISTER_OPPO_PUSH = "registerTIMOPPOPush";
            public static final String OPPO_APPSECRET = "OPPOAppSecret";
            public static final String OPPO_APPKEY = "OPPOAppKey";
        }

        public static class VIVO {
            public static final String VIVO_SERVICE_NAME = "TIMVIVOPushPlugin";
            public static final String METHOD_REGISTER_VIVO_PUSH = "registerTIMVIVOPush";
        }

        public static class NOTIFICATION {
            // common
            public static final String TITLE = "IMTitle";
            public static final String DESCPTION = "IMDesc";
            public static final String CHANNEL_ID = "IMChannelID";
            public static final String CHANNEL_NAME = "IMChannelName";
            public static final String SOUND = "IMSound";
            public static final String MESSAGE_ID = "IMMsgID";

            // fcm & im
            public static final String SMALL_ICON = "IMSmallIcon";
            public static final String LARGE_ICON = "IMLargeIcon";
            public static final String BIG_PICTURE = "IMBigPicture";
            public static final String SUMMARY_TEXT = "IMSummaryText";
            public static final String PRIORITY = "IMPriority";

            // ext
            public static final String ENTITY = "entity";
            public static final String ACTION = "action";

            // report
            public static final String PUSH_ID = "clickExt";
            private static final String PUSH_NOTIFY_MODE = "notifyMode";
            public static final String PUSH_EVENT_TIME_KEY = "pushEventTime";
            public static final String PUSH_EVENT_TYPE_KEY = "pushEventType";
        }
    }

    public static final class TUICommunity {
        public static final String SERVICE_NAME = Service.TUI_COMMUNITY;

        public static final String TOPIC_ID = "topic_id";

        public static final String EVENT_KEY_COMMUNITY_EXPERIENCE = "eventKeyCommunityExperience";
        public static final String EVENT_SUB_KEY_ADD_COMMUNITY = "eventSubKeyAddCommunity";
        public static final String EVENT_SUB_KEY_CREATE_COMMUNITY = "eventSubKeyCreateCommunity";
        public static final String EVENT_SUB_KEY_DISBAND_COMMUNITY = "eventSubKeyDisbandCommunity";
        public static final String EVENT_SUB_KEY_CREATE_TOPIC = "eventSubKeyCreateTopic";
        public static final String EVENT_SUB_KEY_DELETE_TOPIC = "eventSubKeyDeleteTopic";
    }

    public static final class TUIPlugin {
        public static final String PLUGIN_BUSINESS_ID = "businessID";
        public static final String PLUGIN_TITLE = "title";
        public static final String PLUGIN_DESCRIPTION = "description";
        public static final String PLUGIN_CONTENT = "content";
        public static final String PLUGIN_EXTENSION_CONFIG = "config";
        public static final String PLUGIN_ORIGINAL_MESSAGE_ID = "original_msg_id";
        public static final String PLUGIN_ORIGINAL_MESSAGE_SEQUENCE = "original_msg_seq";

        public static final String BUSINESS_ID_PLUGIN_GROUP_NOTE = "group_note";
        public static final String BUSINESS_ID_PLUGIN_GROUP_NOTE_TIPS = "group_note_tips";
        public static final String BUSINESS_ID_PLUGIN_POLL = "group_poll";

        public static final String KEY_EXTENSIONS = "key_extensions";
    }

    public static final class TUIPollPlugin {
        public static final String SERVICE_NAME = Service.TUI_POLL;
        public static final int ACTION_ID_POLL = 3;

        public static final String POLL_CREATOR_ACTIVITY_NAME = "TUIPollCreatorActivity";
        public static final String PLUGIN_POLL_OPTION_LIST = "option_list";
        public static final String PLUGIN_POLL_OPTION_INDEX = "index";
        public static final String PLUGIN_POLL_OPTION_CONTENT = "option";
        public static final String PLUGIN_POLL_ENABLE_PUBLIC = "public";
        public static final String PLUGIN_POLL_ENABLE_MULTI_VOTE = "allow_multi_vote";
        public static final String PLUGIN_POLL_ANONYMOUS = "anonymous";
        public static final String METHOD_GET_POLL_MESSAGE_LAYOUT = "getPollMessageLayout";
        public static final String EVENT_KEY_POLL_MESSAGE_LAYOUT = "eventKeyPollMessageLayout";
        public static final String EVENT_SUB_KEY_REFRESH_POLL_MESSAGE_LAYOUT = "eventKeyRefreshPollMessageLayout";

        public static final String EVENT_KEY_POLL_EVENT = "eventKeyPollEvent";
        public static final String EVENT_SUB_KEY_POLL_VOTE_CHANGED = "eventSubKeyVoteChanged";
    }

    public static final class TUIGroupNotePlugin {
        public static final String SERVICE_NAME = Service.TUI_GROUP_NOTE;

        public static final int ACTION_ID_GROUP_NOTE = 4;

        public static final String GROUP_NOTE_CREATOR_ACTIVITY_NAME = "TUIGroupNoteCreatorActivity";

        public static final String PLUGIN_GROUP_NOTE_CREATOR = "creator";
        public static final String PLUGIN_GROUP_NOTE_SHOW_LINE_NUM = "show_line_num";
        public static final String PLUGIN_GROUP_NOTE_FORMAT = "format";
        public static final String PLUGIN_GROUP_NOTE_ENABLE_MULTIPLE_SUBMISSION = "multi_submit";
        public static final String PLUGIN_GROUP_NOTE_DEADLINE = "deadline";
        public static final String PLUGIN_GROUP_NOTE_ENABLE_NOTIFICATION = "notify";

        public static final String METHOD_GET_GROUP_NOTE_MESSAGE_LAYOUT = "getGroupNoteMessageLayout";
        public static final String METHOD_GET_GROUP_NOTE_TIPS_MESSAGE_LAYOUT = "getGroupNoteTipsMessageLayout";

        public static final String EVENT_KEY_GROUP_NOTE_EVENT = "eventKeyGroupNoteEvent";
        public static final String EVENT_SUB_KEY_GROUP_NOTE_CHANGED = "eventSubKeyGroupNoteChanged";
    }

    public static final class TIMAppKit {
        public static final String SERVICE_NAME = Service.TUI_DEMO;

        public static final String METHOD_INIT_IM_BEFORE_LOGIN = "initIMBeforeLogin";
        public static final String METHOD_ENTER_IM_FROM_RTCUBE = "enterIMFromRTCube";
        public static final String SDK_APP_ID = "sdkAppId";
        public static final String IM_DEMO_ITEM_TYPE_KEY = "imDemoItemTypeKey";
        public static final String IM_DEMO_ITEM_TYPE_CHAT = "imDemoItemTypeChat";
        public static final String IM_DEMO_ITEM_TYPE_COMMUNITY = "imDemoItemTypeCommunity";
        public static final String IM_DEMO_ITEM_TYPE_CONTACT = "imDemoItemTypeContact";
        public static final String IM_DEMO_ITEM_TYPE_RECENT_CALLS = "imDemoItemTypeRecentCalls";
        public static final String IM_DEMO_ITEM_TYPE_PROFILE = "imDemoItemTypeProfile";
        public static final String BACK_TO_RTCUBE_DEMO_TYPE_KEY = "backToRTCubeDemoTypeKey";
        public static final String BACK_TO_RTCUBE_DEMO_TYPE_IM = "backToRTCubeDemoTypeIM";
        public static final String NOTIFY_RTCUBE_EVENT_KEY = "notifyRTCubeEventKey";
        public static final String NOTIFY_RTCUBE_LOGIN_SUB_KEY = "notifyRTCubeLoginSubKey";
        public static final String NOFITY_IMLOGIN_SUCCESS_SUB_KEY = "nofityIMLoginSuccessSubKey";
        public static final String OFFLINE_PUSH_INTENT_DATA = "offlinePushIntentData";

        public static final int BACK_RTCUBE_HOME_ICON_WIDTH = 40;
        public static final int BACK_RTCUBE_HOME_ICON_HEIGHT = 32;

        public static class Extension {
            public static class ProfileSettings {
                public static final String CLASSIC_EXTENSION_ID = "ProfileSettingsViewClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ProfileSettingsMinimalistID";
                public static final String KEY_VIEW = "view";
            }
        }
    }

    public static final class TUIConversationGroupPlugin {
        public static final String SERVICE_NAME = Service.TUI_CONVERSATION_GROUP;
        public static final String OBJECT_FACTORY_NAME = ObjectFactory.FACTORY_CONVERSATION_GROUP;
        public static final String CONTEXT = "context";
        public static final String EXTENSION_GROUP_SETTING_MENU = "extensionGroupSettingMenu";
        public static final String OBJECT_CONVERSATION_GROUP_FRAGMENT = "objectConversationGroupFragment";

        public static class Extension {
            // ConversationGroup popmenu Extension
            public static class ConversationPopMenu {
                public static final String CLASSIC_EXTENSION_ID = "ConversationGroupPopMenuExtensionClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ConversationGroupPopMenuExtensionMinimalistID";
            }
        }
    }

    public static final class TUIConversationMarkPlugin {
        public static final String SERVICE_NAME = Service.TUI_CONVERSATION_MARK;
        public static final String OBJECT_FACTORY_NAME = ObjectFactory.FACTORY_CONVERSATION_MARK;
        public static final String CONTEXT = "context";
        public static final String OBJECT_CONVERSATION_MARK_BEANS = "objectConversationMarkBeans";
        public static final String OBJECT_CONVERSATION_MARK_FRAGMENT = "objectConversationMarkFragment";

        public static class Extension {
            // ConversationMark popmenu Extension
            public static class ConversationPopMenu {
                public static final String CLASSIC_EXTENSION_ID = "ConversationMarkPopMenuExtensionClassicID";
                public static final String MINIMALIST_EXTENSION_ID = "ConversationMarkPopMenuExtensionMinimalistID";
            }
        }
    }

    public static final class TUICustomerServicePlugin {
        public static final String CUSTOMER_SERVICE_MESSAGE_KEY = "customerServicePlugin";
        public static final String CUSTOMER_SERVICE_BUSINESS_ID_SRC_KEY = "src";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_SAY_HELLO = "7";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_TRIGGERED_CONTENT = "triggeredContent";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_TRIGGERED_LANGUAGE = "language";

        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_EVALUATION = "9";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_EVALUATION_SELECTED = "10";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_TYPING = "12";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_BRANCH = "15";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_END = "19";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_TIMEOUT = "20";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_COLLECTION = "21";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_CARD = "22";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_EVALUATION_SETTING = "23";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_TRIGGER_EVALUATION = "24";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_SEAT_STATUS = "26";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_BOT_RESPONSE = "29"; // Supported in 8.1 and later
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_BOT_RICH_TEXT = "30"; // Supported in 8.1 and later
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_BOT_STREAM_TEXT = "31"; // Supported in 8.1 and later
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_TASKS_BRANCH = "32";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_TASKS_COLLECTION = "33";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_THINKING = "35";
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_CLIENT_TIPS = "37";    // 用户超时提醒消息类型
        public static final String BUSINESS_ID_SRC_CUSTOMER_SERVICE_AGENT_TIPS = "38";    // 用户超时提醒客服消息类型
        public static final String METHOD_GET_CUSTOMER_SERVICE_ACCOUNTS = "getCustomerServiceAccounts";
    }

    public static final class Message {
        public static final String CUSTOM_BUSINESS_ID_KEY = "businessID";
        public static final String CALLING_TYPE_KEY = "call_type";
    }

    public static final class NetworkConnection {
        public static final String EVENT_CONNECTION_STATE_CHANGED = "eventConnectionStateChanged";
        public static final String EVENT_SUB_KEY_CONNECTING = "eventSubKeyConnecting";
        public static final String EVENT_SUB_KEY_CONNECT_SUCCESS = "eventSubKeyConnectSuccess";
        public static final String EVENT_SUB_KEY_CONNECT_FAILED = "eventSubKeyConnectFailed";
    }

    public static final class BuyingFeature {
        public static final int ERR_SDK_INTERFACE_NOT_SUPPORT = BaseConstants.ERR_SDK_INTERFACE_NOT_SUPPORT;
        public static final String BUYING_GUIDELINES_EN = "https://intl.cloud.tencent.com/document/product/1047/36021?lang=en&pg=#changing-configuration";
        public static final String BUYING_GUIDELINES = "https://cloud.tencent.com/document/product/269/32458";

        public static final String BUYING_PRICE_DESC_EN = "https://www.tencentcloud.com/document/product/1047/34349#basic-services";
        public static final String BUYING_PRICE_DESC =
            "https://cloud.tencent.com/document/product/269/11673?from=17219#.E5.9F.BA.E7.A1.80.E6.9C.8D.E5.8A.A1.E8.AF.A6.E6.83.85";
        public static final String BUYING_PLUGIN_URL = "https://buy.cloud.tencent.com/avc?activeId=plugin&regionId=1";
        public static final String BUYING_GROUP_CHAT = "https://zhiliao.qq.com/";

        public static final String BUYING_FEATURE_MESSAGE_RECEIPT = "buying_chat_message_read_receipt";
        public static final String BUYING_FEATURE_COMMUNITY = "buying_community";
        public static final String BUYING_FEATURE_SEARCH = "buying_search";
        public static final String BUYING_FEATURE_ONLINE_STATUS = "buying_online_status";
    }

    public static final class Privacy {
        public static class PermissionsFactory {
            public static final String FACTORY_NAME = "PrivacyPermissionsFactory";

            public static class PermissionsName {
                public static final String CAMERA_PERMISSIONS = "CameraPermissions";
                public static final String CAMERA_PERMISSIONS_TIP = "CameraPermissionsTip";
                public static final String MICROPHONE_PERMISSIONS = "MicrophonePermissions";
                public static final String MICROPHONE_PERMISSIONS_TIP = "MicrophonePermissionsTip";
            }
        }

        public static final String METHOD_SHOW_SCREEN_SHARE_TIP = "methodShowScreenShareTip";
        public static final String METHOD_ANTO_FRAUD_REMINDER = "methodAntiFraudReminder";
        public static final String PARAM_DIALOG_CONTEXT = "paramDialogContext";

        public static final int RESULT_CANCEL = 0;
        public static final int RESULT_CONFIRM = 1;
        public static final int RESULT_CONTINUE = 2;

        public static final String EVENT_ROOM_STATE_CHANGED = "eventRoomStateChanged";
        public static final String EVENT_SUB_KEY_ROOM_STATE_START = "eventSubKeyRoomStateStart";
        public static final String EVENT_SUB_KEY_ROOM_STATE_STOP = "eventSubKeyRoomStateStop";

    }

    // localBroadcast
    public static final String CONVERSATION_UNREAD_COUNT_ACTION = "conversation_unread_count_action";
    public static final String UNREAD_COUNT_EXTRA = "unread_count_extra";
}
