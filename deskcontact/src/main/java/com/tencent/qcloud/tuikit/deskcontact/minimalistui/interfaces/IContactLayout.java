package com.tencent.qcloud.tuikit.deskcontact.minimalistui.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.ILayout;
import com.tencent.qcloud.tuikit.deskcontact.minimalistui.widget.ContactListView;

public interface IContactLayout extends ILayout {
    ContactListView getContactListView();
}
