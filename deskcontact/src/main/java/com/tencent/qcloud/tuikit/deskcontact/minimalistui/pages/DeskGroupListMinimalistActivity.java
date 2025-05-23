package com.tencent.qcloud.tuikit.deskcontact.minimalistui.pages;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.tencent.qcloud.tuikit.deskcommon.component.TitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.activities.BaseMinimalistLightActivity;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.ITitleBarLayout;
import com.tencent.qcloud.tuikit.deskcontact.R;
import com.tencent.qcloud.tuikit.deskcontact.bean.ContactItemBean;
import com.tencent.qcloud.tuikit.deskcontact.minimalistui.util.ContactStartChatUtils;
import com.tencent.qcloud.tuikit.deskcontact.minimalistui.widget.ContactListView;
import com.tencent.qcloud.tuikit.deskcontact.presenter.ContactPresenter;

public class DeskGroupListMinimalistActivity extends BaseMinimalistLightActivity {
    private static final String TAG = DeskGroupListMinimalistActivity.class.getSimpleName();

    private TitleBarLayout mTitleBar;
    private ContactListView mListView;

    private ContactPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minimalist_group_list_activity);

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataSource();
    }

    private void init() {
        mTitleBar = findViewById(R.id.group_list_titlebar);
        mTitleBar.setTitle(getResources().getString(R.string.group), ITitleBarLayout.Position.MIDDLE);
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mListView = findViewById(R.id.group_list);
        mListView.setOnItemClickListener(new ContactListView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ContactItemBean contact) {
                String chatName = contact.getId();
                if (!TextUtils.isEmpty(contact.getRemark())) {
                    chatName = contact.getRemark();
                } else if (!TextUtils.isEmpty(contact.getNickName())) {
                    chatName = contact.getNickName();
                }
                ContactStartChatUtils.startChatActivity(contact.getId(), ContactItemBean.TYPE_GROUP, chatName, contact.getAvatarUrl(), null);
            }
        });
    }

    public void loadDataSource() {
        presenter = new ContactPresenter();
        presenter.setFriendListListener();
        mListView.setIsGroupList(true);
        mListView.setPresenter(presenter);
        mListView.setNotFoundTip(getString(R.string.contact_no_group));
        presenter.setContactListView(mListView);
        mListView.loadDataSource(ContactListView.DataSource.GROUP_LIST);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
