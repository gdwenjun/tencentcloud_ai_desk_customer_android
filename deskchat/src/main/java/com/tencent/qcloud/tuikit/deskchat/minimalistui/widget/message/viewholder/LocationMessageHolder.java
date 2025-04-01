package com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.viewholder;

import android.view.View;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.minimalistui.widget.message.MessageContentHolder;

public class LocationMessageHolder extends MessageContentHolder {

    public LocationMessageHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getVariableLayout() {
        return 0;
    }

    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {

    }

}
