package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;
import android.view.View;
import android.widget.TextView;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.MessageBaseHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;

public class ClientTipsHolder extends MessageBaseHolder {
    private TextView tvTitle;
    public ClientTipsHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_client_tips);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.message_client_tips;
    }

    @Override
    public void layoutViews(TUIMessageBean msg, int position)  {
        tvTitle.setText(msg.getExtra());
    }

}
