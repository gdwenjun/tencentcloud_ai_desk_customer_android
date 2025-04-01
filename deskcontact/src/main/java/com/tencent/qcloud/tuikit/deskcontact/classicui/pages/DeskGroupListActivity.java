package com.tencent.qcloud.tuikit.deskcontact.classicui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.Nullable;
import com.tencent.qcloud.tuikit.deskcommon.component.TitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.activities.BaseLightActivity;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.ITitleBarLayout;
import com.tencent.qcloud.tuikit.deskcontact.R;
import com.tencent.qcloud.tuikit.deskcontact.bean.ContactItemBean;
import com.tencent.qcloud.tuikit.deskcontact.classicui.util.ContactStartChatUtils;
import com.tencent.qcloud.tuikit.deskcontact.classicui.widget.ContactListView;
import com.tencent.qcloud.tuikit.deskcontact.presenter.ContactPresenter;

public class DeskGroupListActivity extends BaseLightActivity {
    private static final String TAG = DeskGroupListActivity.class.getSimpleName();

    private TitleBarLayout mTitleBar;
    private ContactListView mListView;

    private ContactPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list_activity);

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataSource();
    }

    private void init() {
        mTitleBar = findViewById(R.id.group_list_titlebar);
        mTitleBar.setTitle(getResources().getString(R.string.group), ITitleBarLayout.Position.LEFT);
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.setTitle(getResources().getString(R.string.add_group), ITitleBarLayout.Position.RIGHT);
        mTitleBar.getRightIcon().setVisibility(View.GONE);
        mTitleBar.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeskGroupListActivity.this, DeskAddMoreActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isGroup", true);
                startActivity(intent);
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
                ContactStartChatUtils.startChatActivity(contact.getId(), ContactItemBean.TYPE_GROUP, chatName, contact.getGroupType());
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
