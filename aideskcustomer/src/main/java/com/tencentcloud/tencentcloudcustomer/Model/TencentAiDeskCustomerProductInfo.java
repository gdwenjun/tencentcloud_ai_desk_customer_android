package com.tencentcloud.tencentcloudcustomer.Model;

import com.tencentcloud.tencentcloudcustomer.Interface.TencentAiDeskCustomerProductOnItemClick;
import com.tencentcloud.tencentcloudcustomer.Interface.TencentAiDeskCustomerProductOnSendClick;

import java.util.HashMap;

public class TencentAiDeskCustomerProductInfo {
    private String name;
    private String description;
    private String pictureUrl;
    private String jumpUrl;
    private TencentAiDeskCustomerProductOnSendClick OnSendClickListener;
    private TencentAiDeskCustomerProductOnItemClick OnItemClickListener;
    private Boolean useUrlJump;
    private Boolean clickAutoSend;
    private HashMap<String,Object> data;


    public void setData(HashMap<String,Object> _data){
        this.data = _data;
    }
    public HashMap<String,Object> getData(){
        return this.data;
    }
    public Boolean getUseUrlJump(){
        return useUrlJump;
    }
    public void setUseUrlJump(boolean isUse){
        this.useUrlJump = isUse;
    }
    public Boolean getClickAutoSend(){
        return clickAutoSend;
    }
    public void setClickAutoSend(boolean isAuto){
        this.clickAutoSend = isAuto;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public TencentAiDeskCustomerProductOnSendClick getOnSendClickListener() {
        return this.OnSendClickListener;
    }

    public void setOnSendClickListener(TencentAiDeskCustomerProductOnSendClick onSendClickListener) {
        this.OnSendClickListener = onSendClickListener;
    }

    public TencentAiDeskCustomerProductOnItemClick getOnItemClickListener() {
        return this.OnItemClickListener;
    }

    public void setOnSendClickListener(TencentAiDeskCustomerProductOnItemClick onItemClickListener) {
        this.OnItemClickListener = onItemClickListener;
    }
}
