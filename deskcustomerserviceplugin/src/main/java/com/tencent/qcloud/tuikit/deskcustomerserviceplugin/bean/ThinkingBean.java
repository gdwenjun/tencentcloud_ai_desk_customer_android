package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;

import java.io.Serializable;

public class ThinkingBean implements Serializable {
    // 0：思考中 1：思考结束
    private int thinkingStatus;

    public int getThinkingStatus() {
        return thinkingStatus;
    }

    public void setThinkingStatus(int thinkingStatus) {
        this.thinkingStatus = thinkingStatus;
    }
}
