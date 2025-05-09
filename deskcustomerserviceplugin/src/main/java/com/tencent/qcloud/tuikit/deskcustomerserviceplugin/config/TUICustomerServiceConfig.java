package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config;

import android.text.TextUtils;
import android.view.View;

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
            getInstance().defaultFloatLayerData.setDefault(true);
            getInstance().defaultFloatLayerData.setIconResourceId(R.drawable.to_human);

            getInstance().defaultFloatLayerData.setOnItemClickListener(new InputViewFloatLayerProxy.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TUICustomerServicePresenter persenter = new TUICustomerServicePresenter();
                    persenter.sendTextMessage(TUICustomerServiceConstants.DEFAULT_CUSTOMER_SERVICE_ACCOUNT,getInstance().defaultFloatLayerData.getContent());
                }
            });
            instance.inputViewFloatLayerDataList.add(getInstance().defaultFloatLayerData);
        }

        return instance;
    }


    private TUICustomerServiceProductInfo productInfo;
    private TUIInputViewFloatLayerData defaultFloatLayerData = new TUIInputViewFloatLayerData();
    private List<TUIInputViewFloatLayerData> inputViewFloatLayerDataList = new ArrayList<>();
    private List<String> customerServiceAccounts = new ArrayList<>();

    public boolean getIsShowHumanService() {
        return isShowHumanService;
    }

    public void setShowHumanService(boolean showHumanService) {
        isShowHumanService = showHumanService;
    }

    private boolean isShowHumanService = false;

    public List<TUIInputViewFloatLayerData> getInputViewFloatLayerDataList() {
        return inputViewFloatLayerDataList;
    }

    private TUICustomerServiceConfig() {
        customerServiceAccounts.add(TUICustomerServiceConstants.DEFAULT_CUSTOMER_SERVICE_ACCOUNT);
    }

    public void setInputViewFloatLayerDataList(List<TUIInputViewFloatLayerData> inputViewFloatLayerDataList) {
        this.inputViewFloatLayerDataList.clear();
        this.inputViewFloatLayerDataList.add(defaultFloatLayerData);
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

    private boolean showItemsAfterClick = false;

    public boolean getShowItemsAfterClick() {
        return showItemsAfterClick;
    }

    public void setShowItemsAfterClick(boolean value) {
        showItemsAfterClick = value;
    }
}
