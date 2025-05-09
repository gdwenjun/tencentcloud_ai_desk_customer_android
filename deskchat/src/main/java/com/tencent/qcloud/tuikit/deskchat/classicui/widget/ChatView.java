package com.tencent.qcloud.tuikit.deskchat.classicui.widget;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMGroupAtInfo;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.deskcore.TUIThemeManager;
import com.tencent.qcloud.deskcore.interfaces.TUIExtensionInfo;
import com.tencent.qcloud.deskcore.util.ToastUtil;
import com.tencent.qcloud.tuikit.deskchat.classicui.page.DeskTUIBaseChatFragment;
import com.tencent.qcloud.tuikit.deskchat.classicui.page.DeskTUIC2CChatActivityDesk;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.SelectTextHelper;
import com.tencent.qcloud.tuikit.deskcommon.component.TitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.UnreadCountTextView;
import com.tencent.qcloud.tuikit.deskcommon.component.dialog.TUIKitDialog;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.ITitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuikit.deskcommon.interfaces.ChatInputMoreListener;
import com.tencent.qcloud.tuikit.deskcommon.interfaces.OnChatPopActionClickListener;
import com.tencent.qcloud.tuikit.deskcommon.util.ThreadUtils;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.TUIChatConstants;
import com.tencent.qcloud.tuikit.deskchat.TUIChatService;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.GroupApplyInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.MessageTyping;
import com.tencent.qcloud.tuikit.deskchat.bean.ReplyPreviewBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.CallingMessageBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.ReplyMessageBean;
import com.tencent.qcloud.tuikit.deskchat.classicui.component.noticelayout.NoticeLayout;
import com.tencent.qcloud.tuikit.deskchat.classicui.interfaces.IChatLayout;
import com.tencent.qcloud.tuikit.deskchat.classicui.widget.input.InputView;
import com.tencent.qcloud.tuikit.deskchat.classicui.widget.input.ShortcutView;
import com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.MessageAdapter;
import com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.MessageRecyclerView;
import com.tencent.qcloud.tuikit.deskchat.component.audio.AudioPlayer;
import com.tencent.qcloud.tuikit.deskchat.component.audio.AudioRecorder;
import com.tencent.qcloud.tuikit.deskchat.component.pinned.GroupPinnedView;
import com.tencent.qcloud.tuikit.deskchat.component.progress.ProgressPresenter;
import com.tencent.qcloud.tuikit.deskchat.config.ShortcutMenuConfig;
import com.tencent.qcloud.tuikit.deskchat.config.TUIChatConfigs;
import com.tencent.qcloud.tuikit.deskchat.config.classicui.TUIChatConfigClassic;
import com.tencent.qcloud.tuikit.deskchat.interfaces.OnEmptySpaceClickListener;
import com.tencent.qcloud.tuikit.deskchat.interfaces.OnGestureScrollListener;
import com.tencent.qcloud.tuikit.deskchat.interfaces.TotalUnreadCountListener;
import com.tencent.qcloud.tuikit.deskchat.presenter.C2CChatPresenter;
import com.tencent.qcloud.tuikit.deskchat.presenter.ChatPresenter;
import com.tencent.qcloud.tuikit.deskchat.presenter.GroupChatPresenter;
import com.tencent.qcloud.tuikit.deskchat.util.ChatMessageBuilder;
import com.tencent.qcloud.tuikit.deskchat.util.SoftHideKeyBoardUtil;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ChatView extends LinearLayout implements IChatLayout {
    private static final String TAG = ChatView.class.getSimpleName();

    // Limit the number of messages forwarded one by one
    private static final int FORWARD_MSG_NUM_LIMIT = 30;

    protected MessageAdapter mAdapter;
    private ForwardSelectActivityListener mForwardSelectActivityListener;
    private TotalUnreadCountListener unreadCountListener;

    private AnimationDrawable mVolumeAnim;
    private Runnable mTypingRunnable = null;
    private ChatInfo mChatInfo;
    public ChatPresenter.TypingListener mTypingListener = new ChatPresenter.TypingListener() {
        @Override
        public void onTyping(int status) {
            if (!TUIChatConfigClassic.isEnableTypingIndicator()) {
                return;
            }

            if (mChatInfo == null) {
                return;
            }

            TUIChatLog.d(TAG, "mTypingListener status= " + status);
            String oldTitle = mChatInfo.getChatName();
            if (status == 1) {
                if (isCustomer()) {
                    getTitleBar().getLeftTitle().setText(R.string.typing);
                } else {
                    getTitleBar().getMiddleTitle().setText(R.string.typing);
                }

                if (mTypingRunnable == null) {
                    mTypingRunnable = new Runnable() {
                        @Override
                        public void run() {
                            if (isCustomer()) {
                                getTitleBar().getLeftTitle().setText(oldTitle);
                            } else {
                                getTitleBar().getMiddleTitle().setText(R.string.typing);
                            }
                        }
                    };
                }
                getTitleBar().getMiddleTitle().removeCallbacks(mTypingRunnable);
                getTitleBar().getMiddleTitle().postDelayed(mTypingRunnable, TUIChatConstants.TYPING_PARSE_MESSAGE_INTERVAL * 1000);
            } else if (status == 0) {
                getTitleBar().getMiddleTitle().removeCallbacks(mTypingRunnable);
                getTitleBar().getMiddleTitle().setText(oldTitle);
            } else {
                TUIChatLog.e(TAG, "parseTypingMessage error status =" + status);
            }
        }
    };

    protected FrameLayout topExtensionLayout;
    protected GroupPinnedView groupPinnedView;
    protected FrameLayout mCustomView;
    protected NoticeLayout mGroupApplyLayout;
    protected View mRecordingGroup;
    protected ImageView mRecordingIcon;
    protected TextView mRecordingTips;
    protected TextView recordCountDownTv;
    private TitleBarLayout mTitleBar;
    private ImageView chatBackgroundView;
    private MessageRecyclerView mMessageRecyclerView;
    private InputView mInputView;
    private View flInputFloatLayout;
    private ViewGroup shortcutContainer;
    private NoticeLayout mNoticeLayout;
    private LinearLayout mJumpMessageLayout;
    private ImageView mArrowImageView;
    private TextView mJumpMessageTextView;
    private boolean mJumpNewMessageShow;
    private boolean mJumpGroupAtInfoShow;
    private boolean mClickLastMessageShow;
    private Timer recordCountDownTimer;

    private LinearLayout mForwardLayout;
    private View mForwardOneButton;
    private View mForwardMergeButton;
    private View mDeleteButton;
    private long lastTypingTime = 0;
    private boolean isSupportTyping = false;

    private ChatPresenter presenter;
    private int scrollDirection = 0;

    private View chatBg;

    private DeskTUIC2CChatActivityDesk chatActivity;

    public void setActivityInstance(DeskTUIC2CChatActivityDesk activity){
        chatActivity = activity;
        initViews();
    }

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
        inflate(getContext(), R.layout.desk_tuichat_chat_layout, this);
        mTitleBar = findViewById(R.id.chat_title_bar);
        chatBackgroundView = findViewById(R.id.chat_background_view);


        mMessageRecyclerView = findViewById(R.id.chat_message_layout);
        mInputView = findViewById(R.id.chat_input_layout);
        mInputView.setChatLayout(this);
        boolean enableMainPageInputBar = TUIChatConfigClassic.isShowInputBar();
        mInputView.setVisibility(enableMainPageInputBar ? VISIBLE : GONE);
        mRecordingGroup = findViewById(R.id.voice_recording_view);
        mRecordingIcon = findViewById(R.id.recording_icon);
        mRecordingTips = findViewById(R.id.recording_tips);
        recordCountDownTv = findViewById(R.id.voice_count_down_tv);
        mGroupApplyLayout = findViewById(R.id.chat_group_apply_layout);
        mNoticeLayout = findViewById(R.id.chat_notice_layout);
        mCustomView = findViewById(R.id.custom_layout);
        mCustomView.setVisibility(GONE);
        topExtensionLayout = findViewById(R.id.chat_top_extension_layout);
        topExtensionLayout.setVisibility(GONE);

        groupPinnedView = findViewById(R.id.group_pinned_message_view);

        flInputFloatLayout = findViewById(R.id.fl_input_float);
        shortcutContainer = findViewById(R.id.shortcut_container);

        mForwardLayout = findViewById(R.id.forward_layout);
        mForwardOneButton = findViewById(R.id.forward_one_by_one_button);
        mForwardMergeButton = findViewById(R.id.forward_merge_button);
        mDeleteButton = findViewById(R.id.delete_button);

        mJumpMessageLayout = findViewById(R.id.jump_message_layout);
        mJumpMessageTextView = findViewById(R.id.jump_message_content);
        mArrowImageView = findViewById(R.id.arrow_icon);
        mJumpNewMessageShow = false;
        hideJumpMessageLayouts();

        mTitleBar.getMiddleTitle().setEllipsize(TextUtils.TruncateAt.END);



        lastTypingTime = 0;

        setChatBg();
    }

    public void setChatBg(){

        int sourceID = TUIChatConfigs.getGeneralConfig().getChatBgSourceID();
        if(TUICore.getService(TUIConstants.Service.TUI_CUSTOMER_PLUGIN) != null && chatActivity!=null){
            mTitleBar.getRightIcon().setVisibility(GONE);
//            mTitleBar.getMiddleTitle().setVisibility(GONE);
//            mTitleBar.getLeftTitle().setEllipsize(TextUtils.TruncateAt.END);
            chatBg = findViewById(R.id.chat_bg);
            chatActivity.immersiveStatusBar();
            chatBg.setBackground(getContext().getResources().getDrawable(R.drawable.business_bg));
            if(sourceID == 0){
                chatBg.setBackground(getContext().getResources().getDrawable(R.drawable.business_bg));
            }else if(sourceID == 1){
                chatBg.setBackground(getContext().getResources().getDrawable(R.drawable.finance_bg));
            }else if (sourceID == 2){
                chatBg.setBackground(getContext().getResources().getDrawable(R.drawable.service));
            }


            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mTitleBar.getLayoutParams();
            layoutParams.topMargin = chatActivity.getStatusBarHeight(); // 设置marginTop的值为32px
            mTitleBar.setLayoutParams(layoutParams);
            mTitleBar.setBackgroundColor(Color.TRANSPARENT);
            findViewById(R.id.view_line).setVisibility(GONE);
            flInputFloatLayout.setBackgroundColor(Color.TRANSPARENT);
            flInputFloatLayout.setPadding(20,20,20,20);
            SoftHideKeyBoardUtil.assistActivity(chatActivity, chatActivity.getStatusBarHeight());
        }
    }

    public void displayBackToLastMessages() {
        mJumpMessageLayout.setVisibility(VISIBLE);
        mArrowImageView.setBackgroundResource(TUIThemeManager.getAttrResId(getContext(), R.attr.chat_jump_recent_down_icon));
        mJumpMessageTextView.setText(getContext().getString(R.string.back_to_lastmessage));
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
        mArrowImageView.setBackgroundResource(TUIThemeManager.getAttrResId(getContext(), R.attr.chat_jump_recent_down_icon));
        mJumpMessageTextView.setText(String.valueOf(count) + getContext().getString(R.string.back_to_newmessage));
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

    public void displayBackToAtMessages(V2TIMGroupAtInfo groupAtInfo) {
        mJumpGroupAtInfoShow = true;
        mJumpMessageLayout.setVisibility(VISIBLE);
        mArrowImageView.setBackgroundResource(TUIThemeManager.getAttrResId(getContext(), R.attr.chat_jump_recent_up_icon));
        if (groupAtInfo.getAtType() == V2TIMGroupAtInfo.TIM_AT_ALL) {
            mJumpMessageTextView.setText(getContext().getString(R.string.back_to_atmessage_all));
        } else {
            mJumpMessageTextView.setText(getContext().getString(R.string.back_to_atmessage_me));
        }
        mJumpMessageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                locateOriginMessageBySeq(groupAtInfo.getSeq());
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

    private boolean isCustomer(){
        boolean res = false;
        if(TUICore.getService(TUIConstants.Service.TUI_CUSTOMER_PLUGIN)!=null){
            List<String> userCount =  (List<String>) TUICore.getService(TUIConstants.Service.TUI_CUSTOMER_PLUGIN).onCall(TUIConstants.TUICustomerServicePlugin.METHOD_GET_CUSTOMER_SERVICE_ACCOUNTS,null);
            if(mChatInfo != null && mChatInfo.getId() != null && userCount.contains(mChatInfo.getId())){
                res = true;
            }
        }
        return  res;
    }

    private void initGroupAtInfoLayout() {
        if (mChatInfo != null) {
            List<V2TIMGroupAtInfo> groupAtInfos = mChatInfo.getAtInfoList();
            if (groupAtInfos != null && groupAtInfos.size() > 0) {
                V2TIMGroupAtInfo groupAtInfo = groupAtInfos.get(0);
                if (groupAtInfo != null) {
                    displayBackToAtMessages(groupAtInfo);
                } else {
                    mJumpGroupAtInfoShow = false;
                    hideJumpMessageLayouts();
                }
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            SelectTextHelper.resetSelected();
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setChatInfo(ChatInfo chatInfo) {
        mChatInfo = chatInfo;
        if (chatInfo == null) {
            return;
        }
        mInputView.setChatInfo(chatInfo);
        setChatName();
        setChatHandler();
        if (isCustomer()) {
            setFaceUrl();
            this.getInputLayout().setCustomerInputStyle();
        }

        if (!TUIChatUtils.isC2CChat(chatInfo.getType())) {
            loadApplyList();
            mGroupApplyLayout.setOnNoticeClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(TUIChatConstants.GROUP_ID, chatInfo.getId());
                    TUICore.startActivity(getContext(), "GroupApplyManagerActivity", bundle);
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
                SelectTextHelper.resetSelected();
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
        setTotalUnread();
        initExtension();
        setShortcutView();
        if (isCustomer()) {
            mMessageRecyclerView.setCustomerBubbleBg();
        }
    }

    private void initExtension() {

        Map<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUIChat.CHAT_ID, mChatInfo.getId());
        param.put(TUIConstants.TUIChat.CHAT_TYPE, mChatInfo.getType());
        List<TUIExtensionInfo> extensionInfoList = TUICore.getExtensionList(TUIConstants.TUIChat.Extension.ChatView.GET_CONFIG_PARAMS, param);
        if (extensionInfoList != null && extensionInfoList.size() > 0) {
            TUIExtensionInfo extensionInfo = extensionInfoList.get(0);
            if (extensionInfo.getData() != null) {
                if (extensionInfo.getData().containsKey(TUIConstants.TUIChat.Extension.ChatView.MESSAGE_NEED_READ_RECEIPT)) {
                    boolean needReadReceipt = (Boolean) extensionInfo.getData().get(TUIConstants.TUIChat.Extension.ChatView.MESSAGE_NEED_READ_RECEIPT);
                    mChatInfo.setNeedReadReceipt(needReadReceipt);
                }

                if (extensionInfo.getData().containsKey(TUIConstants.TUIChat.Extension.ChatView.ENABLE_VIDEO_CALL)) {
                    boolean enableVideoCall = (Boolean) extensionInfo.getData().get(TUIConstants.TUIChat.Extension.ChatView.ENABLE_VIDEO_CALL);
                    mChatInfo.setEnableVideoCall(enableVideoCall);
                }

                if (extensionInfo.getData().containsKey(TUIConstants.TUIChat.Extension.ChatView.ENABLE_AUDIO_CALL)) {
                    boolean enableAudioCall = (Boolean) extensionInfo.getData().get(TUIConstants.TUIChat.Extension.ChatView.ENABLE_AUDIO_CALL);
                    mChatInfo.setEnableAudioCall(enableAudioCall);
                }

                if (extensionInfo.getData().containsKey(TUIConstants.TUIChat.Extension.ChatView.ENABLE_CUSTOM_HELLO_MESSAGE)) {
                    boolean enableCustomHelloMessage =
                        (Boolean) extensionInfo.getData().get(TUIConstants.TUIChat.Extension.ChatView.ENABLE_CUSTOM_HELLO_MESSAGE);
                    mChatInfo.setEnableCustomHelloMessage(enableCustomHelloMessage);
                }
            }
        }

        HashMap<String, Object> raiseExtensionParam = new HashMap<>();
        raiseExtensionParam.put(TUIChatConstants.CHAT_INFO, mChatInfo);
        TUICore.raiseExtension(TUIConstants.TUIChat.Extension.InputViewFloatLayer.CLASSIC_EXTENSION_ID, flInputFloatLayout, raiseExtensionParam);

        // chat top extension
        Map<String, Object> topExtensionParam = new HashMap<>();
        topExtensionParam.put(
            TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.VIEW_TYPE, TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.VIEW_TYPE_CLASSIC);
        topExtensionParam.put(TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.CHAT_ID, mChatInfo.getId());
        topExtensionParam.put(TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.IS_GROUP, ChatInfo.TYPE_GROUP == mChatInfo.getType());
        TUICore.raiseExtension(TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.EXTENSION_ID, topExtensionLayout, topExtensionParam);
    }

    private void setShortcutView() {
        ShortcutMenuConfig.ChatShortcutViewDataSource dataSource = ShortcutMenuConfig.getShortcutViewDataSource();
        if (dataSource != null) {
            List<ShortcutMenuConfig.TUIChatShortcutMenuData> dataList = dataSource.itemsInShortcutViewOfInfo(mChatInfo);
            if (dataList != null && !dataList.isEmpty()) {
                shortcutContainer.setVisibility(VISIBLE);
            } else {
                shortcutContainer.setVisibility(GONE);
                return;
            }
            Drawable drawable = dataSource.shortcutViewBackgroundOfInfo(mChatInfo);
            if (drawable != null) {
                shortcutContainer.setBackground(drawable);
            }
            ShortcutView shortcutView = new ShortcutView(getContext());
            shortcutView.setDataList(dataList);
            shortcutContainer.addView(shortcutView);
        }
    }

    private void setChatName() {
        if (!TextUtils.isEmpty(mChatInfo.getChatName())) {
            getTitleBar().setTitle(mChatInfo.getChatName(), isCustomer()? ITitleBarLayout.Position.LEFT : ITitleBarLayout.Position.MIDDLE);
        } else {
            presenter.getChatName(mChatInfo.getId(), new IUIKitCallback<String>() {
                @Override
                public void onSuccess(String data) {
                    mChatInfo.setChatName(data);
                    getTitleBar().setTitle(mChatInfo.getChatName(), isCustomer()? ITitleBarLayout.Position.LEFT : ITitleBarLayout.Position.MIDDLE);
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    getTitleBar().setTitle(mChatInfo.getId(), isCustomer()? ITitleBarLayout.Position.LEFT : ITitleBarLayout.Position.MIDDLE);
                }
            });
        }
    }

    private void setFaceUrl() {
        if (!TextUtils.isEmpty(mChatInfo.getFaceUrl())) {
            getTitleBar().setAvatar(mChatInfo.getFaceUrl());
        } else {
            presenter.getChatFaceUrl(mChatInfo.getId(), new IUIKitCallback<List<Object>>() {

                @Override
                public void onSuccess(List<Object> data) {
                    super.onSuccess(data);
                    if (data != null && data.size() == 1 ) {
                        mChatInfo.setFaceUrl(data.get(0).toString());
                        getTitleBar().setAvatar(mChatInfo.getFaceUrl());
                    }
                }
                @Override
                public void onError(String module, int errCode, String errMsg) {
                    getTitleBar().setAvatar(mChatInfo.getFaceUrl());
                }

            });
        }
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

    private void setTotalUnread() {
        UnreadCountTextView unreadCountTextView = mTitleBar.getUnreadCountTextView();
        unreadCountTextView.setPaintColor(getResources().getColor(TUIThemeManager.getAttrResId(getContext(), R.attr.chat_unread_dot_bg_color)));
        unreadCountTextView.setTextColor(getResources().getColor(TUIThemeManager.getAttrResId(getContext(), R.attr.chat_unread_dot_text_color)));
        long unreadCount = 0;
        Object result = TUICore.callService(TUIConstants.TUIConversation.SERVICE_NAME, TUIConstants.TUIConversation.METHOD_GET_TOTAL_UNREAD_COUNT, null);
        if (result != null && result instanceof Long) {
            unreadCount =
                (long) TUICore.callService(TUIConstants.TUIConversation.SERVICE_NAME, TUIConstants.TUIConversation.METHOD_GET_TOTAL_UNREAD_COUNT, null);
        }
        updateUnreadCount(unreadCountTextView, unreadCount);
        unreadCountListener = new TotalUnreadCountListener() {
            @Override
            public void onTotalUnreadCountChanged(long totalUnreadCount) {
                updateUnreadCount(unreadCountTextView, totalUnreadCount);
            }
        };
        TUIChatService.getInstance().addUnreadCountListener(unreadCountListener);
    }

    private void updateUnreadCount(UnreadCountTextView unreadCountTextView, long count) {
        if (count <= 0) {
            unreadCountTextView.setVisibility(GONE);
        } else {
            unreadCountTextView.setVisibility(VISIBLE);
            String unreadCountStr = count + "";
            if (count > 99) {
                unreadCountStr = "99+";
            }
            unreadCountTextView.setText(unreadCountStr);
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
                    ChatView.this.onFriendNameChanged(newName);
                }
            });
        }
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
        getTitleBar().setTitle(newName, ITitleBarLayout.Position.MIDDLE);
    }

    public void onFriendNameChanged(String newName) {
        getTitleBar().setTitle(newName, ITitleBarLayout.Position.MIDDLE);
    }

    public void onApplied(int size) {
        if (size <= 0) {
            mGroupApplyLayout.setVisibility(View.GONE);
        } else {
            mGroupApplyLayout.getContent().setText(getContext().getString(R.string.group_apply_tips, size));
            mGroupApplyLayout.setVisibility(View.VISIBLE);
        }
    }

    public void loadPinnedMessage() {
        if (presenter instanceof GroupChatPresenter) {
            groupPinnedView.setGroupChatPresenter((GroupChatPresenter) presenter);
            ((GroupChatPresenter) presenter).setGroupPinnedView(groupPinnedView);
            ((GroupChatPresenter) presenter).loadPinnedMessage(getChatInfo().getId());
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

    public LinearLayout getForwardLayout() {
        return mForwardLayout;
    }

    public View getForwardOneButton() {
        return mForwardOneButton;
    }

    public View getDeleteButton() {
        return mDeleteButton;
    }

    public View getForwardMergeButton() {
        return mForwardMergeButton;
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

    @Override
    public TitleBarLayout getTitleBar() {
        return mTitleBar;
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
                TUIKitDialog tipsDialog =
                    new TUIKitDialog(getContext())
                        .builder()
                        .setCancelable(true)
                        .setCancelOutside(true)
                        .setTitle(getContext().getString(R.string.chat_delete_msg_tip))
                        .setDialogWidth(0.75f)
                        .setPositiveButton(getContext().getString(com.tencent.qcloud.deskcore.R.string.sure),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteMessage(msg);
                                }
                            })
                        .setNegativeButton(getContext().getString(com.tencent.qcloud.deskcore.R.string.cancel), new View.OnClickListener() {
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
            public void onSpeakerModeSwitchClick(TUIMessageBean msg) {
                boolean enableSpeakerMode = TUIChatConfigs.getGeneralConfig().isEnableSoundMessageSpeakerMode();
                TUIChatConfigClassic.setPlayingSoundMessageViaSpeakerByDefault(!enableSpeakerMode);
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
                        showRecordingAnim();
                        startCountDown();
                        break;
                    case RECORD_STOP:
                    case RECORD_CANCEL:
                        stopRecordingAnim();
                        break;
                    case RECORD_READY_TO_CANCEL:
                        showRecordingPauseAnim();
                        break;
                    case RECORD_TOO_SHORT:
                    case RECORD_FAILED:
                        stopAbnormally(status);
                        break;
                    case RECORD_CONTINUE:
                        showContinueAnim();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onUserTyping(boolean status, long curTime) {
                if (!TUIChatConfigClassic.isEnableTypingIndicator()) {
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

            private void showRecordingAnim() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        AudioPlayer.getInstance().stopPlay();
                        mRecordingGroup.setVisibility(View.VISIBLE);
                        mRecordingIcon.setImageResource(R.drawable.recording_volume);
                        mVolumeAnim = (AnimationDrawable) mRecordingIcon.getDrawable();
                        mVolumeAnim.start();
                        mRecordingTips.setTextColor(Color.WHITE);
                        mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.down_cancle_send));
                    }
                });
            }

            private void showContinueAnim() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        mRecordingIcon.setImageResource(R.drawable.recording_volume);
                        mVolumeAnim = (AnimationDrawable) mRecordingIcon.getDrawable();
                        mVolumeAnim.start();
                        mRecordingTips.setTextColor(Color.WHITE);
                        mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.down_cancle_send));
                    }
                });
            }

            private void stopRecordingAnim() {
                stopCountDown();
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (mVolumeAnim != null) {
                            mVolumeAnim.stop();
                        }
                        mRecordingGroup.setVisibility(View.GONE);
                    }
                });
            }

            private void stopAbnormally(final int status) {
                stopCountDown();
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (mVolumeAnim != null) {
                            mVolumeAnim.stop();
                        }
                        mRecordingIcon.setImageResource(R.drawable.ic_volume_dialog_length_short);
                        mRecordingTips.setTextColor(Color.WHITE);
                        if (status == RECORD_TOO_SHORT) {
                            mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.say_time_short));
                        } else {
                            mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.record_fail));
                        }
                    }
                });
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecordingGroup.setVisibility(View.GONE);
                    }
                }, 500);
            }

            private void showRecordingPauseAnim() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        mRecordingIcon.setImageResource(R.drawable.ic_volume_dialog_cancel);
                        mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.up_cancle_send));
                    }
                });
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

    private void startCountDown() {
        stopCountDown();
        recordCountDownTimer = new Timer();
        RecordCountDownTask task = new RecordCountDownTask();
        task.setTextView(recordCountDownTv);
        recordCountDownTimer.schedule(task, 0, 1000);
    }

    private void stopCountDown() {
        if (recordCountDownTimer != null) {
            recordCountDownTimer.cancel();
            recordCountDownTimer = null;
        }
    }

    public boolean isSupportTyping(long time) {
        return presenter.isSupportTyping(time);
    }

    @Override
    public void initDefault(DeskTUIBaseChatFragment fragment) {
        getTitleBar().getLeftGroup().setVisibility(View.VISIBLE);
        getTitleBar().setOnLeftClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        });
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
        resetForwardState("");
    }

    private void setCustomTopView() {
        // Set custom view of chat interface as security prompt
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

    @Override
    public void setParentLayout(Object parentContainer) {}

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
            mAdapter.setItemChecked(msg, !msg.hasRiskContent());
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

    private void resetTitleBar(String leftTitle) {
        getTitleBar().getRightGroup().setVisibility(VISIBLE);

        getTitleBar().getLeftGroup().setVisibility(View.VISIBLE);
        getTitleBar().getLeftIcon().setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(leftTitle)) {
            getTitleBar().setTitle(leftTitle, ITitleBarLayout.Position.LEFT);
        } else {
            getTitleBar().setTitle("", TitleBarLayout.Position.LEFT);
        }
        getTitleBar().setOnLeftClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        });

        getForwardLayout().setVisibility(GONE);
        boolean enableMainPageInputBar = TUIChatConfigClassic.isShowInputBar();
        getInputLayout().setVisibility(enableMainPageInputBar ? VISIBLE : GONE);
    }

    private void resetForwardState(String leftTitle) {
        if (mAdapter != null) {
            mAdapter.setShowMultiSelectCheckBox(false);
            mAdapter.notifyDataSetChanged();
        }

        resetTitleBar(leftTitle);
    }

    private void setTitleBarWhenMultiSelectMessage() {
        getTitleBar().getRightGroup().setVisibility(GONE);

        boolean enableForwardMessage = true;
        Map<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUIChat.Extension.MultiSelectMessageBar.USER_ID, mChatInfo.getId());
        List<TUIExtensionInfo> extensionList = TUICore.getExtensionList(TUIConstants.TUIChat.Extension.MultiSelectMessageBar.CLASSIC_EXTENSION_ID, param);
        for (TUIExtensionInfo info : extensionList) {
            Map<String, Object> extensionMap = info.getData();
            if (!extensionMap.isEmpty()) {
                Object enableForwardMessageObj = extensionMap.get(TUIConstants.TUIChat.Extension.MultiSelectMessageBar.ENABLE_FORWARD_MESSAGE);
                if (enableForwardMessageObj instanceof Boolean) {
                    enableForwardMessage = (Boolean) enableForwardMessageObj;
                    break;
                }
            }
        }

        if (!enableForwardMessage) {
            mForwardOneButton.setVisibility(View.GONE);
            mForwardMergeButton.setVisibility(View.GONE);
        }

        getTitleBar().getLeftGroup().setVisibility(View.VISIBLE);
        getTitleBar().getLeftIcon().setVisibility(GONE);
        final CharSequence leftTitle = getTitleBar().getLeftTitle().getText();
        getTitleBar().setTitle(getContext().getString(com.tencent.qcloud.deskcore.R.string.cancel), TitleBarLayout.Position.LEFT);
        getTitleBar().setOnLeftClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForwardState(leftTitle.toString());
            }
        });
        getInputLayout().setVisibility(GONE);
        getForwardLayout().setVisibility(VISIBLE);
        getForwardOneButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForwardDialog(true, true);
            }
        });
        getForwardMergeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForwardDialog(true, false);
            }
        });
        getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TUIMessageBean> msgIds = mAdapter.getSelectedItem();

                if (msgIds == null || msgIds.isEmpty()) {
                    ToastUtil.toastShortMessage("please select message!");
                    return;
                }

                deleteMessageInfos(msgIds);

                resetForwardState(leftTitle.toString());
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

        for (TUIMessageBean tuiMessageBean : messageInfoList) {
            if (!tuiMessageBean.isEnableForward()) {
                ToastUtil.toastShortMessage(getContext().getString(R.string.forward_group_note_or_poll_failed_tip));
                return;
            }
        }

        if (!isMultiSelect) {
            mAdapter.setShowMultiSelectCheckBox(false);
        }

        if (isOneByOne) {
            if (messageInfoList.size() > FORWARD_MSG_NUM_LIMIT) {
                showForwardLimitDialog(messageInfoList);
            } else {
                startSelectForwardActivity(TUIChatConstants.FORWARD_MODE_ONE_BY_ONE, messageInfoList);
                resetForwardState("");
            }
        } else {
            startSelectForwardActivity(TUIChatConstants.FORWARD_MODE_MERGE, messageInfoList);
            resetForwardState("");
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
                                          new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  startSelectForwardActivity(TUIChatConstants.FORWARD_MODE_MERGE, messageInfoList);
                                                  resetForwardState("");
                                              }
                                          })
                                      .setNegativeButton(getContext().getString(com.tencent.qcloud.deskcore.R.string.cancel), new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {}
                                      });
        tipsDialog.show();
    }

    private void startSelectForwardActivity(int mode, List<TUIMessageBean> messageBeans) {
        if (mForwardSelectActivityListener != null) {
            mForwardSelectActivityListener.forwardMessages(mode, messageBeans);
        }
    }

    public void setForwardSelectActivityListener(ForwardSelectActivityListener listener) {
        this.mForwardSelectActivityListener = listener;
    }

    private String sendMessage(TUIMessageBean messageBean, boolean retry, IUIKitCallback<TUIMessageBean> callback) {
        return presenter.sendMessage(messageBean, retry, false, new IUIKitCallback<TUIMessageBean>() {
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
                    if (messageBean.isNeedReadReceipt()) {
                        toastMsg = getResources().getString(R.string.chat_message_read_receipt)
                            + getResources().getString(com.tencent.qcloud.deskcore.R.string.TUIKitErrorUnsupporInterfaceSuffix);
                    }
                }
                ToastUtil.toastLongMessage(toastMsg);
            }

            @Override
            public void onProgress(Object data) {
                TUIChatUtils.callbackOnProgress(callback, data);
                ProgressPresenter.updateProgress(messageBean.getId(), (Integer) data);
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
                        TUIChatLog.e(TAG, "modify message failed code = " + errCode + " message = " + errMsg);
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
        TUIChatLog.d(TAG, "sendTypingStatusMessage data = " + data);
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
        getTitleBar().getMiddleTitle().removeCallbacks(mTypingRunnable);
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
        void forwardMessages(int mode, List<TUIMessageBean> messageBeans);

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
        final int foregroundColor = getResources().getColor(TUIThemeManager.getAttrResId(getContext(), com.tencent.qcloud.deskcore.R.attr.core_primary_color));

        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(foregroundColor);
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
        TUIKitDialog.TUIIMUpdateDialog.getInstance()
            .createDialog(getContext())
            .setShowOnlyDebug(true)
            .setMovementMethod(LinkMovementMethod.getInstance())
            .setHighlightColor(Color.TRANSPARENT)
            .setCancelable(true)
            .setCancelOutside(true)
            .setTitle(string)
            .setDialogWidth(0.75f)
            .setDialogFeatureName(TUIConstants.BuyingFeature.BUYING_FEATURE_MESSAGE_RECEIPT)
            .setPositiveButton(getResources().getString(R.string.chat_no_more_reminders),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TUIKitDialog.TUIIMUpdateDialog.getInstance().dismiss();
                        TUIKitDialog.TUIIMUpdateDialog.getInstance().setNeverShow(true);
                    }
                })
            .setNegativeButton(getResources().getString(R.string.chat_i_know),
                new View.OnClickListener() {
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

    static class RecordCountDownTask extends TimerTask {
        WeakReference<TextView> textView;
        int count = 60;

        public void setTextView(TextView textView) {
            this.textView = new WeakReference<>(textView);
        }

        @Override
        public void run() {
            ThreadUtils.runOnUiThread(() -> {
                if (count < 0) {
                    return;
                }
                if (textView != null && textView.get() != null) {
                    textView.get().setText(count + "''");
                    count--;
                }
            });
        }
    }
}
