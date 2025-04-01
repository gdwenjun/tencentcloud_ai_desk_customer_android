package com.tencentcloud.tencentcloudcustomer.Model;

import com.tencentcloud.tencentcloudcustomer.Interface.TencentAiDeskCustomerQuickMessageOnClick;


public class TencentAiDeskCustomerQuickMessageInfo {
    private String content;
    private int iconResourceId = -1;
    private TencentAiDeskCustomerQuickMessageOnClick OnItemClickListener;
    private Boolean autoSendMessageUseContent = true;

    public boolean getAutoSendMessageUseContent(){
        return this.autoSendMessageUseContent;
    }

    public void setAutoSendMessageUseContent(boolean isAutoSend){
        this.autoSendMessageUseContent = isAutoSend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int value) {
        this.iconResourceId =value;
    }

    public TencentAiDeskCustomerQuickMessageOnClick getOnItemClickListener() {
        return OnItemClickListener;
    }

    public void setOnItemClickListener(TencentAiDeskCustomerQuickMessageOnClick onItemClickListener) {
        this.OnItemClickListener = onItemClickListener;
    }
}
