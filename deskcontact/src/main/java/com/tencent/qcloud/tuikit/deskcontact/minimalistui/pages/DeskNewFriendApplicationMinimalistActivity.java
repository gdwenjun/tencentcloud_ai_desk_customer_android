package com.tencent.qcloud.tuikit.deskcontact.minimalistui.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.tencent.qcloud.tuikit.deskcommon.component.TitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.activities.BaseMinimalistLightActivity;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.ITitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuikit.deskcontact.R;
import com.tencent.qcloud.tuikit.deskcontact.bean.FriendApplicationBean;
import com.tencent.qcloud.tuikit.deskcontact.interfaces.INewFriendActivity;
import com.tencent.qcloud.tuikit.deskcontact.minimalistui.widget.NewFriendListAdapter;
import com.tencent.qcloud.tuikit.deskcontact.presenter.NewFriendPresenter;
import com.tencent.qcloud.tuikit.deskcontact.util.TUIContactLog;
import java.util.List;

public class DeskNewFriendApplicationMinimalistActivity extends BaseMinimalistLightActivity implements INewFriendActivity {
    private static final String TAG = DeskNewFriendApplicationMinimalistActivity.class.getSimpleName();

    private TitleBarLayout mTitleBar;
    private ListView mNewFriendLv;
    private NewFriendListAdapter mAdapter;
    private TextView notFoundTip;

    private NewFriendPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desk_contact_minimalist_new_friend_activity);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPendency();
    }

    private void init() {
        mTitleBar = findViewById(R.id.new_friend_titlebar);
        mTitleBar.setTitle(getResources().getString(R.string.contact_new_application_title, 0), ITitleBarLayout.Position.MIDDLE);
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.getRightIcon().setVisibility(View.GONE);
        presenter = new NewFriendPresenter();
        presenter.setFriendActivity(this);
        presenter.setFriendApplicationRead(new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {}

            @Override
            public void onError(String module, int errCode, String errMsg) {}
        });
        mNewFriendLv = findViewById(R.id.new_friend_list);
        notFoundTip = findViewById(R.id.not_found_tip);
    }

    private void initPendency() {
        presenter.loadFriendApplicationList();
    }

    @Override
    public void onDataSourceChanged(List<FriendApplicationBean> dataSource) {
        TUIContactLog.i(TAG, "getFriendApplicationList success");
        if (dataSource == null || dataSource.isEmpty()) {
            notFoundTip.setVisibility(View.VISIBLE);
            mTitleBar.setTitle(getResources().getString(R.string.contact_no_new_friend_application), ITitleBarLayout.Position.MIDDLE);
        } else {
            mTitleBar.setTitle(getResources().getString(R.string.contact_new_application_title, dataSource.size()), ITitleBarLayout.Position.MIDDLE);
            notFoundTip.setVisibility(View.GONE);
        }
        mNewFriendLv.setVisibility(View.VISIBLE);
        mAdapter = new NewFriendListAdapter(DeskNewFriendApplicationMinimalistActivity.this, R.layout.desk_contact_minimalist_new_friend_application_item, dataSource);
        mAdapter.setPresenter(presenter);
        mNewFriendLv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
