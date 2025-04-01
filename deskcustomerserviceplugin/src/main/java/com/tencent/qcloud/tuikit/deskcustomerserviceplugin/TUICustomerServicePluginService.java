package com.tencent.qcloud.tuikit.deskcustomerserviceplugin;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.google.auto.service.AutoService;
import com.tencent.qcloud.deskcore.ServiceInitializer;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.deskcore.TUIThemeManager;
import com.tencent.qcloud.deskcore.annotations.TUIInitializerDependency;
import com.tencent.qcloud.deskcore.annotations.TUIInitializerID;
import com.tencent.qcloud.deskcore.interfaces.ITUIExtension;
import com.tencent.qcloud.deskcore.interfaces.ITUINotification;
import com.tencent.qcloud.deskcore.interfaces.ITUIService;
import com.tencent.qcloud.deskcore.interfaces.TUIExtensionEventListener;
import com.tencent.qcloud.deskcore.interfaces.TUIExtensionInfo;
import com.tencent.qcloud.deskcore.interfaces.TUIInitializer;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuikit.deskchat.TUIChatConstants;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BotBranchMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BotBranchMessageReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BranchMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BranchMessageReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.CardMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.CardMessageReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.CollectionMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.CollectionMessageReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.CustomerServiceTypingMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.EvaluationMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.EvaluationMessageReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.InvisibleMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.RichTextMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.RichTextMessageReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.StreamTextMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.StreamTextMessageReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksBranchMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksBranchMessageReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionMessageReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.page.CustomerServiceMemberListActivity;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.BotBranchHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.BotBranchReplyView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.BranchHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.BranchReplyView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.CardHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.CardReplyView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.CollectionHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.CollectionReplyView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.EvaluationHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.EvaluationReplyView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.InputViewFloatLayerProxy;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.InvisibleHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.RichTextHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.RichTextReplyQuoteView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.StreamTextHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.StreamTextReplyQuoteView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.TasksBranchHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.TasksBranchReplyView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.TasksCollectionHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.TasksCollectionReplyView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUICustomerServiceConfig;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceLog;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceUtils;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AutoService(TUIInitializer.class)
@TUIInitializerDependency({"TUIChatClassic", "TUIContact"})
@TUIInitializerID("TUICustomerServicePlugin")
public class TUICustomerServicePluginService implements TUIInitializer, ITUINotification, ITUIService, ITUIExtension {
    public static final String TAG = TUICustomerServicePluginService.class.getSimpleName();
    private static TUICustomerServicePluginService instance;

    public static TUICustomerServicePluginService getInstance() {
        return instance;
    }

    private Context appContext;
    private boolean canTriggerEvaluation = false;

    @Override
    public void init(Context context) {
        appContext = context;
        instance = this;
        initTheme();
        initExtension();
        initMessage();
        initService();
    }

    private void initService(){
        TUICore.registerService(TUIConstants.Service.TUI_CUSTOMER_PLUGIN, this);
    }

    @Override
    public Object onCall(String method, Map<String, Object> param) {
        if(TextUtils.equals(method,TUIConstants.TUICustomerServicePlugin.METHOD_GET_CUSTOMER_SERVICE_ACCOUNTS)){
            return TUICustomerServiceConfig.getInstance().getCustomerServiceAccounts();
        }
        return ITUIService.super.onCall(method, param);
    }

    private void initMessage() {
        Map<String, Object> branchParam = new HashMap<>();
        branchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_BRANCH);
        branchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, BranchMessageBean.class);
        branchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, BranchHolder.class);
        branchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_BEAN_CLASS, BranchMessageReplyQuoteBean.class);
        branchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_VIEW_CLASS, BranchReplyView.class);
        TUICore.callService(
            TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME, TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, branchParam);

        Map<String, Object> tasksBranchParam = new HashMap<>();
        tasksBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
                TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_TASKS_BRANCH);
        tasksBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, TasksBranchMessageBean.class);
        tasksBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, TasksBranchHolder.class);
        tasksBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_BEAN_CLASS, TasksBranchMessageReplyQuoteBean.class);
        tasksBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_VIEW_CLASS, TasksBranchReplyView.class);
        tasksBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.IS_NEED_EMPTY_VIEW_GROUP, true);
        TUICore.callService(
                TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME, TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, tasksBranchParam);


        Map<String, Object> collectionParam = new HashMap<>();
        collectionParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY
                + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_COLLECTION);
        collectionParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, CollectionMessageBean.class);
        collectionParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, CollectionHolder.class);
        collectionParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_BEAN_CLASS, CollectionMessageReplyQuoteBean.class);
        collectionParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_VIEW_CLASS, CollectionReplyView.class);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
            TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, collectionParam);

        Map<String, Object> tasksCollectionParam = new HashMap<>();
        tasksCollectionParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
                TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY
                        + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_TASKS_COLLECTION);
        tasksCollectionParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, TasksCollectionMessageBean.class);
        tasksCollectionParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, TasksCollectionHolder.class);
        tasksCollectionParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_BEAN_CLASS, TasksCollectionMessageReplyQuoteBean.class);
        tasksCollectionParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_VIEW_CLASS, TasksCollectionReplyView.class);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
                TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, tasksCollectionParam);

        Map<String, Object> cardParam = new HashMap<>();
        cardParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_CARD);
        cardParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, CardMessageBean.class);
        cardParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, CardHolder.class);
        cardParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_BEAN_CLASS, CardMessageReplyQuoteBean.class);
        cardParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_VIEW_CLASS, CardReplyView.class);
        TUICore.callService(
            TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME, TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, cardParam);

        Map<String, Object> evaluationParam = new HashMap<>();
        evaluationParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY
                + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_EVALUATION);
        evaluationParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, EvaluationMessageBean.class);
        evaluationParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, EvaluationHolder.class);
        evaluationParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_BEAN_CLASS, EvaluationMessageReplyQuoteBean.class);
        evaluationParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_VIEW_CLASS, EvaluationReplyView.class);
        evaluationParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.IS_NEED_EMPTY_VIEW_GROUP, true);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
            TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, evaluationParam);

        Map<String, Object> botBranchParam = new HashMap<>();
        botBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
                TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_BOT_RESPONSE);
        botBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, BotBranchMessageBean.class);
        botBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, BotBranchHolder.class);
        botBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_BEAN_CLASS, BotBranchMessageReplyQuoteBean.class);
        botBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.IS_NEED_EMPTY_VIEW_GROUP, true);
        botBranchParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_VIEW_CLASS, BotBranchReplyView.class);
        TUICore.callService(
                TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME, TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, botBranchParam);

        Map<String, Object> richTextParam = new HashMap<>();
        richTextParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
                TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_BOT_RICH_TEXT);
        richTextParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, RichTextMessageBean.class);
        richTextParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, RichTextHolder.class);
        richTextParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_BEAN_CLASS, RichTextMessageReplyQuoteBean.class);
        richTextParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_VIEW_CLASS, RichTextReplyQuoteView.class);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
                TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, richTextParam);

        Map<String, Object> streamTextParam = new HashMap<>();
        streamTextParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
                TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_BOT_STREAM_TEXT);
        streamTextParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, StreamTextMessageBean.class);
        streamTextParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, StreamTextHolder.class);
        streamTextParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_BEAN_CLASS, StreamTextMessageReplyQuoteBean.class);
        streamTextParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_REPLY_VIEW_CLASS, StreamTextReplyQuoteView.class);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
                TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, streamTextParam);

        Map<String, Object> invisibleEndParam = new HashMap<>();
        invisibleEndParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_END);
        invisibleEndParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, InvisibleMessageBean.class);
        invisibleEndParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, InvisibleHolder.class);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
            TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, invisibleEndParam);

        Map<String, Object> invisibleTimeoutParam = new HashMap<>();
        invisibleTimeoutParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY
                + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_TIMEOUT);
        invisibleTimeoutParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, InvisibleMessageBean.class);
        invisibleTimeoutParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, InvisibleHolder.class);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
            TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, invisibleTimeoutParam);

        Map<String, Object> invisibleEvaluationSettingParam = new HashMap<>();
        invisibleEvaluationSettingParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY
                + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_EVALUATION_SETTING);
        invisibleEvaluationSettingParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, InvisibleMessageBean.class);
        invisibleEvaluationSettingParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, InvisibleHolder.class);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
            TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, invisibleEvaluationSettingParam);

        Map<String, Object> invisibleEvaluationSelectedParam = new HashMap<>();
        invisibleEvaluationSelectedParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY
                + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_EVALUATION_SELECTED);
        invisibleEvaluationSelectedParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, InvisibleMessageBean.class);
        invisibleEvaluationSelectedParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, InvisibleHolder.class);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
            TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, invisibleEvaluationSelectedParam);

        Map<String, Object> invisibleTriggerEvaluationParam = new HashMap<>();
        invisibleTriggerEvaluationParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY
                + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_TRIGGER_EVALUATION);
        invisibleTriggerEvaluationParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, InvisibleMessageBean.class);
        invisibleTriggerEvaluationParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, InvisibleHolder.class);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
            TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, invisibleTriggerEvaluationParam);

        Map<String, Object> invisibleGetEvaluationSettingParam = new HashMap<>();
        invisibleGetEvaluationSettingParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY
                + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_SAY_HELLO);
        invisibleGetEvaluationSettingParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, InvisibleMessageBean.class);
        invisibleGetEvaluationSettingParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_VIEW_HOLDER_CLASS, InvisibleHolder.class);
        TUICore.callService(TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME,
            TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, invisibleGetEvaluationSettingParam);

        Map<String, Object> typingParam = new HashMap<>();
        typingParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BUSINESS_ID,
            TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY + TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_TYPING);
        typingParam.put(TUIConstants.TUIChat.Method.RegisterCustomMessage.MESSAGE_BEAN_CLASS, CustomerServiceTypingMessageBean.class);
        TUICore.callService(
            TUIConstants.TUIChat.Method.RegisterCustomMessage.CLASSIC_SERVICE_NAME, TUIConstants.TUIChat.Method.RegisterCustomMessage.METHOD_NAME, typingParam);
    }

    private void initExtension() {
        TUICore.registerExtension(TUIConstants.TUIChat.Extension.InputMore.CLASSIC_EXTENSION_ID, this);
        TUICore.registerExtension(TUIConstants.TUIChat.Extension.InputViewFloatLayer.CLASSIC_EXTENSION_ID, this);
        TUICore.registerExtension(TUIConstants.TUIContact.Extension.ContactItem.CLASSIC_EXTENSION_ID, this);
        TUICore.registerExtension(TUIConstants.TUIChat.Extension.ChatNavigationMoreItem.CLASSIC_EXTENSION_ID, this);
        TUICore.registerExtension(TUIConstants.TUIChat.Extension.ChatUserIconClickedProcessor.CLASSIC_EXTENSION_ID, this);
        TUICore.registerExtension(TUIConstants.TUIChat.Extension.ChatView.GET_CONFIG_PARAMS, this);
    }

    private void initTheme() {
        TUIThemeManager.addLightTheme(R.style.TUICustomerServiceLightTheme);
        TUIThemeManager.addLivelyTheme(R.style.TUICustomerServiceLivelyTheme);
        TUIThemeManager.addSeriousTheme(R.style.TUICustomerServiceSeriousTheme);
    }

    @Override
    public List<TUIExtensionInfo> onGetExtension(String extensionID, Map<String, Object> param) {
        if (TextUtils.equals(extensionID, TUIConstants.TUIChat.Extension.InputMore.CLASSIC_EXTENSION_ID)) {
            if (!canTriggerEvaluation) {
                return null;
            }

            if (param != null && !param.isEmpty()) {
                String userID = getOrDefault(param, TUIConstants.TUIChat.Extension.InputMore.USER_ID, null);
                if (!TextUtils.isEmpty(userID)) {
                    TUIExtensionInfo extensionInfo = new TUIExtensionInfo();
                    extensionInfo.setWeight(200);
                    extensionInfo.setText(appContext.getString(R.string.extension_satisfaction_evaluation));
                    extensionInfo.setIcon(R.drawable.tui_evaluation_ic);
                    extensionInfo.setExtensionListener(new TUIExtensionEventListener() {
                        @Override
                        public void onClicked(Map<String, Object> param) {
                            TUICustomerServicePresenter presenter = new TUICustomerServicePresenter();
                            presenter.triggerEvaluation(userID);
                        }
                    });
                    return Collections.singletonList(extensionInfo);
                }
            }
        } else if (TextUtils.equals(extensionID, TUIConstants.TUIContact.Extension.ContactItem.CLASSIC_EXTENSION_ID)) {
            TUIExtensionInfo extensionInfo = new TUIExtensionInfo();
            extensionInfo.setWeight(200);
            extensionInfo.setText(appContext.getString(R.string.customer_service));
            extensionInfo.setIcon(TUIThemeManager.getAttrResId(appContext, R.attr.customer_service_icon));
            extensionInfo.setExtensionListener(new TUIExtensionEventListener() {
                @Override
                public void onClicked(Map<String, Object> param) {
                    TUICustomerServiceUtils.checkCustomerServiceAbility(
                        TUICustomerServiceConstants.CUSTOMER_SERVICE_PLUGIN_ABILITY, new IUIKitCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean isSupportPlugin) {
                                if (isSupportPlugin) {
                                    Intent intent = new Intent(TUICustomerServicePluginService.getAppContext(), CustomerServiceMemberListActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    TUICustomerServicePluginService.getAppContext().startActivity(intent);
                                } else {
                                    Context context = getOrDefault(param, TUIConstants.TUIContact.CONTEXT, null);
                                    if (context != null) {
                                        TUICustomerServiceUtils.showNotSupportDialog(context);
                                    }
                                }
                            }
                        });
                }
            });
            return Collections.singletonList(extensionInfo);
        } else if (TextUtils.equals(extensionID, TUIConstants.TUIChat.Extension.ChatNavigationMoreItem.CLASSIC_EXTENSION_ID)) {
            Object userID = param.get(TUIConstants.TUIChat.Extension.ChatNavigationMoreItem.USER_ID);
            if (userID instanceof String && TUICustomerServiceConfig.getInstance().getCustomerServiceAccounts().contains(userID)) {
                TUIExtensionInfo extensionInfo = new TUIExtensionInfo();
                extensionInfo.setIcon(
                    TUIThemeManager.getAttrResId(getAppContext(), com.tencent.qcloud.tuikit.deskcontact.R.attr.contact_chat_extension_title_bar_more_menu));
                extensionInfo.setWeight(200);
                extensionInfo.setExtensionListener(new TUIExtensionEventListener() {
                    @Override
                    public void onClicked(Map<String, Object> param) {
                            return;
//                        Intent intent = new Intent(getAppContext(), CustomerServiceProfileActivity.class);
//                        intent.putExtra(TUIConstants.TUIChat.CHAT_ID, (String) userID);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getAppContext().startActivity(intent);
                    }
                });
                return Collections.singletonList(extensionInfo);
            }
        } else if (TextUtils.equals(extensionID, TUIConstants.TUIChat.Extension.ChatUserIconClickedProcessor.CLASSIC_EXTENSION_ID)) {
            Object userIDObj = param.get(TUIConstants.TUIChat.Extension.ChatUserIconClickedProcessor.USER_ID);
            if (userIDObj instanceof String) {
                String userID = (String) userIDObj;
                if (TUICustomerServiceConfig.getInstance().getCustomerServiceAccounts().contains(userID)) {
                    TUIExtensionInfo extensionInfo = new TUIExtensionInfo();
                    extensionInfo.setIcon(
                        TUIThemeManager.getAttrResId(getAppContext(), com.tencent.qcloud.tuikit.deskcontact.R.attr.contact_chat_extension_title_bar_more_menu));
                    extensionInfo.setWeight(100);
                    extensionInfo.setExtensionListener(new TUIExtensionEventListener() {
                        @Override
                        public void onClicked(Map<String, Object> param) {
//                            Intent intent = new Intent(getAppContext(), CustomerServiceProfileActivity.class);
//                            intent.putExtra(TUIConstants.TUIChat.CHAT_ID, userID);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            getAppContext().startActivity(intent);
                            return;
                        }
                    });
                    return Collections.singletonList(extensionInfo);
                }
            }
        } else if (TextUtils.equals(extensionID, TUIConstants.TUIChat.Extension.ChatView.GET_CONFIG_PARAMS)) {
            Object userIDObj = param.get(TUIConstants.TUIChat.CHAT_ID);
            if (userIDObj instanceof String) {
                String userID = (String) userIDObj;
                if (TUICustomerServiceConfig.getInstance().getCustomerServiceAccounts().contains(userID)) {
                    TUIExtensionInfo extensionInfo = new TUIExtensionInfo();
                    Map<String, Object> extensionMap = new HashMap<>();
                    extensionMap.put(TUIConstants.TUIChat.Extension.ChatView.MESSAGE_NEED_READ_RECEIPT, true);
                    extensionMap.put(TUIConstants.TUIChat.Extension.ChatView.ENABLE_VIDEO_CALL, false);
                    extensionMap.put(TUIConstants.TUIChat.Extension.ChatView.ENABLE_AUDIO_CALL, false);
                    extensionMap.put(TUIConstants.TUIChat.Extension.ChatView.ENABLE_CUSTOM_HELLO_MESSAGE, false);
                    extensionInfo.setData(extensionMap);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TUICustomerServicePresenter presenter = new TUICustomerServicePresenter();
                            presenter.sendHelloMessage(userID);
                            TUICustomerServiceLog.i("onGetExtension","Chat view get param. send hello message end. userID:" + userID);
                        }
                    }, 200);

                    return Collections.singletonList(extensionInfo);
                }
            }
        }

        return null;
    }

    @Override
    public boolean onRaiseExtension(String extensionID, View parentView, Map<String, Object> param) {
        if (TextUtils.equals(extensionID, TUIConstants.TUIChat.Extension.InputViewFloatLayer.CLASSIC_EXTENSION_ID)) {
            if (parentView == null || param == null) {
                return false;
            }

            ViewGroup viewGroup = null;
            if (parentView instanceof ViewGroup) {
                viewGroup = (ViewGroup) parentView;
            }
            if (viewGroup == null) {
                return false;
            }

            Object objChatInfo = param.get(TUIChatConstants.CHAT_INFO);
            if (!(objChatInfo instanceof ChatInfo)) {
                return false;
            }

            ChatInfo chatInfo = (ChatInfo) objChatInfo;
            if (chatInfo.getType() != ChatInfo.TYPE_C2C) {
                return false;
            }

            if (!TUICustomerServiceConfig.getInstance().getCustomerServiceAccounts().contains(chatInfo.getId())) {
                return false;
            }
            if(TUICustomerServiceConfig.getInstance().getProductInfo()!=null){
                TUICustomerServiceLog.i("onRaiseExtension","has product info. show product first");
                InputViewFloatLayerProxy inputViewFloatLayerProxy = new InputViewFloatLayerProxy(chatInfo);
                inputViewFloatLayerProxy.showFloatLayerContentForProduct(viewGroup);
            }else if (!TUICustomerServiceConfig.getInstance().getInputViewFloatLayerDataList().isEmpty()) {
                InputViewFloatLayerProxy inputViewFloatLayerProxy = new InputViewFloatLayerProxy(chatInfo);
                inputViewFloatLayerProxy.showFloatLayerContent(viewGroup);
                return true;
            }
        }

        return false;
    }

    @Override
    public void onNotifyEvent(String key, String subKey, Map<String, Object> param) {}

    private <T> T getOrDefault(Map map, Object key, T defaultValue) {
        if (map == null || map.isEmpty()) {
            return defaultValue;
        }
        Object object = map.get(key);
        try {
            if (object != null) {
                return (T) object;
            }
        } catch (ClassCastException e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public void setCanTriggerEvaluation(boolean canTriggerEvaluation) {
        this.canTriggerEvaluation = canTriggerEvaluation;
    }

    public static Context getAppContext() {
        return ServiceInitializer.getAppContext();
    }
}
