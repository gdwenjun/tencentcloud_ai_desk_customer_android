package com.tencent.qcloud.tuikit.deskcontact.classicui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.tuikit.deskcontact.R;
import com.tencent.qcloud.tuikit.deskcontact.TUIContactService;
import com.tencent.qcloud.tuikit.deskcontact.bean.ContactItemBean;
import com.tencent.qcloud.tuikit.deskcontact.classicui.widget.ContactLayout;
import com.tencent.qcloud.tuikit.deskcontact.classicui.widget.ContactListView;
import com.tencent.qcloud.tuikit.deskcontact.presenter.ContactPresenter;
import com.tencent.qcloud.tuikit.deskcontact.util.TUIContactLog;
import java.util.HashMap;
import java.util.Map;

public class DeskTUIContactFragment extends Fragment {
    private static final String TAG = DeskTUIContactFragment.class.getSimpleName();
    private ContactLayout mContactLayout;

    private ContactPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.desk_contact_fragment, container, false);
        initViews(baseView);

        return baseView;
    }

    private void initViews(View view) {
        mContactLayout = view.findViewById(R.id.contact_layout);

        presenter = new ContactPresenter();
        presenter.setFriendListListener();
        mContactLayout.setPresenter(presenter);
        mContactLayout.initDefault();

        mContactLayout.getContactListView().setOnItemClickListener(new ContactListView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ContactItemBean contact) {
                if (position == 0) {
                    Intent intent = new Intent(TUIContactService.getAppContext(), DeskNewFriendActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    TUIContactService.getAppContext().startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(TUIContactService.getAppContext(), DeskGroupListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    TUIContactService.getAppContext().startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(TUIContactService.getAppContext(), DeskBlackListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    TUIContactService.getAppContext().startActivity(intent);
                } else {
                    if (contact.isTop() && contact.getExtensionListener() != null) {
                        Map<String, Object> param = new HashMap<>();
                        param.put(TUIConstants.TUIContact.CONTEXT, getActivity());
                        contact.getExtensionListener().onClicked(param);
                    } else {
                        Intent intent = new Intent(TUIContactService.getAppContext(), DeskFriendProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(TUIConstants.TUIContact.USER_ID, contact.getId());
                        TUIContactService.getAppContext().startActivity(intent);
                    }
                }
            }
        });
    }

    public void reloadData() {
        if (mContactLayout != null) {
            mContactLayout.reloadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        TUIContactLog.i(TAG, "onResume");
    }
}
