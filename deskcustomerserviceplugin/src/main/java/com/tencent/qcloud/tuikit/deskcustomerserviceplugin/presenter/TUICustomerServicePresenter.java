package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter;

import android.text.TextUtils;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskchat.TUIChatService;
import com.tencent.qcloud.tuikit.deskchat.util.ChatMessageBuilder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.TUICustomerServiceConstants;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BranchMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.CollectionMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.EvaluationBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksBranchBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksBranchMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUICustomerServiceProductInfo;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.model.TUICustomerServiceProvider;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceLog;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TUICustomerServicePresenter {
    private static final String TAG = "TUICustomerServicePresenter";
    private TUIMessageBean messageBean;
    private TUICustomerServiceProvider provider;

    public TUICustomerServicePresenter() {
        provider = new TUICustomerServiceProvider();
    }

    public void setMessage(TUIMessageBean currentMessage) {
        messageBean = currentMessage;
    }

    public TUIMessageBean getMessageBean() {
        return messageBean;
    }

    public void getCustomerServiceUserInfo(V2TIMValueCallback<List<V2TIMUserFullInfo>> callback) {
        provider.getCustomerServiceUserInfo(callback);
    }

    public boolean allowSelection() {
        if (messageBean instanceof BranchMessageBean) {
            BranchMessageBean branchMessageBean = (BranchMessageBean) messageBean;
            return branchMessageBean.getBranchBean().getSelectedItem() == null;
        } else if (messageBean instanceof CollectionMessageBean) {
            CollectionMessageBean collectionMessageBean = (CollectionMessageBean) messageBean;
            return collectionMessageBean.getCollectionBean().getSelectedItem() == null;
        } else if (messageBean instanceof TasksBranchMessageBean) {
            TasksBranchMessageBean branchMessageBean = (TasksBranchMessageBean) messageBean;
            if (branchMessageBean.getTasksBranchBean().getNodeStatus() == TasksBranchBean.NodeStatus.CanEdit) {
                return branchMessageBean.getTasksBranchBean().getSelectedItem() == null;
            } else {
                return false;
            }

        } else if (messageBean instanceof TasksCollectionMessageBean) {
            TasksCollectionMessageBean collectionMessageBean = (TasksCollectionMessageBean) messageBean;
            if (collectionMessageBean.getCollectionBean().getNodeStatus() == TasksCollectionBean.NodeStatus.CanEdit) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public void OnItemContentSelected(String content) {
        if (messageBean == null) {
            TUICustomerServiceLog.e(TAG, "OnItemContentSelected, messageBean is null");
            return;
        }

        String chatID = messageBean.getUserId();
        TUIMessageBean messageBean = ChatMessageBuilder.buildTextMessage(content);
        TUIChatService.getInstance().sendMessage(messageBean, chatID, V2TIMConversation.V2TIM_C2C, false);
    }

    public void OnItemContentSelected(String content, String cloudCustomData)  {
        if (messageBean == null) {
            TUICustomerServiceLog.e(TAG, "OnItemContentSelected, messageBean is null");
            return;
        }

        String chatID = messageBean.getUserId();
        TUIMessageBean messageBean = ChatMessageBuilder.buildTextMessage(content, cloudCustomData);
        TUIChatService.getInstance().sendMessage(messageBean, chatID, V2TIMConversation.V2TIM_C2C, false);
    }

    public void sendTextMessage(String userID, String content) {
        TUIMessageBean messageBean = ChatMessageBuilder.buildTextMessage(content);
        TUIChatService.getInstance().sendMessage(messageBean, userID, V2TIMConversation.V2TIM_C2C, false);
    }

    public void sendHelloMessage(String userID) {
        JSONObject getEvaluationSettingJson = new JSONObject();
        try {
            getEvaluationSettingJson.put(TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY, 0);
            getEvaluationSettingJson.put(TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_BUSINESS_ID_SRC_KEY,
                TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_SAY_HELLO);
            JSONObject lanJson = new JSONObject();
            String lang = Locale.getDefault().getLanguage();
            lanJson.put(TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_TRIGGERED_LANGUAGE, lang);
            getEvaluationSettingJson.put(TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_TRIGGERED_CONTENT, lanJson);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if (!TextUtils.isEmpty(getEvaluationSettingJson.toString())) {
            TUIMessageBean messageBean = ChatMessageBuilder.buildCustomMessage(getEvaluationSettingJson.toString(), "", null);
            TUIChatService.getInstance().sendMessage(messageBean, userID, V2TIMConversation.V2TIM_C2C, true);
        }
    }

    public void sendEvaluationMessage(EvaluationBean.Menu submitMenu, String sessionID) {
        if (messageBean == null) {
            TUICustomerServiceLog.e(TAG, "sendEvaluationMessage, messageBean is null");
            return;
        }

        JSONObject submitMenuJson = new JSONObject();
        try {
            submitMenuJson.put(TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY, 0);
            submitMenuJson.put(TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_BUSINESS_ID_SRC_KEY,
                TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_EVALUATION_SELECTED);
            JSONObject menuSelectedJson = new JSONObject();
            menuSelectedJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_ITEM_ID, submitMenu.getId());
            menuSelectedJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_ITEM_CONTENT, submitMenu.getContent());
            menuSelectedJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_SESSION_ID, sessionID);
            submitMenuJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_ITEM_MENU_SELECTED, menuSelectedJson);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if (!TextUtils.isEmpty(submitMenuJson.toString())) {
            String chatID = messageBean.getUserId();
            TUIMessageBean messageBean = ChatMessageBuilder.buildCustomMessage(submitMenuJson.toString(), "", null);
            TUIChatService.getInstance().sendMessage(messageBean, chatID, V2TIMConversation.V2TIM_C2C, true);
        }
    }

    public void triggerEvaluation(String userID) {
        JSONObject triggerEvaluationJson = new JSONObject();
        try {
            triggerEvaluationJson.put(TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY, 0);
            triggerEvaluationJson.put(TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_BUSINESS_ID_SRC_KEY,
                TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_TRIGGER_EVALUATION);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if (!TextUtils.isEmpty(triggerEvaluationJson.toString())) {
            TUIMessageBean messageBean = ChatMessageBuilder.buildCustomMessage(triggerEvaluationJson.toString(), "", null);
            TUIChatService.getInstance().sendMessage(messageBean, userID, V2TIMConversation.V2TIM_C2C, true);
        }
    }

    public void triggerTasksCollection(List<TasksCollectionBean.FormItem> formItems) {
        if (formItems == null) {
            return;
        }
        JSONObject msgJson = new JSONObject();

        try {
            msgJson.put(TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY, 0);
            msgJson.put(TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_BUSINESS_ID_SRC_KEY,
                    TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_TASKS_COLLECTION);

            JSONObject contentJson = new JSONObject();
            JSONArray tasksCollectioMessageJson = new JSONArray();
            for (int i = 0 ; i < formItems.size(); i++) {
                JSONObject itemObj = new JSONObject();
                TasksCollectionBean.FormItem formItem = formItems.get(i);
                itemObj.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_ITEM_NAME, formItem.getName());
                itemObj.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_ITEM_ISREQUIRED, formItem.getIsRequired());
                itemObj.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_ITEM_VARIABLEVALUE, formItem.getVariableValue());
                tasksCollectioMessageJson.put(i,itemObj);
            }
            contentJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_INPUTVARIABLES, tasksCollectioMessageJson);
            msgJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_CONTENT,contentJson);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String chatID = messageBean.getUserId();
        TUIMessageBean messageBean = ChatMessageBuilder.buildCustomMessage(msgJson.toString(), "", null);
        TUIChatService.getInstance().sendMessage(messageBean, chatID, V2TIMConversation.V2TIM_C2C, false);
    }

    public void sendProductMessage(String userID, TUICustomerServiceProductInfo productInfo) {
        if (productInfo == null) {
            return;
        }

        JSONObject productMessageJson = new JSONObject();
        try {
            productMessageJson.put(TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_MESSAGE_KEY, 0);
            productMessageJson.put(TUIConstants.TUICustomerServicePlugin.CUSTOMER_SERVICE_BUSINESS_ID_SRC_KEY,
                TUIConstants.TUICustomerServicePlugin.BUSINESS_ID_SRC_CUSTOMER_SERVICE_CARD);
            JSONObject productJson = new JSONObject();
            productJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_HEADER, productInfo.getName());
            productJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_ITEM_DESCRIPTION, productInfo.getDescription());
            productJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_CARD_PIC, productInfo.getPictureUrl());
            productJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_CARD_URL, productInfo.getJumpUrl());
            productMessageJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_CONTENT, productJson);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if (!TextUtils.isEmpty(productMessageJson.toString())) {
            TUIMessageBean messageBean = ChatMessageBuilder.buildCustomMessage(productMessageJson.toString(), "", null);
            TUIChatService.getInstance().sendMessage(messageBean, userID, V2TIMConversation.V2TIM_C2C, false);
        }
    }
}
