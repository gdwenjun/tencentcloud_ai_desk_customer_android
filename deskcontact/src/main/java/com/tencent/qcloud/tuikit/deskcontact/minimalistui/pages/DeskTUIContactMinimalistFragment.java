package com.tencent.qcloud.tuikit.deskcontact.minimalistui.pages;

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
import com.tencent.qcloud.tuikit.deskcontact.minimalistui.widget.ContactLayout;
import com.tencent.qcloud.tuikit.deskcontact.minimalistui.widget.ContactListView;
import com.tencent.qcloud.tuikit.deskcontact.presenter.ContactPresenter;
import com.tencent.qcloud.tuikit.deskcontact.util.TUIContactLog;

public class DeskTUIContactMinimalistFragment extends Fragment {
    private static final String TAG = DeskTUIContactMinimalistFragment.class.getSimpleName();
    private ContactLayout mContactLayout;

    private ContactPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.minimalist_contact_fragment, container, false);
        initViews(baseView);

        return baseView;
    }

    private void initViews(View view) {
        mContactLayout = view.findViewById(R.id.contact_layout);

        presenter = new ContactPresenter();
        presenter.setFriendListListener();
        mContactLayout.setPresenter(presenter);
        mContactLayout.initDefault();

        mContactLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void finishActivity() {
                getActivity().finish();
            }
        });

        mContactLayout.getContactListView().setOnItemClickListener(new ContactListView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ContactItemBean contact) {
                if (position == 0) {
                    Intent intent = new Intent(TUIContactService.getAppContext(), DeskNewFriendApplicationMinimalistActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    TUIContactService.getAppContext().startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(TUIContactService.getAppContext(), DeskGroupListMinimalistActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    TUIContactService.getAppContext().startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(TUIContactService.getAppContext(), DeskBlackListMinimalistActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    TUIContactService.getAppContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(TUIContactService.getAppContext(), DeskFriendProfileMinimalistActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(TUIConstants.TUIContact.USER_ID, contact.getId());
                    TUIContactService.getAppContext().startActivity(intent);
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
        mContactLayout.initUI();
    }

    public interface OnClickListener {
        void finishActivity();
    }
}
