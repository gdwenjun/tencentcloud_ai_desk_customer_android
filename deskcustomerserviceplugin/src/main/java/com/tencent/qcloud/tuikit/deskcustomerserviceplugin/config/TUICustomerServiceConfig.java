package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config;

import android.text.TextUtils;
import android.view.View;

import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.TUICustomerServiceConstants;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget.InputViewFloatLayerProxy;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;

import java.util.ArrayList;
import java.util.List;

public class TUICustomerServiceConfig {
    private static TUICustomerServiceConfig instance;

    public static TUICustomerServiceConfig getInstance() {
        if (instance == null) {
            instance = new TUICustomerServiceConfig();
            instance.inputViewFloatLayerDataList = new ArrayList<>();
            TUIInputViewFloatLayerData humanBtnService = new TUIInputViewFloatLayerData();
            humanBtnService.setDefault(true);
            humanBtnService.setIconResourceId(R.drawable.to_human);
            humanBtnService.setPresetId("humanService");
            getInstance().defaultFloatLayerData.add(humanBtnService);

            TUIInputViewFloatLayerData serviceRatingBtn = new TUIInputViewFloatLayerData();
            serviceRatingBtn.setDefault(true);
            serviceRatingBtn.setVisible(false);
            serviceRatingBtn.setPresetId("serviceRating");
            serviceRatingBtn.setIconResourceId(R.drawable.service_rating);
            getInstance().defaultFloatLayerData.add(serviceRatingBtn);

            TUIInputViewFloatLayerData endServiceBtn = new TUIInputViewFloatLayerData();
            endServiceBtn.setDefault(true);
            endServiceBtn.setVisible(false);
            endServiceBtn.setPresetId("endHumanService");
            endServiceBtn.setIconResourceId(R.drawable.end_human_service);
            getInstance().defaultFloatLayerData.add(endServiceBtn);

            instance.inputViewFloatLayerDataList.addAll(getInstance().defaultFloatLayerData);
        }

        return instance;
    }


    private TUICustomerServiceProductInfo productInfo;
    private List<TUIInputViewFloatLayerData> defaultFloatLayerData = new ArrayList<>();
    private List<TUIInputViewFloatLayerData> inputViewFloatLayerDataList = new ArrayList<>();
    private List<String> customerServiceAccounts = new ArrayList<>();

    public boolean getIsShowHumanService() {
        return isShowHumanService;
    }

    public void setShowHumanService(boolean showHumanService) {
        isShowHumanService = showHumanService;
    }

    private boolean isShowHumanService = false;

    private boolean isShowServiceRating = false;

    public void setShowServiceRating(boolean showServiceRating) {
        isShowServiceRating = showServiceRating;
    }

    public boolean getShowServiceRating() {
        return isShowServiceRating;
    }

    private boolean isShowEndHumanService = false;

    public void setShowEndHumanService(boolean showEndHumanService) {
        isShowEndHumanService = showEndHumanService;
    }

    public boolean getShowEndHumanService() {
        return isShowEndHumanService;
    }


    public List<TUIInputViewFloatLayerData> getInputViewFloatLayerDataList() {
        return inputViewFloatLayerDataList;
    }

    private TUICustomerServiceConfig() {
        customerServiceAccounts.add(TUICustomerServiceConstants.DEFAULT_CUSTOMER_SERVICE_ACCOUNT);
    }

    public void setInputViewFloatLayerDataList(List<TUIInputViewFloatLayerData> inputViewFloatLayerDataList) {
        this.inputViewFloatLayerDataList.clear();
        this.inputViewFloatLayerDataList.addAll(defaultFloatLayerData);
        this.inputViewFloatLayerDataList.addAll(inputViewFloatLayerDataList);
    }

    public void setProductInfo(TUICustomerServiceProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public TUICustomerServiceProductInfo getProductInfo() {
        return this.productInfo;
    }

    public List<String> getCustomerServiceAccounts() {
        return customerServiceAccounts;
    }

    public void setCustomerServiceAccounts(List<String> customerServiceAccounts) {
        if (customerServiceAccounts == null) {
            return;
        }

        this.customerServiceAccounts = customerServiceAccounts;
    }

    public boolean isOnlineShopping(String userID) {
        if (TextUtils.isEmpty(userID)) {
            return false;
        }

        return userID.contains("#online_shopping_mall");
    }

}
