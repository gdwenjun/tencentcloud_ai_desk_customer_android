package com.tencent.qcloud.tuikit.deskcontact.classicui.pages;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.Nullable;
import com.tencent.qcloud.deskcore.util.ToastUtil;
import com.tencent.qcloud.tuikit.deskcommon.component.TitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.activities.BaseLightActivity;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.ITitleBarLayout;
import com.tencent.qcloud.tuikit.deskcontact.R;
import com.tencent.qcloud.tuikit.deskcontact.bean.ContactItemBean;
import com.tencent.qcloud.tuikit.deskcontact.classicui.util.ContactStartChatUtils;
import com.tencent.qcloud.tuikit.deskcontact.classicui.widget.ContactListView;
import com.tencent.qcloud.tuikit.deskcontact.presenter.ContactPresenter;
import java.util.ArrayList;
import java.util.List;

public class DeskStartC2CChatActivity extends BaseLightActivity {
    private static final String TAG = DeskStartC2CChatActivity.class.getSimpleName();

    private TitleBarLayout mTitleBar;
    private ContactListView mContactListView;
    private ContactItemBean mSelectedItem;
    private List<ContactItemBean> mContacts = new ArrayList<>();

    private ContactPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_start_c2c_chat_activity);

        mTitleBar = findViewById(R.id.start_c2c_chat_title);
        mTitleBar.setTitle(getResources().getString(com.tencent.qcloud.deskcore.R.string.sure), ITitleBarLayout.Position.RIGHT);
        mTitleBar.getRightIcon().setVisibility(View.GONE);
        mTitleBar.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConversation();
            }
        });
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mContactListView = findViewById(R.id.contact_list_view);
        mContactListView.setSingleSelectMode(true);

        presenter = new ContactPresenter();
        presenter.setFriendListListener();
        mContactListView.setPresenter(presenter);
        presenter.setContactListView(mContactListView);

        mContactListView.loadDataSource(ContactListView.DataSource.FRIEND_LIST);
        mContactListView.setOnSelectChangeListener(new ContactListView.OnSelectChangedListener() {
            @Override
            public void onSelectChanged(ContactItemBean contact, boolean selected) {
                if (selected) {
                    if (mSelectedItem != contact) {
                        if (mSelectedItem != null) {
                            mSelectedItem.setSelected(false);
                        }
                        mSelectedItem = contact;
                    }
                } else {
                    if (mSelectedItem == contact) {
                        mSelectedItem.setSelected(false);
                    }
                }
            }
        });
    }

    public void startConversation() {
        if (mSelectedItem == null || !mSelectedItem.isSelected()) {
            ToastUtil.toastLongMessage(getString(R.string.select_chat));
            return;
        }
        String chatName = mSelectedItem.getId();
        if (!TextUtils.isEmpty(mSelectedItem.getRemark())) {
            chatName = mSelectedItem.getRemark();
        } else if (!TextUtils.isEmpty(mSelectedItem.getNickName())) {
            chatName = mSelectedItem.getNickName();
        }

        ContactStartChatUtils.startChatActivity(mSelectedItem.getId(), ContactItemBean.TYPE_C2C, chatName, "");
        finish();
    }
}
