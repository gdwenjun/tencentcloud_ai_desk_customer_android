package com.tencent.qcloud.tuikit.deskcontact.classicui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.tencent.qcloud.tuikit.deskcommon.component.TitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.ILayout;
import com.tencent.qcloud.tuikit.deskcontact.R;
import com.tencent.qcloud.tuikit.deskcontact.presenter.ContactPresenter;

public class ContactLayout extends LinearLayout implements ILayout {
    private static final String TAG = ContactLayout.class.getSimpleName();

    private ContactListView mContactListView;

    private ContactPresenter presenter;

    public ContactLayout(Context context) {
        super(context);
        init();
    }

    public ContactLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContactLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPresenter(ContactPresenter presenter) {
        this.presenter = presenter;
    }

    private void init() {
        inflate(getContext(), R.layout.desk_contact_layout, this);
        mContactListView = findViewById(R.id.contact_listview);
    }

    public void initDefault() {
        mContactListView.setPresenter(presenter);
        presenter.setContactListView(mContactListView);
        presenter.setIsClassicStyle(true);
        mContactListView.loadDataSource(ContactListView.DataSource.CONTACT_LIST);
    }

    public void reloadData() {
        mContactListView.reloadContactList();
    }

    public ContactListView getContactListView() {
        return mContactListView;
    }

    @Override
    public TitleBarLayout getTitleBar() {
        return null;
    }

    @Override
    public void setParentLayout(Object parent) {}
}
