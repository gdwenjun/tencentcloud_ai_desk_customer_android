package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.TUICustomerServicePluginService;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceMessageParser;

public class SeatStatusMessageBean extends TUIMessageBean {
    private SeatStatusBean seatStatusBean;
    @Override
    public String onGetDisplayString() {
        return "";
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        seatStatusBean = TUICustomerServiceMessageParser.getSeatStatusBeannInfo(v2TIMMessage);
        if (seatStatusBean == null) {
            return;
        }
        if (seatStatusBean.getCommand().equals("updateSeatStatus")) {
            String content = seatStatusBean.getContent();
            if (content.equals("inSeat")) {
               // 隐藏人工服务
                TUICustomerServicePluginService.getInstance().updateHumanServiceVis(false);
            }
            if (content.equals("outSeat")){
                // 显示人工服务
                TUICustomerServicePluginService.getInstance().updateHumanServiceVis(true);
            }
        }
    }
}
