package com.tencent.qcloud.tuikit.deskchat.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;

import java.util.List;

public interface IMessageAdapter {
    void onDataSourceChanged(List<TUIMessageBean> dataSource);

    void onViewNeedRefresh(int type, int value);

    void onViewNeedRefresh(int type, TUIMessageBean locateMessage);

}
