package com.tencent.qcloud.tuikit.deskchat.minimalistui.widget;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMGroupAtInfo;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.deskcore.TUIThemeManager;
import com.tencent.qcloud.deskcore.interfaces.TUIExtensionInfo;
import com.tencent.qcloud.deskcore.util.ToastUtil;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.page.DeskMessageDetailMinimalistActivity;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.component.dialog.TUIKitDialog;
import com.tencent.qcloud.tuikit.deskcommon.component.gatherimage.SynthesizedImageView;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuikit.deskcommon.interfaces.ChatInputMoreListener;
import com.tencent.qcloud.tuikit.deskcommon.interfaces.OnChatPopActionClickListener;
import com.tencent.qcloud.tuikit.deskcommon.util.LayoutUtil;
import com.tencent.qcloud.tuikit.deskcommon.util.ScreenUtil;
import com.tencent.qcloud.tuikit.deskcommon.util.TUIUtil;
import com.tencent.qcloud.tuikit.deskcommon.util.ThreadUtils;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.TUIChatConstants;
import com.tencent.qcloud.tuikit.deskchat.TUIChatService;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.GroupApplyInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.GroupChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.GroupMemberInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.MessageTyping;
import com.tencent.qcloud.tuikit.deskchat.bean.ReplyPreviewBean;
import com.tencent.qcloud.tuikit.deskchat.bean.UserStatusBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.CallingMessageBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.ReplyMessageBean;
import com.tencent.qcloud.tuikit.deskchat.component.audio.AudioPlayer;
import com.tencent.qcloud.tuikit.deskchat.component.audio.AudioRecorder;
import com.tencent.qcloud.tuikit.deskchat.component.pinned.GroupPinnedView;
import com.tencent.qcloud.tuikit.deskchat.component.progress.ProgressPresenter;
import com.tencent.qcloud.tuikit.deskchat.config.TUIChatConfigs;
import com.tencent.qcloud.tuikit.deskchat.config.minimalistui.TUIChatConfigMinimalist;
import com.tencent.qcloud.tuikit.deskchat.interfaces.OnEmptySpaceClickListener;
import com.tencent.qcloud.tuikit.deskchat.interfaces.OnGestureScrollListener;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.component.dialog.ChatBottomSelectSheet;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.component.noticelayout.NoticeLayout;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.interfaces.IChatLayout;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.page.DeskTUIBaseChatMinimalistFragment;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.input.InputView;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.MessageAdapter;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.MessageRecyclerView;
import com.tencent.qcloud.tuikit.deskchat.presenter.C2CChatPresenter;
import com.tencent.qcloud.tuikit.deskchat.presenter.ChatPresenter;
import com.tencent.qcloud.tuikit.deskchat.presenter.GroupChatPresenter;
import com.tencent.qcloud.tuikit.deskchat.util.ChatMessageBuilder;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChatView extends LinearLayout implements IChatLayout {
    private static final String TAG = ChatView.class.getSimpleName();

    // Limit the number of messages forwarded one by one
    private static final int FORWARD_MSG_NUM_LIMIT = 30;

    protected MessageAdapter mAdapter;
    private ForwardSelectActivityListener mForwardSelectActivityListener;

    private ChatInfo mChatInfo;

    protected FrameLayout topExtensionLayout;
    protected GroupPinnedView groupPinnedView;

    protected FrameLayout mCustomView;
    protected NoticeLayout mGroupApplyLayout;
    protected View mRecordingGroup;
    protected TextView mRecordingTips;
    private MessageRecyclerView mMessageRecyclerView;
    private ImageView chatBackgroundView;
    private InputView mInputView;
    private NoticeLayout mNoticeLayout;
    private LinearLayout mJumpMessageLayout;
    private ImageView mArrowImageView;
    private TextView mJumpMessageTextView;
    private boolean mJumpNewMessageShow;
    private boolean mJumpGroupAtInfoShow;
    private boolean mClickLastMessageShow;

    private View forwardArea;
    private View forwardButton;
    private View deleteButton;
    private View forwardCancelButton;
    private CardView jumpView;
    private ChatBottomSelectSheet forwardSelectSheet;
    private TextView forwardText;
    private long lastTypingTime = 0;
    private boolean isSupportTyping = false;

    private LinearLayout extensionArea;
    private View chatHeaderBackBtn;
    private TextView chatName;
    private TextView chatDescription;
    private SynthesizedImageView chatAvatar;
    private View userNameArea;
    private OnClickListener onBackClickListener;
    private OnClickListener onAvatarClickListener;
    private ChatPresenter presenter;
    private int scrollDirection = 0;
    private UserStatusBean userStatusBean;
    private Runnable typingRunnable = null;
    public ChatPresenter.TypingListener typingListener = new ChatPresenter.TypingListener() {
        @Override
        public void onTyping(int status) {
            if (!TUIChatConfigMinimalist.isEnableTypingIndicator()) {
                return;
            }

            if (mChatInfo == null) {
                return;
            }

            TUIChatLog.d(TAG, "mTypingListener status= " + status);
            if (status == 1) {
                chatDescription.setText(R.string.typing);

                if (typingRunnable == null) {
                    typingRunnable = new Runnable() {
                        @Override
                        public void run() {
                            setUserStatusDesc();
                        }
                    };
                }
                chatDescription.removeCallbacks(typingRunnable);
                chatDescription.postDelayed(typingRunnable, TUIChatConstants.TYPING_PARSE_MESSAGE_INTERVAL * 1000);
            } else if (status == 0) {
                chatDescription.removeCallbacks(typingRunnable);
                setUserStatusDesc();
            } else {
                TUIChatLog.e(TAG, "parseTypingMessage error status =" + status);
            }
        }
    };

    public ChatView(Context context) {
        super(context);
        initViews();
    }

    public ChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ChatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.desk_tuichat_chat_minimalist_layout, this);

        mMessageRecyclerView = findViewById(R.id.chat_message_layout);
        chatBackgroundView = findViewById(R.id.chat_background_view);
        mInputView = findViewById(R.id.chat_input_layout);
        mInputView.setChatLayout(this);
        boolean enableMainPageInputBar = TUIChatConfigMinimalist.isShowInputBar();
        mInputView.setVisibility(enableMainPageInputBar ? VISIBLE : GONE);
        mRecordingGroup = findViewById(R.id.voice_recording_view);
        mRecordingTips = findViewById(R.id.recording_tips);
        mGroupApplyLayout = findViewById(R.id.chat_group_apply_layout);
        mNoticeLayout = findViewById(R.id.chat_notice_layout);
        mCustomView = findViewById(R.id.custom_layout);
        mCustomView.setVisibility(GONE);
        topExtensionLayout = findViewById(R.id.chat_top_extension_layout);
        topExtensionLayout.setVisibility(GONE);

        groupPinnedView = findViewById(R.id.group_pinned_message_view);

        forwardArea = findViewById(R.id.forward_area);
        forwardButton = findViewById(R.id.forward_image);
        deleteButton = findViewById(R.id.delete_image);
        forwardText = findViewById(R.id.forward_select_text);
        forwardCancelButton = findViewById(R.id.forward_cancel_btn);

        jumpView = findViewById(R.id.jump_view_layout);
        int contentPaddingRight = ScreenUtil.dip2px(16);
        if (LayoutUtil.isRTL()) {
            jumpView.setContentPadding(contentPaddingRight, 0, 0, 0);
        } else {
            jumpView.setContentPadding(0, 0, contentPaddingRight, 0);
        }
        mJumpMessageLayout = findViewById(R.id.jump_message_layout);
        mJumpMessageTextView = findViewById(R.id.jump_message_content);
        mArrowImageView = findViewById(R.id.arrow_icon);
        mJumpNewMessageShow = false;
        hideJumpMessageLayouts();

        extensionArea = findViewById(R.id.extension_area);
        chatName = findViewById(R.id.chat_name);
        chatDescription = findViewById(R.id.chat_description);
        chatAvatar = findViewById(R.id.avatar_img);
        chatHeaderBackBtn = findViewById(R.id.back_btn);
        chatHeaderBackBtn.getBackground().setAutoMirrored(true);
        userNameArea = findViewById(R.id.user_name_area);

        lastTypingTime = 0;
    }

    public void displayBackToLastMessages() {
        mJumpMessageLayout.setVisibility(VISIBLE);
        mArrowImageView.setBackgroundResource(R.drawable.chat_minimalist_jump_down_icon);
        mJumpMessageTextView.setVisibility(GONE);
        mJumpMessageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatInfo chatInfo = getChatInfo();
                presenter.locateLastMessage(chatInfo.getId(), chatInfo.getType() == ChatInfo.TYPE_GROUP, new IUIKitCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        hideJumpMessageLayouts();
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        hideJumpMessageLayouts();
                        ToastUtil.toastShortMessage(getContext().getString(R.string.locate_origin_msg_failed_tip));
                    }
                });
                hideJumpMessageLayouts();
                mClickLastMessageShow = true;
            }
        });
    }

    public void displayBackToNewMessages(String messageId, int count) {
        mJumpNewMessageShow = true;
        mJumpMessageLayout.setVisibility(VISIBLE);
        mArrowImageView.setBackgroundResource(R.drawable.chat_minimalist_jump_down_icon);
        mJumpMessageTextView.setVisibility(VISIBLE);
        mJumpMessageTextView.setText(String.valueOf(count));
        mJumpMessageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                locateOriginMessage(messageId);
                presenter.markMessageAsRead(mChatInfo);
                mJumpNewMessageShow = false;
                presenter.resetNewMessageCount();
            }
        });
    }

    public void displayBackToAtMessages(List<V2TIMGroupAtInfo> groupAtInfos) {
        mJumpGroupAtInfoShow = true;
        mJumpMessageLayout.setVisibility(VISIBLE);
        mArrowImageView.setBackgroundResource(R.drawable.chat_minimalist_jump_at_icon);
        int atTimes = groupAtInfos.size();
        mJumpMessageTextView.setText(atTimes + "");
        mJumpMessageTextView.setVisibility(VISIBLE);
        mJumpMessageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                locateOriginMessageBySeq(groupAtInfos.get(0).getSeq());
                hideJumpMessageLayouts();
                mJumpGroupAtInfoShow = false;
            }
        });
    }

    public void hideJumpMessageLayouts() {
        mJumpMessageLayout.setVisibility(GONE);
    }

    public void hideBackToAtMessages() {
        if (mJumpGroupAtInfoShow) {
            mJumpGroupAtInfoShow = false;
            hideJumpMessageLayouts();
        }
    }

    private void initGroupAtInfoLayout() {
        if (mChatInfo != null) {
            List<V2TIMGroupAtInfo> groupAtInfos = mChatInfo.getAtInfoList();
            if (groupAtInfos != null && groupAtInfos.size() > 0) {
                displayBackToAtMessages(groupAtInfos);
            } else {
                TUIChatLog.d(TAG, "initGroupAtInfoLayout groupAtInfos == null");
                mJumpGroupAtInfoShow = false;
                hideJumpMessageLayouts();
            }
        } else {
            TUIChatLog.d(TAG, "initGroupAtInfoLayout mChatInfo == null");
            mJumpGroupAtInfoShow = false;
            hideJumpMessageLayouts();
        }
    }

    private void locateOriginMessageBySeq(long seq) {
        presenter.locateMessageBySeq(mChatInfo.getId(), seq, new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                hideJumpMessageLayouts();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                hideJumpMessageLayouts();
                ToastUtil.toastShortMessage(getContext().getString(R.string.locate_origin_msg_failed_tip));
            }
        });
    }

    private void locateOriginMessage(String originMsgId) {
        if (TextUtils.isEmpty(originMsgId)) {
            ToastUtil.toastShortMessage(getContext().getString(R.string.locate_origin_msg_failed_tip));
            return;
        }
        presenter.locateMessage(originMsgId, new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                hideJumpMessageLayouts();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                hideJumpMessageLayouts();
                ToastUtil.toastShortMessage(getContext().getString(R.string.locate_origin_msg_failed_tip));
            }
        });
        hideJumpMessageLayouts();
    }

    public void setPresenter(ChatPresenter presenter) {
        this.presenter = presenter;
        mMessageRecyclerView.setPresenter(presenter);
        mInputView.setPresenter(presenter);
        presenter.setMessageListAdapter(mAdapter);
        mAdapter.setPresenter(presenter);
        presenter.setMessageRecycleView(mMessageRecyclerView);
    }

    public void setOnBackClickListener(OnClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    public void setOnAvatarClickListener(OnClickListener onAvatarClickListener) {
        this.onAvatarClickListener = onAvatarClickListener;
    }

    public void setChatInfo(ChatInfo chatInfo) {
        mChatInfo = chatInfo;
        if (chatInfo == null) {
            return;
        }
        mInputView.setChatInfo(chatInfo);
        setChatHandler();

        if (!TUIChatUtils.isC2CChat(chatInfo.getType())) {
            loadApplyList();
            mGroupApplyLayout.setOnNoticeClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(TUIChatConstants.GROUP_ID, chatInfo.getId());
                    TUICore.startActivity(getContext(), "GroupApplyManagerMinimalistActivity", bundle);
                }
            });
        }

        mMessageRecyclerView.setOnGestureScrollListener(new OnGestureScrollListener() {
            @Override
            public void onScroll(MotionEvent m1, MotionEvent m2, float distanceX, float distanceY) {
                if (distanceY < 0) {
                    scrollDirection = -1;
                } else if (distanceY > 0) {
                    scrollDirection = 1;
                } else {
                    scrollDirection = 0;
                }
            }
        });

        mMessageRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getMessageLayout().getLayoutManager();
                int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                sendMsgReadReceipt(firstVisiblePosition, lastVisiblePosition);
                notifyMessageDisplayed(firstVisiblePosition, lastVisiblePosition);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (scrollDirection == -1) {
                        if (!mMessageRecyclerView.canScrollVertically(-1)) {
                            mAdapter.showLoading();
                            loadMessages(TUIChatConstants.GET_MESSAGE_FORWARD);
                        }
                    } else if (scrollDirection == 1) {
                        if (!mMessageRecyclerView.canScrollVertically(1)) {
                            loadMessages(TUIChatConstants.GET_MESSAGE_BACKWARD);
                            displayBackToLastMessage(false);
                            displayBackToNewMessage(false, "", 0);
                            presenter.resetCurrentChatUnreadCount();
                        }
                    }
                    scrollDirection = 0;

                    if (mMessageRecyclerView.isDisplayJumpMessageLayout()) {
                        displayBackToLastMessage(true);
                    } else {
                        displayBackToLastMessage(false);
                    }
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hideBackToAtMessages();
                }
            }
        });

        loadPinnedMessage();
        loadMessages(
            chatInfo.getLocateMessage(), chatInfo.getLocateMessage() == null ? TUIChatConstants.GET_MESSAGE_FORWARD : TUIChatConstants.GET_MESSAGE_TWO_WAY);
        initHeader();
    }

    private void initHeader() {
        initExtension();
        loadAvatar();
        loadChatName();
        if (TUIChatUtils.isGroupChat(mChatInfo.getType())) {
            ((GroupChatPresenter) presenter).loadGroupMembers(mChatInfo.getId(), new IUIKitCallback<List<GroupMemberInfo>>() {
                @Override
                public void onSuccess(List<GroupMemberInfo> data) {
                    StringBuilder builder = new StringBuilder();
                    for (GroupMemberInfo memberInfo : data) {
                        builder.append(memberInfo.getDisplayName());
                        builder.append("、");
                    }
                    builder.deleteCharAt(builder.lastIndexOf("、"));
                    chatDescription.setText(builder);
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    chatDescription.setText(R.string.chat_user_status_unknown);
                }
            });
        } else {
            presenter.loadUserStatus(Collections.singletonList(mChatInfo.getId()), new IUIKitCallback<Map<String, UserStatusBean>>() {
                @Override
                public void onSuccess(Map<String, UserStatusBean> data) {
                    userStatusBean = data.get(mChatInfo.getId());
                    setUserStatusDesc();
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    chatDescription.setText(R.string.chat_user_status_unknown);
                }
            });
        }
        chatAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeaderUserClick(v);
            }
        });

        userNameArea.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeaderUserClick(v);
            }
        });

        chatHeaderBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackClickListener != null) {
                    onBackClickListener.onClick(v);
                }
            }
        });
    }

    private void setUserStatusDesc() {
        if (userStatusBean == null || userStatusBean.getOnlineStatus() != UserStatusBean.STATUS_ONLINE) {
            chatDescription.setText(R.string.chat_user_status_offline);
        } else {
            chatDescription.setText(R.string.chat_user_status_online);
        }
    }

    private void loadChatName() {
        if (!TextUtils.isEmpty(mChatInfo.getChatName())) {
            chatName.setText(mChatInfo.getChatName());
        } else {
            presenter.getChatName(mChatInfo.getId(), new IUIKitCallback<String>() {
                @Override
                public void onSuccess(String data) {
                    chatName.setText(data);
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    chatName.setText(mChatInfo.getId());
                }
            });
        }
    }

    private void loadAvatar() {
        int avatarRadius = TUIChatConfigMinimalist.getAvatarCornerRadius();
        if (avatarRadius != TUIChatConfigMinimalist.UNDEFINED) {
            chatAvatar.setRadius(avatarRadius);
        }
        String chatId = mChatInfo.getId();
        if (TUIChatUtils.isGroupChat(mChatInfo.getType())) {
            if (mChatInfo.getIconUrlList() == null || mChatInfo.getIconUrlList().isEmpty()) {
                loadFaceAsync(chatId);
            } else {
                chatAvatar.setImageId(chatId);
                chatAvatar.displayImage(mChatInfo.getIconUrlList()).load(chatId);
            }
        } else {
            String faceUrl = mChatInfo.getFaceUrl();
            if (!TextUtils.isEmpty(faceUrl)) {
                loadFace(faceUrl);
            } else {
                loadFaceAsync(chatId);
            }
        }
    }

    private void loadFaceAsync(String chatId) {
        presenter.getChatFaceUrl(chatId, new IUIKitCallback<List<Object>>() {
            @Override
            public void onSuccess(List<Object> data) {
                chatAvatar.setImageId(chatId);
                chatAvatar.displayImage(data).load(chatId);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                loadFace(null);
            }
        });
    }

    private void loadFace(String faceUrl) {
        int resID = com.tencent.qcloud.tuikit.deskcommon.R.drawable.core_default_user_icon_light;
        if (mChatInfo instanceof GroupChatInfo) {
            resID = TUIUtil.getDefaultGroupIconResIDByGroupType(getContext(), ((GroupChatInfo) mChatInfo).getGroupType());
        }

        Glide.with(this).load(faceUrl).apply(new RequestOptions().error(resID).placeholder(resID)).into(chatAvatar);
    }

    private void onHeaderUserClick(View v) {
        if (onAvatarClickListener != null) {
            onAvatarClickListener.onClick(v);
        }
    }

    private void initExtension() {
        // topic not support call yet.
        Map<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUIChat.Extension.ChatNavigationMoreItem.CONTEXT, getContext());
        if (ChatInfo.TYPE_C2C == mChatInfo.getType()) {
            param.put(TUIConstants.TUIChat.Extension.ChatNavigationMoreItem.USER_ID, mChatInfo.getId());
        } else {
            param.put(TUIConstants.TUIChat.Extension.ChatNavigationMoreItem.GROUP_ID, mChatInfo.getId());
        }
        param.put(TUIConstants.TUIChat.Extension.ChatNavigationMoreItem.FILTER_VIDEO_CALL,
            !mChatInfo.isEnableVideoCall() || !TUIChatConfigMinimalist.isEnableVideoCall());
        param.put(TUIConstants.TUIChat.Extension.ChatNavigationMoreItem.FILTER_VOICE_CALL,
            !mChatInfo.isEnableAudioCall() || !TUIChatConfigMinimalist.isEnableAudioCall());

        List<TUIExtensionInfo> extensionInfos = TUICore.getExtensionList(TUIConstants.TUIChat.Extension.ChatNavigationMoreItem.MINIMALIST_EXTENSION_ID, param);
        Collections.sort(extensionInfos, new Comparator<TUIExtensionInfo>() {
            @Override
            public int compare(TUIExtensionInfo o1, TUIExtensionInfo o2) {
                return o2.getWeight() - o1.getWeight();
            }
        });
        for (TUIExtensionInfo extensionInfo : extensionInfos) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.desk_chat_minimalist_title_extension_item_layout, null);
            ImageView extension = view.findViewById(R.id.extension_item);
            extension.setBackgroundResource((Integer) extensionInfo.getIcon());
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (extensionInfo.getExtensionListener() != null) {
                        extensionInfo.getExtensionListener().onClicked(null);
                    }
                }
            });
            extensionArea.addView(view);
        }

        // chat top extension
        Map<String, Object> topExtensionParam = new HashMap<>();
        topExtensionParam.put(
            TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.VIEW_TYPE, TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.VIEW_TYPE_MINIMALIST);
        topExtensionParam.put(TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.CHAT_ID, mChatInfo.getId());
        topExtensionParam.put(TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.IS_GROUP, ChatInfo.TYPE_GROUP == mChatInfo.getType());
        TUICore.raiseExtension(TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.EXTENSION_ID, topExtensionLayout, topExtensionParam);
    }

    private void sendMsgReadReceipt(int firstPosition, int lastPosition) {
        if (mAdapter == null || presenter == null) {
            return;
        }
        List<TUIMessageBean> tuiMessageBeans = mAdapter.getItemList(firstPosition, lastPosition);
        presenter.sendMessageReadReceipt(tuiMessageBeans, new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {}

            @Override
            public void onError(String module, int errCode, String errMsg) {
                if (errCode == TUIConstants.BuyingFeature.ERR_SDK_INTERFACE_NOT_SUPPORT) {
                    showNotSupportDialog();
                }
            }
        });
    }

    private void markCallingMsgRead(int firstPosition, int lastPosition) {
        if (mAdapter == null || presenter == null) {
            return;
        }
        List<CallingMessageBean> tuiMessageBeans = new ArrayList<CallingMessageBean>();
        for (TUIMessageBean bean : mAdapter.getItemList(firstPosition, lastPosition)) {
            if (bean instanceof CallingMessageBean) {
                tuiMessageBeans.add((CallingMessageBean) bean);
            }
        }

        presenter.markCallingMsgRead(tuiMessageBeans);
    }

    private void notifyMessageDisplayed(int firstPosition, int lastPosition) {
        // *******************************

        // *******************************
        markCallingMsgRead(firstPosition, lastPosition);
        // *******************************
        // *******************************

        if (mAdapter == null || presenter == null) {
            return;
        }
        for (TUIMessageBean bean : mAdapter.getItemList(firstPosition, lastPosition)) {
            Map<String, Object> param = new HashMap<>();
            param.put(TUIConstants.TUIChat.MESSAGE_BEAN, bean);
            TUICore.notifyEvent(TUIConstants.TUIChat.EVENT_KEY_MESSAGE_EVENT, TUIConstants.TUIChat.EVENT_SUB_KEY_DISPLAY_MESSAGE_BEAN, param);
        }
    }

    private void setChatHandler() {
        if (presenter instanceof GroupChatPresenter) {
            presenter.setChatNotifyHandler(new ChatPresenter.ChatNotifyHandler() {
                @Override
                public void onGroupForceExit() {
                    ChatView.this.onExitChat();
                }

                @Override
                public void onGroupNameChanged(String newName) {
                    ChatView.this.onGroupNameChanged(newName);
                }

                @Override
                public void onGroupFaceUrlChanged(String faceUrl) {
                    if (isActivityDestroyed()) {
                        return;
                    }
                    Glide.with(getContext())
                        .load(faceUrl)
                        .apply(new RequestOptions()
                                   .error(com.tencent.qcloud.tuikit.deskcommon.R.drawable.core_default_user_icon_light)
                                   .placeholder(com.tencent.qcloud.tuikit.deskcommon.R.drawable.core_default_user_icon_light))
                        .into(chatAvatar);
                }

                @Override
                public void onApplied(int size) {
                    ChatView.this.onApplied(size);
                }

                @Override
                public void onExitChat(String chatId) {
                    ChatView.this.onExitChat();
                }
            });
        } else if (presenter instanceof C2CChatPresenter) {
            presenter.setChatNotifyHandler(new ChatPresenter.ChatNotifyHandler() {
                @Override
                public void onExitChat(String chatId) {
                    ChatView.this.onExitChat();
                }

                @Override
                public void onFriendNameChanged(String newName) {
                    if (isActivityDestroyed()) {
                        return;
                    }
                    ChatView.this.onFriendNameChanged(newName);
                }

                @Override
                public void onFriendFaceUrlChanged(String faceUrl) {
                    if (isActivityDestroyed()) {
                        return;
                    }
                    Glide.with(getContext())
                        .load(faceUrl)
                        .apply(new RequestOptions()
                                   .error(com.tencent.qcloud.tuikit.deskcommon.R.drawable.core_default_user_icon_light)
                                   .placeholder(com.tencent.qcloud.tuikit.deskcommon.R.drawable.core_default_user_icon_light))
                        .into(chatAvatar);
                }
            });
        }
    }

    private boolean isActivityDestroyed() {
        Context context = getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing() || ((Activity) context).isDestroyed()) {
                return true;
            }
        }
        return false;
    }

    private void loadApplyList() {
        presenter.loadApplyList(new IUIKitCallback<List<GroupApplyInfo>>() {
            @Override
            public void onSuccess(List<GroupApplyInfo> data) {
                if (data != null && data.size() > 0) {
                    mGroupApplyLayout.getContent().setText(getContext().getString(R.string.group_apply_tips, data.size()));
                    mGroupApplyLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                TUIChatLog.e(TAG, "loadApplyList onError: " + errMsg);
            }
        });
    }

    public void onExitChat() {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }

    public void onGroupNameChanged(String newName) {
        chatName.setText(newName);
    }

    public void onFriendNameChanged(String newName) {
        chatName.setText(newName);
    }

    public void onApplied(int size) {
        if (size <= 0) {
            mGroupApplyLayout.setVisibility(View.GONE);
        } else {
            mGroupApplyLayout.getContent().setText(getContext().getString(R.string.group_apply_tips, size));
            mGroupApplyLayout.setVisibility(View.VISIBLE);
        }
    }

    public void loadMessages(TUIMessageBean lastMessage, int type) {
        if (presenter != null) {
            presenter.loadMessage(type, lastMessage);
        }
    }

    @Override
    public void loadMessages(int type) {
        if (type == TUIChatConstants.GET_MESSAGE_FORWARD) {
            loadMessages(mAdapter.getItemCount() > 0 ? mAdapter.getFirstMessageBean() : null, type);
        } else if (type == TUIChatConstants.GET_MESSAGE_BACKWARD) {
            loadMessages(mAdapter.getItemCount() > 0 ? mAdapter.getLastMessageBean() : null, type);
        }
    }

    public void loadPinnedMessage() {
        if (presenter instanceof GroupChatPresenter) {
            groupPinnedView.setGroupChatPresenter((GroupChatPresenter) presenter);
            ((GroupChatPresenter) presenter).setGroupPinnedView(groupPinnedView);
            ((GroupChatPresenter) presenter).loadPinnedMessage(getChatInfo().getId());
        }
    }

    @Override
    public InputView getInputLayout() {
        return mInputView;
    }

    @Override
    public MessageRecyclerView getMessageLayout() {
        return mMessageRecyclerView;
    }

    @Override
    public NoticeLayout getNoticeLayout() {
        return mNoticeLayout;
    }

    public FrameLayout getCustomView() {
        return mCustomView;
    }

    @Override
    public ChatInfo getChatInfo() {
        return mChatInfo;
    }

    private void initListener() {
        getMessageLayout().setPopActionClickListener(new OnChatPopActionClickListener() {
            @Override
            public void onCopyClick(TUIMessageBean msg) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard == null || msg == null) {
                    return;
                }
                String copyContent = msg.getSelectText();
                ClipData clip = ClipData.newPlainText("message", copyContent);
                clipboard.setPrimaryClip(clip);

                if (!TextUtils.isEmpty(copyContent)) {
                    String copySuccess = getResources().getString(R.string.copy_success_tip);
                    ToastUtil.toastShortMessage(copySuccess);
                }
            }

            @Override
            public void onSendMessageClick(TUIMessageBean msg, boolean retry) {
                sendMessage(msg, retry);
            }

            @Override
            public void onDeleteMessageClick(TUIMessageBean msg) {
                TUIKitDialog tipsDialog = new TUIKitDialog(getContext())
                                              .builder()
                                              .setCancelable(true)
                                              .setCancelOutside(true)
                                              .setTitle(getContext().getString(R.string.chat_delete_msg_tip))
                                              .setDialogWidth(0.75f)
                                              .setPositiveButton(getContext().getString(com.tencent.qcloud.deskcore.R.string.sure),
                                                  new OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          deleteMessage(msg);
                                                      }
                                                  })
                                              .setNegativeButton(getContext().getString(com.tencent.qcloud.deskcore.R.string.cancel), new OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {}
                                              });
                tipsDialog.show();
            }

            @Override
            public void onRevokeMessageClick(TUIMessageBean msg) {
                revokeMessage(msg);
            }

            @Override
            public void onMultiSelectMessageClick(TUIMessageBean msg) {
                multiSelectMessage(msg);
            }

            @Override
            public void onForwardMessageClick(TUIMessageBean msg) {
                forwardMessage(msg);
            }

            @Override
            public void onReplyMessageClick(TUIMessageBean msg) {
                replyMessage(msg);
            }

            @Override
            public void onQuoteMessageClick(TUIMessageBean msg) {
                quoteMessage(msg);
            }

            @Override
            public void onInfoMessageClick(TUIMessageBean msg) {
                showMessageInfo(msg);
            }

            @Override
            public void onSpeakerModeSwitchClick(TUIMessageBean msg) {
                boolean enableSpeakerMode = TUIChatConfigs.getGeneralConfig().isEnableSoundMessageSpeakerMode();
                TUIChatConfigs.getGeneralConfig().setEnableSoundMessageSpeakerMode(!enableSpeakerMode);
                AudioPlayer.getInstance().setSpeakerMode();
                if (enableSpeakerMode) {
                    ToastUtil.toastShortMessage(getResources().getString(R.string.chat_speaker_mode_off_tip));
                } else {
                    ToastUtil.toastShortMessage(getResources().getString(R.string.chat_speaker_mode_on_tip));
                }
            }
        });

        getMessageLayout().setChatDelegate(new MessageRecyclerView.ChatDelegate() {
            @Override
            public void displayBackToLastMessage(boolean display) {
                ChatView.this.displayBackToLastMessage(display);
            }

            @Override
            public void displayBackToNewMessage(boolean display, String messageId, int count) {
                ChatView.this.displayBackToNewMessage(display, messageId, count);
            }

            @Override
            public void loadMessageFinish() {
                initGroupAtInfoLayout();
            }

            @Override
            public void scrollMessageFinish() {
                if (mClickLastMessageShow && mMessageRecyclerView != null) {
                    mClickLastMessageShow = false;
                }
            }

            @Override
            public void hideSoftInput() {
                getInputLayout().hideSoftInput();
            }
        });

        getMessageLayout().setEmptySpaceClickListener(new OnEmptySpaceClickListener() {
            @Override
            public void onClick() {
                getInputLayout().onEmptyClick();
            }
        });

        getInputLayout().setChatInputHandler(new InputView.ChatInputHandler() {
            @Override
            public void onInputAreaClick() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (presenter != null) {
                            presenter.scrollToNewestMessage();
                        }
                    }
                });
            }

            @Override
            public void onRecordStatusChanged(int status) {
                switch (status) {
                    case RECORD_START:
                        startRecording();
                        break;
                    case RECORD_CONTINUE:
                        showContinueRecord();
                        break;
                    case RECORD_STOP:
                    case RECORD_CANCEL:
                        stopRecording();
                        break;
                    case RECORD_READY_TO_CANCEL:
                        readyToCancelRecord();
                        break;
                    case RECORD_TOO_SHORT:
                    case RECORD_FAILED:
                        stopAbnormally(status);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onUserTyping(boolean status, long curTime) {
                if (!TUIChatConfigMinimalist.isEnableTypingIndicator()) {
                    return;
                }

                if (!isSupportTyping) {
                    if (!isSupportTyping(curTime)) {
                        TUIChatLog.d(TAG, "onUserTyping trigger condition not met");
                        return;
                    } else {
                        isSupportTyping = true;
                    }
                }

                if (!status) {
                    sendTypingStatusMessage(false);
                    return;
                }

                if (lastTypingTime != 0) {
                    if ((curTime - lastTypingTime) < TUIChatConstants.TYPING_SEND_MESSAGE_INTERVAL) {
                        return;
                    }
                }

                lastTypingTime = curTime;
                sendTypingStatusMessage(true);
            }

            private void startRecording() {
                post(() -> {
                    AudioPlayer.getInstance().stopPlay();
                    mRecordingGroup.setVisibility(View.VISIBLE);
                    mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.left_cancle_send));
                });
            }

            private void showContinueRecord() {
                post(() -> mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.left_cancle_send)));
            }

            private void stopRecording() {
                post(() -> mRecordingGroup.setVisibility(View.GONE));
            }

            private void stopAbnormally(final int status) {
                post(() -> {
                    if (status == RECORD_TOO_SHORT) {
                        mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.say_time_short));
                    } else {
                        mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.record_fail));
                    }
                });
                postDelayed(() -> mRecordingGroup.setVisibility(View.GONE), 500);
            }

            private void readyToCancelRecord() {
                post(() -> mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.up_cancle_send)));
            }
        });

        forwardCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForwardState();
            }
        });
        mInputView.setChatInputMoreListener(new ChatInputMoreListener() {
            @Override
            public String sendMessage(TUIMessageBean msg, IUIKitCallback<TUIMessageBean> callback) {
                return ChatView.this.sendMessage(msg, false, callback);
            }
        });
    }

    private void displayBackToNewMessage(boolean display, String messageId, int count) {
        if (display) {
            displayBackToNewMessages(messageId, count);
        } else {
            mJumpNewMessageShow = false;
            hideJumpMessageLayouts();
        }
    }

    private void displayBackToLastMessage(boolean display) {
        if (mJumpNewMessageShow) {
            return;
        }
        if (display) {
            if (mAdapter != null) {
                displayBackToLastMessages();
            } else {
                TUIChatLog.e(TAG, "displayJumpLayout mAdapter is null");
            }
        } else {
            hideJumpMessageLayouts();
        }
    }

    public boolean isSupportTyping(long time) {
        return presenter.isSupportTyping(time);
    }

    @Override
    public void initDefault(DeskTUIBaseChatMinimalistFragment fragment) {
        getInputLayout().setMessageHandler(new InputView.MessageHandler() {
            @Override
            public void sendMessage(TUIMessageBean msg) {
                if (msg instanceof ReplyMessageBean) {
                    ChatView.this.sendReplyMessage(msg, false);
                } else {
                    ChatView.this.sendMessage(msg, false);
                }
            }

            @Override
            public void sendMessages(List<TUIMessageBean> messageBeans) {
                presenter.sendMessages(messageBeans);
            }

            @Override
            public void scrollToEnd() {
                ChatView.this.scrollToEnd();
            }
        });
        if (getMessageLayout().getAdapter() == null) {
            mAdapter = new MessageAdapter();
            mAdapter.setFragment(fragment);
            mMessageRecyclerView.setAdapter(mAdapter);
        }
        setCustomTopView();
        initListener();
        resetForwardState();
    }

    private void setCustomTopView() {
        View customNoticeLayout = TUIChatConfigs.getNoticeLayoutConfig().getCustomNoticeLayout();
        FrameLayout customView = getCustomView();
        if (customNoticeLayout != null && customView.getVisibility() == View.GONE) {
            ViewParent viewParent = customNoticeLayout.getParent();
            if (viewParent instanceof ViewGroup) {
                ViewGroup parentView = (ViewGroup) viewParent;
                parentView.removeAllViews();
            }
            customView.addView(customNoticeLayout);
            customView.setVisibility(View.VISIBLE);
        }
    }

    public void scrollToEnd() {
        getMessageLayout().scrollToEnd();
    }

    protected void deleteMessage(TUIMessageBean msg) {
        presenter.deleteMessage(msg);
    }

    protected void deleteMessages(final List<Integer> positions) {
        presenter.deleteMessages(positions);
    }

    protected void deleteMessageInfos(final List<TUIMessageBean> msgIds) {
        presenter.deleteMessageInfos(msgIds);
    }

    protected boolean checkFailedMessageInfos(final List<TUIMessageBean> msgIds) {
        return presenter.checkFailedMessageInfos(msgIds);
    }

    protected void revokeMessage(TUIMessageBean msg) {
        presenter.revokeMessage(msg);
    }

    protected void multiSelectMessage(TUIMessageBean msg) {
        if (mAdapter != null) {
            mInputView.hideSoftInput();
            mAdapter.setShowMultiSelectCheckBox(true);
            mAdapter.setOnCheckListChangedListener(new MessageAdapter.OnCheckListChangedListener() {
                @Override
                public void onCheckListChanged(List<TUIMessageBean> checkedList) {
                    int size = checkedList != null ? checkedList.size() : 0;
                    forwardText.setText(String.format(Locale.US, getResources().getString(R.string.chat_forward_checked_num), size));
                }
            });
            mAdapter.setItemChecked(msg, true);
            mAdapter.notifyDataSetChanged();
            setTitleBarWhenMultiSelectMessage();
        }
    }

    public void forwardMessage(TUIMessageBean msg) {
        if (mAdapter != null) {
            mInputView.hideSoftInput();
            mAdapter.setItemChecked(msg, true);
            mAdapter.notifyDataSetChanged();
            showForwardDialog(false, true);
        }
    }

    public void forwardText(String text) {
        showForwardTextDialog(text);
    }

    // Reply Message need MessageRootId
    protected void replyMessage(TUIMessageBean messageBean) {
        ReplyPreviewBean replyPreviewBean = ChatMessageBuilder.buildReplyPreviewBean(messageBean);
        mInputView.showReplyPreview(replyPreviewBean);
    }

    // Quote Message not need MessageRootId
    protected void quoteMessage(TUIMessageBean messageBean) {
        ReplyPreviewBean replyPreviewBean = ChatMessageBuilder.buildReplyPreviewBean(messageBean);
        replyPreviewBean.setMessageRootID(null);
        mInputView.showReplyPreview(replyPreviewBean);
    }

    protected void showMessageInfo(TUIMessageBean messageBean) {
        Intent intent = new Intent(getContext(), DeskMessageDetailMinimalistActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(TUIChatConstants.MESSAGE_BEAN, messageBean);
        intent.putExtra(TUIChatConstants.CHAT_INFO, presenter.getChatInfo());
        getContext().startActivity(intent);
    }

    private void resetForwardState() {
        if (mAdapter != null) {
            mAdapter.setShowMultiSelectCheckBox(false);
            mAdapter.notifyDataSetChanged();
        }
        forwardArea.setVisibility(GONE);
        forwardText.setText("");

        boolean enableMainPageInputBar = TUIChatConfigMinimalist.isShowInputBar();
        getInputLayout().setVisibility(enableMainPageInputBar ? VISIBLE : GONE);
    }

    private void setTitleBarWhenMultiSelectMessage() {
        getInputLayout().setVisibility(GONE);
        forwardArea.setVisibility(VISIBLE);
        requestFocus();
        forwardButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardSelectSheet = new ChatBottomSelectSheet(getContext());
                List<String> stringList = new ArrayList<>();
                String forwardOneByOne = getResources().getString(R.string.forward_mode_onebyone);
                String forwardMerge = getResources().getString(R.string.forward_mode_merge);
                stringList.add(forwardOneByOne);
                stringList.add(forwardMerge);
                forwardSelectSheet.setSelectList(stringList);
                forwardSelectSheet.setOnClickListener(new ChatBottomSelectSheet.BottomSelectSheetOnClickListener() {
                    @Override
                    public void onSheetClick(int index) {
                        if (index == 0) {
                            showForwardDialog(true, true);
                        } else if (index == 1) {
                            showForwardDialog(true, false);
                        }
                    }
                });
                forwardSelectSheet.show();
            }
        });

        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<TUIMessageBean> messageInfoList = mAdapter.getSelectedItem();
                if (messageInfoList == null || messageInfoList.isEmpty()) {
                    return;
                }
                TUIKitDialog tipsDialog = new TUIKitDialog(getContext())
                                              .builder()
                                              .setCancelable(true)
                                              .setCancelOutside(true)
                                              .setTitle(getContext().getString(R.string.chat_delete_msg_tip))
                                              .setDialogWidth(0.75f)
                                              .setPositiveButton(getContext().getString(com.tencent.qcloud.deskcore.R.string.sure),
                                                  new OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          final List<TUIMessageBean> messageInfoList = mAdapter.getSelectedItem();
                                                          deleteMessageInfos(messageInfoList);
                                                          resetForwardState();
                                                      }
                                                  })
                                              .setNegativeButton(getContext().getString(com.tencent.qcloud.deskcore.R.string.cancel), new OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {}
                                              });
                tipsDialog.show();
            }
        });
    }

    private void showForwardDialog(boolean isMultiSelect, boolean isOneByOne) {
        if (mAdapter == null) {
            return;
        }

        final List<TUIMessageBean> messageInfoList = mAdapter.getSelectedItem();

        if (messageInfoList == null || messageInfoList.isEmpty()) {
            ToastUtil.toastShortMessage(getContext().getString(R.string.forward_tip));
            return;
        }

        if (checkFailedMessageInfos(messageInfoList)) {
            ToastUtil.toastShortMessage(getContext().getString(R.string.forward_failed_tip));
            return;
        }

        if (!isMultiSelect) {
            mAdapter.setShowMultiSelectCheckBox(false);
        }

        if (isOneByOne) {
            if (messageInfoList.size() > FORWARD_MSG_NUM_LIMIT) {
                showForwardLimitDialog(messageInfoList);
            } else {
                startSelectForwardActivity(TUIChatConstants.FORWARD_MODE_ONE_BY_ONE, messageInfoList);
                resetForwardState();
            }
        } else {
            startSelectForwardActivity(TUIChatConstants.FORWARD_MODE_MERGE, messageInfoList);
            resetForwardState();
        }
    }

    private void showForwardTextDialog(String text) {
        if (mForwardSelectActivityListener != null) {
            mForwardSelectActivityListener.forwardText(text);
        }
    }

    private void showForwardLimitDialog(final List<TUIMessageBean> messageInfoList) {
        TUIKitDialog tipsDialog = new TUIKitDialog(getContext())
                                      .builder()
                                      .setCancelable(true)
                                      .setCancelOutside(true)
                                      .setTitle(getContext().getString(R.string.forward_oneByOne_limit_number_tip))
                                      .setDialogWidth(0.75f)
                                      .setPositiveButton(getContext().getString(R.string.forward_mode_merge),
                                          new OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  startSelectForwardActivity(TUIChatConstants.FORWARD_MODE_MERGE, messageInfoList);
                                                  resetForwardState();
                                              }
                                          })
                                      .setNegativeButton(getContext().getString(com.tencent.qcloud.deskcore.R.string.cancel), new OnClickListener() {
                                          @Override
                                          public void onClick(View v) {}
                                      });
        tipsDialog.show();
    }

    private void startSelectForwardActivity(int mode, List<TUIMessageBean> msgIds) {
        if (mForwardSelectActivityListener != null) {
            mForwardSelectActivityListener.forwardMessages(mode, msgIds);
        }
    }

    public void setForwardSelectActivityListener(ForwardSelectActivityListener listener) {
        this.mForwardSelectActivityListener = listener;
    }

    private String sendMessage(TUIMessageBean msg, boolean retry, IUIKitCallback<TUIMessageBean> callback) {
        return presenter.sendMessage(msg, retry, false, new IUIKitCallback<TUIMessageBean>() {
            @Override
            public void onSuccess(TUIMessageBean data) {
                TUIChatUtils.callbackOnSuccess(callback, data);
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scrollToEnd();
                    }
                });
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                TUIChatUtils.callbackOnError(callback, errCode, errMsg);
                String toastMsg = errMsg;
                if (errCode == TUIConstants.BuyingFeature.ERR_SDK_INTERFACE_NOT_SUPPORT) {
                    showNotSupportDialog();
                    if (msg.isNeedReadReceipt()) {
                        toastMsg = getResources().getString(R.string.chat_message_read_receipt)
                            + getResources().getString(com.tencent.qcloud.deskcore.R.string.TUIKitErrorUnsupporInterfaceSuffix);
                    }
                }
                ToastUtil.toastLongMessage(toastMsg);
            }

            @Override
            public void onProgress(Object data) {
                TUIChatUtils.callbackOnProgress(callback, data);
                ProgressPresenter.getInstance().updateProgress(msg.getId(), (Integer) data);
            }
        });
    }

    @Override
    public void sendMessage(TUIMessageBean msg, boolean retry) {
        sendMessage(msg, retry, null);
    }

    public void sendReplyMessage(TUIMessageBean msg, boolean retry) {
        presenter.sendMessage(msg, retry, false, new IUIKitCallback<TUIMessageBean>() {
            @Override
            public void onSuccess(TUIMessageBean data) {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scrollToEnd();
                    }
                });
                presenter.modifyRootMessageToAddReplyInfo((ReplyMessageBean) data, new IUIKitCallback<Void>() {
                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        ToastUtil.toastShortMessage("modify message failed code = " + errCode + " message = " + errMsg);
                    }
                });
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                String toastMsg = errCode + ", " + errMsg;
                if (errCode == TUIConstants.BuyingFeature.ERR_SDK_INTERFACE_NOT_SUPPORT) {
                    showNotSupportDialog();
                    if (msg.isNeedReadReceipt()) {
                        toastMsg = getResources().getString(R.string.chat_message_read_receipt)
                            + getResources().getString(com.tencent.qcloud.deskcore.R.string.TUIKitErrorUnsupporInterfaceSuffix);
                    }
                }
                ToastUtil.toastLongMessage(toastMsg);
            }
        });
    }

    public void setChatBackground(Drawable drawable) {
        chatBackgroundView.post(() -> {
            int vw = chatBackgroundView.getWidth();
            int vh = chatBackgroundView.getHeight();
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();
            float scale;
            float dx = 0;
            float dy = 0;

            if (dw * vh > vw * dh) {
                scale = (float) vh / (float) dh;
                dx = (vw - dw * scale) * 0.5f;
            } else {
                scale = (float) vw / (float) dw;
                dy = (vh - dh * scale) * 0.5f;
            }
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            matrix.postTranslate(Math.round(dx), Math.round(dy));
            chatBackgroundView.setImageMatrix(matrix);
            chatBackgroundView.setImageDrawable(drawable);
        });
    }

    public void sendTypingStatusMessage(boolean status) {
        if (mChatInfo == null || TextUtils.isEmpty(getChatInfo().getId())) {
            TUIChatLog.e(TAG, "sendTypingStatusMessage receiver is invalid");
            return;
        }

        Gson gson = new Gson();
        MessageTyping typingMessageBean = new MessageTyping();
        typingMessageBean.setTypingStatus(status);
        String data = gson.toJson(typingMessageBean);
        TUIMessageBean msg = ChatMessageBuilder.buildCustomMessage(data, "", null);

        presenter.sendTypingStatusMessage(msg, mChatInfo.getId(), new IUIKitCallback<TUIMessageBean>() {
            @Override
            public void onSuccess(TUIMessageBean data) {
                TUIChatLog.d(TAG, "sendTypingStatusMessage onSuccess:" + data.getId());
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                TUIChatLog.d(TAG, "sendTypingStatusMessage fail:" + errCode + "=" + errMsg);
            }
        });
    }

    public void exitChat() {
        AudioRecorder.cancelRecord();
        AudioPlayer.getInstance().stopPlay();
        presenter.markMessageAsRead(mChatInfo, false);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCustomView.removeAllViews();
        exitChat();
    }

    public interface ForwardSelectActivityListener {
        void forwardMessages(int forwardMode, List<TUIMessageBean> messageBeans);

        void forwardText(String text);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        // You will go to Chat from other interfaces, and you must also report a read receipt
        if (visibility == VISIBLE) {
            if (getMessageLayout() == null) {
                return;
            }
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getMessageLayout().getLayoutManager();
            if (linearLayoutManager == null) {
                return;
            }
            int firstVisiblePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            sendMsgReadReceipt(firstVisiblePosition, lastVisiblePosition);
            if (presenter != null) {
                presenter.markMessageAsRead(mChatInfo);
            }
        }
    }

    private void showNotSupportDialog() {
        String string = getResources().getString(R.string.chat_im_flagship_edition_update_tip, getResources().getString(R.string.chat_message_read_receipt));
        String buyingGuidelines = getResources().getString(R.string.chat_buying_guidelines);
        int buyingGuidelinesIndex = string.lastIndexOf(buyingGuidelines);
        final int foregroundColor = getResources().getColor(TUIThemeManager.getAttrResId(getContext(), com.tencent.qcloud.deskcore.R.attr.core_primary_color));
        SpannableString spannedString = new SpannableString(string);
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(foregroundColor);
        spannedString.setSpan(colorSpan2, buyingGuidelinesIndex, buyingGuidelinesIndex + buyingGuidelines.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (TextUtils.equals(TUIThemeManager.getInstance().getCurrentLanguage(), "zh")) {
                    openWebUrl(TUIConstants.BuyingFeature.BUYING_PRICE_DESC);
                } else {
                    openWebUrl(TUIConstants.BuyingFeature.BUYING_PRICE_DESC_EN);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        };
        spannedString.setSpan(clickableSpan2, buyingGuidelinesIndex, buyingGuidelinesIndex + buyingGuidelines.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        TUIKitDialog.TUIIMUpdateDialog.getInstance()
            .createDialog(getContext())
            .setShowOnlyDebug(true)
            .setMovementMethod(LinkMovementMethod.getInstance())
            .setHighlightColor(Color.TRANSPARENT)
            .setCancelable(true)
            .setCancelOutside(true)
            .setTitle(spannedString)
            .setDialogWidth(0.75f)
            .setDialogFeatureName(TUIConstants.BuyingFeature.BUYING_FEATURE_MESSAGE_RECEIPT)
            .setPositiveButton(getResources().getString(R.string.chat_no_more_reminders),
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TUIKitDialog.TUIIMUpdateDialog.getInstance().dismiss();
                        TUIKitDialog.TUIIMUpdateDialog.getInstance().setNeverShow(true);
                    }
                })
            .setNegativeButton(getResources().getString(R.string.chat_i_know),
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TUIKitDialog.TUIIMUpdateDialog.getInstance().dismiss();
                    }
                })
            .show();
    }

    private void openWebUrl(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri contentUrl = Uri.parse(url);
        intent.setData(contentUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }
}
