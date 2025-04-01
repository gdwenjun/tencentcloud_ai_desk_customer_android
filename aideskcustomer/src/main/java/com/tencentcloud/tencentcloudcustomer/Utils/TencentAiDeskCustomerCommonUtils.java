package com.tencentcloud.tencentcloudcustomer.Utils;

import android.view.View;

import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUICustomerServiceProductInfo;
import com.tencentcloud.tencentcloudcustomer.Model.TencentAiDeskCustomerProductInfo;

import java.util.HashMap;

public class TencentAiDeskCustomerCommonUtils {
    public static TencentAiDeskCustomerProductInfo TUICustomerProductIntoToTencentCustomerInfo(TUICustomerServiceProductInfo TUIInfo){
        TencentAiDeskCustomerProductInfo info = new TencentAiDeskCustomerProductInfo();
        if(info.getClickAutoSend()!=null){
            TUIInfo.setClickAutoSend(info.getClickAutoSend());
        }
        if(info.getDescription()!=null){
            TUIInfo.setDescription(info.getDescription());
        }
        if(info.getOnItemClickListener()!=null){
            TUIInfo.setOnItemClickListener(new TUICustomerServiceProductInfo.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, HashMap<String, Object> data) {

                    info.getOnItemClickListener().onItemClick(view,position,data);
                }
            });
        }
        if(info.getOnSendClickListener()!=null){
            TUIInfo.setOnSendClickListener(new TUICustomerServiceProductInfo.OnSendClickListener() {
                @Override
                public void onItemClick(View view, int position, HashMap<String, Object> data) {
                    info.getOnSendClickListener().onItemClick(view,position,data);
                }
            });
        }
        if(info.getData()!=null){
            TUIInfo.setData(info.getData());
        }
        if(info.getUseUrlJump()!=null){
            TUIInfo.setJumpUrl(info.getJumpUrl());
        }
        if(info.getJumpUrl()!=null){
            TUIInfo.setJumpUrl(info.getJumpUrl());
        }
        if(info.getName()!=null){
            TUIInfo.setName(info.getName());
        }
        if(info.getPictureUrl()!=null){
            TUIInfo.setPictureUrl(info.getPictureUrl());
        }
        return info;
    }
    public static TUICustomerServiceProductInfo TencentCustomerInfoToTUICustomerProductInto(TencentAiDeskCustomerProductInfo info){
        TUICustomerServiceProductInfo TUIInfo = new TUICustomerServiceProductInfo();
        if(info.getClickAutoSend()!=null){
            TUIInfo.setClickAutoSend(info.getClickAutoSend());
        }
        if(info.getDescription()!=null){
            TUIInfo.setDescription(info.getDescription());
        }
        if(info.getOnItemClickListener()!=null){
            TUIInfo.setOnItemClickListener(new TUICustomerServiceProductInfo.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, HashMap<String, Object> data) {

                    info.getOnItemClickListener().onItemClick(view,position,data);
                }
            });
        }
        if(info.getOnSendClickListener()!=null){
            TUIInfo.setOnSendClickListener(new TUICustomerServiceProductInfo.OnSendClickListener() {
                @Override
                public void onItemClick(View view, int position, HashMap<String, Object> data) {
                    info.getOnSendClickListener().onItemClick(view,position,data);
                }
            });
        }
        if(info.getData()!=null){
            TUIInfo.setData(info.getData());
        }
        if(info.getUseUrlJump()!=null){
            TUIInfo.setJumpUrl(info.getJumpUrl());
        }
        if(info.getJumpUrl()!=null){
            TUIInfo.setJumpUrl(TUIInfo.getJumpUrl());
        }
        if(info.getName()!=null){
            TUIInfo.setName(info.getName());
        }
        if(info.getPictureUrl()!=null){
            TUIInfo.setPictureUrl(info.getPictureUrl());
        }

        return TUIInfo;
    }
}
