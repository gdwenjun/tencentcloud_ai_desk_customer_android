package com.tencent.qcloud.tuikit.deskchat.classicui.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.component.CustomLinearLayoutManager;
import com.tencent.qcloud.tuikit.deskcommon.component.TitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.activities.BaseLightActivity;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.ITitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.interfaces.OnItemClickListener;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.TUIChatConstants;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskchat.bean.message.MergeMessageBean;
import com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.MessageAdapter;
import com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.MessageRecyclerView;
import com.tencent.qcloud.tuikit.deskchat.presenter.ForwardPresenter;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class DeskTUIForwardChatActivity extends BaseLightActivity {
    private static final String TAG = DeskTUIForwardChatActivity.class.getSimpleName();

    private TitleBarLayout mTitleBar;
    private MessageRecyclerView mFowardChatMessageRecyclerView;
    private MessageAdapter mForwardChatAdapter;

    private MergeMessageBean mMessageInfo;
    private ChatInfo chatInfo;
    private String mTitle;

    private ForwardPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desk_forward_chat_layout);
        mFowardChatMessageRecyclerView = findViewById(R.id.chat_message_layout);
        mFowardChatMessageRecyclerView.setLayoutManager(new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mForwardChatAdapter = new MessageAdapter();
        mForwardChatAdapter.setForwardMode(true);
        presenter = new ForwardPresenter();
        presenter.initListener();
        presenter.setMessageListAdapter(mForwardChatAdapter);
        presenter.setNeedShowBottom(false);
        mForwardChatAdapter.setPresenter(presenter);

        mFowardChatMessageRecyclerView.setAdapter(mForwardChatAdapter);
        mFowardChatMessageRecyclerView.setPresenter(presenter);

        mTitleBar = findViewById(R.id.chat_title_bar);
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mFowardChatMessageRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onUserIconClick(View view, TUIMessageBean messageInfo) {
                if (!(messageInfo instanceof MergeMessageBean)) {
                    return;
                }

                Intent intent = new Intent(getBaseContext(), DeskTUIForwardChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(TUIChatConstants.FORWARD_MERGE_MESSAGE_KEY, messageInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onMessageClick(View view, TUIMessageBean messageBean) {
                if (messageBean instanceof MergeMessageBean) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(TUIChatConstants.FORWARD_MERGE_MESSAGE_KEY, messageBean);
                    bundle.putSerializable(TUIChatConstants.CHAT_INFO, chatInfo);
                    TUICore.startActivity("TUIForwardChatActivity", bundle);
                }
            }
        });

        init();
    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = getString(R.string.forward_chat_record);
            mTitleBar.setTitle(mTitle, ITitleBarLayout.Position.MIDDLE);
            mTitleBar.getRightGroup().setVisibility(View.GONE);

            mMessageInfo = (MergeMessageBean) intent.getSerializableExtra(TUIChatConstants.FORWARD_MERGE_MESSAGE_KEY);
            chatInfo = (ChatInfo) intent.getSerializableExtra(TUIChatConstants.CHAT_INFO);
            if (null == mMessageInfo) {
                TUIChatLog.e(TAG, "mMessageInfo is null");
                return;
            }
            presenter.setChatInfo(chatInfo);
            presenter.downloadMergerMessage(mMessageInfo);
        }
    }
}
