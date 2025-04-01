package com.tencent.qcloud.tuikit.deskcontact.interfaces;

import com.tencent.qcloud.tuikit.deskcontact.bean.FriendApplicationBean;

import java.util.List;

public interface INewFriendActivity {
    void onDataSourceChanged(List<FriendApplicationBean> beanList);
}
