package com.tencentcloud.tencentcloudcustomer;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.qcloud.deskcore.TUIConfig;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUILogin;
import com.tencent.qcloud.deskcore.TUIThemeManager;
import com.tencent.qcloud.deskcore.interfaces.TUICallback;
import com.tencent.qcloud.deskcore.interfaces.TUILogListener;
import com.tencent.qcloud.deskcore.interfaces.TUILoginConfig;
import com.tencent.qcloud.tuikit.deskchat.classicui.page.DeskTUIC2CChatActivityDesk;
import com.tencent.qcloud.tuikit.deskchat.config.TUIChatConfigs;
import com.tencent.qcloud.tuikit.deskchat.config.classicui.TUIChatConfigClassic;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.TUICustomerServiceConstants;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.InputViewFloatLayerProxy;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUICustomerServiceConfig;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUIInputViewFloatLayerData;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;
import com.tencentcloud.tencentcloudcustomer.Callbacks.TencentAiDeskCustomerLoginCallback;
import com.tencentcloud.tencentcloudcustomer.Config.TencentAiDeskCustomerLanguageConfig;
import com.tencentcloud.tencentcloudcustomer.Config.TencentAiDeskCustomerLoginConfig;
import com.tencentcloud.tencentcloudcustomer.Config.TencentAiDeskCustomerThemeConfig;
import com.tencentcloud.tencentcloudcustomer.Listener.TencentAiDeskCustomerLogListener;
import com.tencentcloud.tencentcloudcustomer.Model.TencentAiDeskCustomerProductInfo;
import com.tencentcloud.tencentcloudcustomer.Model.TencentAiDeskCustomerQuickMessageInfo;
import com.tencentcloud.tencentcloudcustomer.Utils.TencentAiDeskCustomerCommonUtils;
import com.tencentcloud.tencentcloudcustomer.Utils.TencentAiDeskCustomerReport;

import java.util.LinkedList;
import java.util.List;


public class TencentAiDeskCustomer {

    public static final String _version = "2.2.7";


    public static final String TAG = TencentAiDeskCustomer.class.getSimpleName();

    private static class TencentAiDeskCustomerHolder {
        private static final TencentAiDeskCustomer instance = new TencentAiDeskCustomer();
    }
    private static TencentAiDeskCustomer instance;
    private String currentServiceUserId = TUICustomerServiceConstants.DEFAULT_CUSTOMER_SERVICE_ACCOUNT;
    public static TencentAiDeskCustomer getInstance() {

        return TencentAiDeskCustomerHolder.instance;
    }

    private static TencentAiDeskCustomerLogListener loglsr;

    private static final TUILogListener logListener = new TUILogListener(){
        @Override
        public void onLog(int logLevel, String logContent) {
            super.onLog(logLevel, logContent);
            loglsr.onLog(logLevel,logContent);
        }
    };

    public void login( Context context, int sdkAppID, String userID, String userSig, TencentAiDeskCustomerLoginCallback callback){
        getInstance().internalLogin(context, sdkAppID, userID, userSig, null, callback);
    }
    public void login(Context context, int sdkAppID, String userID, String userSig, TencentAiDeskCustomerLoginConfig config, TencentAiDeskCustomerLoginCallback callback){
        getInstance().internalLogin(context, sdkAppID, userID, userSig, config, callback);
    }

    private void internalLogin(Context context, final int sdkAppId, final String userId, final String userSig, TencentAiDeskCustomerLoginConfig config, TencentAiDeskCustomerLoginCallback callback) {
        TencentAiDeskCustomerReport.init(context,sdkAppId,userId);
        TencentAiDeskCustomerReport.reportInfo("internalLogin");
        TUIChatConfigs.getGeneralConfig().setIsShowAvatar(false);
        TUIChatConfigClassic.hideItemsWhenLongPressMessage(new int[]{
                TUIChatConfigClassic.SELECT,
                TUIChatConfigClassic.FORWARD,

                TUIChatConfigClassic.EMOJI_REACTION,
                TUIChatConfigClassic.REPLY,
        });
        TUIChatConfigs.getGeneralConfig().setChatBgSourceID(TencentAiDeskCustomerThemeConfig.business.ordinal());

        TUILoginConfig loginConfig = null;
        if(config!=null){
            loginConfig = new TUILoginConfig();
            loglsr = config.getLogListener();
            TencentAiDeskCustomerReport.reportInfo("CheckLoginConfig","have login config" + loglsr);
            if(config.getLogListener() != null){
                loginConfig.setLogListener(logListener);
            }

            loginConfig.setLogLevel(config.getLogLevel());
            loginConfig.setInitLocalStorageOnly(config.isInitLocalStorageOnly());
        }
        TUILogin.login(context,sdkAppId,userId,userSig, loginConfig, new TUICallback() {
            @Override
            public void onSuccess() {
                TencentAiDeskCustomerReport.reportInfo("login",userId+" login success. current instance version is " + _version);
                callback.onSuccess();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                TencentAiDeskCustomerReport.reportError("login",userId+" login failed." + errorCode + "," + errorMessage);
                callback.onError(errorCode,errorMessage);
            }
        });
    }
    public void sendTextMessage(String text){
        TUICustomerServicePresenter persenter = new TUICustomerServicePresenter();
        persenter.sendTextMessage(TUICustomerServiceConstants.DEFAULT_CUSTOMER_SERVICE_ACCOUNT,text);
        TencentAiDeskCustomerReport.reportInfo("send text message end");
    }

    public void sendTextMessage(String userId, String text){
        TUICustomerServicePresenter persenter = new TUICustomerServicePresenter();
        persenter.sendTextMessage(userId,text);
        TencentAiDeskCustomerReport.reportInfo("send text message end");
    }
    
    public Intent getTencentCloudCustomerChatIntent(Context context){
        return  getInstance().getIntentInner(context,null);
    }

    public Intent getTencentCloudCustomerChatIntent(Context context,String currentServiceUserId){
        return getInstance().getIntentInner(context,currentServiceUserId);
    }

    private Intent getIntentInner(Context context,String currentServiceUserId){
        Intent intent = null;
        if(TextUtils.isEmpty(TUILogin.getLoginUser())){
            TencentAiDeskCustomerReport.reportError("getTencentCloudCustomerChatIntent","get intent failed. You are not logged in yet");
            return intent;
        }
        TencentAiDeskCustomerReport.reportInfo("getIntentInner to TUIC2CChatActivity");
        intent = new Intent(context, DeskTUIC2CChatActivityDesk.class);


        String userId = currentServiceUserId == null ? TUICustomerServiceConstants.DEFAULT_CUSTOMER_SERVICE_ACCOUNT : currentServiceUserId;

        List<String> currentValidateUserId =  TUICustomerServiceConfig.getInstance().getCustomerServiceAccounts();
        if(!currentValidateUserId.contains(userId)){
            currentValidateUserId.add(userId);
            TencentAiDeskCustomerReport.reportInfo("getTencentCloudCustomerChatIntent", "set customer service account"+currentValidateUserId);
            TUICustomerServiceConfig.getInstance().setCustomerServiceAccounts(currentValidateUserId);
        }
        this.currentServiceUserId = userId;
        intent.putExtra(TUIConstants.TUIChat.CHAT_ID, userId);
        intent.putExtra(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C);
        TencentAiDeskCustomerReport.reportInfo("get success. userid: "+ userId + " all userIDs is "+currentValidateUserId);
        return intent;
    }

    public  String getVersion(){
        TencentAiDeskCustomerReport.reportInfo("getVersion",_version);
        return _version;
    }

    public  void setConfig(){
        TencentAiDeskCustomerReport.reportInfo("setConfig","");
    }

    public  void setTheme(TencentAiDeskCustomerThemeConfig theme){
        TUIChatConfigs.getGeneralConfig().setChatBgSourceID(theme.ordinal());
        TencentAiDeskCustomerReport.reportInfo("setTheme","");
    }

    public  void setLanguage(Context context, TencentAiDeskCustomerLanguageConfig language){
        TUIThemeManager.getInstance().changeLanguage(context, language.toString());
        TencentAiDeskCustomerReport.reportInfo("setLanguage","");
    }

    public void addListener(){
        TencentAiDeskCustomerReport.reportInfo("addListener","");
    }

    public void setQuickMessages(LinkedList<TencentAiDeskCustomerQuickMessageInfo> messageList){
        TencentAiDeskCustomerReport.reportInfo("setQuickMessages","messageList: " + messageList.size());
        List<TUIInputViewFloatLayerData> list = new LinkedList<TUIInputViewFloatLayerData>();
        for (TencentAiDeskCustomerQuickMessageInfo s : messageList) {
            TUIInputViewFloatLayerData data = new TUIInputViewFloatLayerData();
            data.setContent(s.getContent());
            data.setIconResourceId(s.getIconResourceId());
            data.setOnItemClickListener(new InputViewFloatLayerProxy.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if(s.getAutoSendMessageUseContent()){
                        if(s.getContent()!=null){
                            getInstance().sendTextMessage(currentServiceUserId, s.getContent());
                        }else{
                            TencentAiDeskCustomerReport.reportInfo("setQuickMessages","auto send message failed. content is null");
                        }
                    }
                    if(s.getOnItemClickListener()!=null){
                        s.getOnItemClickListener().onItemClick(view,position,s);
                    }
                }
            });
            list.add(data);
        }
        TencentAiDeskCustomerReport.reportInfo("set quick message " + list.toString());
        TUICustomerServiceConfig.getInstance().setInputViewFloatLayerDataList(list);
    }

    public  void setProductInfo(TencentAiDeskCustomerProductInfo info){
        TUICustomerServiceConfig.getInstance().setProductInfo(TencentAiDeskCustomerCommonUtils.TencentCustomerInfoToTUICustomerProductInto(info));
        if (info == null) {
            TencentAiDeskCustomerReport.reportInfo("set product info is null  " );
            return;
        }
        TencentAiDeskCustomerReport.reportInfo("set product info " + info.toString());
    }

    public void setShowHumanService(boolean showHumanService){
        TUICustomerServiceConfig.getInstance().setShowHumanService(showHumanService);
        TencentAiDeskCustomerReport.reportInfo("set setShowHumanService " + showHumanService);
    }

    public String getCrashLogDir(){
        TencentAiDeskCustomerReport.reportInfo("getCrashLogDir","");
        return TUIConfig.getCrashLogDir();
    }

    public void callExperimentalAPI(Context context, String api, Object param) {
        TencentAiDeskCustomerReport.reportInfo("callExperimentalAPI",api);
        if (api.equals("setTestEnvironment")) {
            TUILogin.logout(new TUICallback() {
                @Override
                public void onSuccess() {
                    TUILogin.unInit();
                    V2TIMManager.getInstance().callExperimentalAPI(api, param, null);
                }

                @Override
                public void onError(int errorCode, String errorMessage) {
                    TUILogin.unInit();
                    V2TIMManager.getInstance().callExperimentalAPI(api, param, null);
                }
            });
        }
    }
    

}