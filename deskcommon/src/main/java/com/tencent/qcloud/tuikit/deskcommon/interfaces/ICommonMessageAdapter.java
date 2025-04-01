package com.tencent.qcloud.tuikit.deskcommon.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;

public interface ICommonMessageAdapter {
    TUIMessageBean getItem(int position);

    TUIMessageBean getFirstMessageBean();

    TUIMessageBean getLastMessageBean();

    void onItemRefresh(TUIMessageBean messageBean);

    UserFaceUrlCache getUserFaceUrlCache();
}
