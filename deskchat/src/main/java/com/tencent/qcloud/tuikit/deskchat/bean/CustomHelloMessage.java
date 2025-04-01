package com.tencent.qcloud.tuikit.deskchat.bean;

import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.TUIChatConstants;
import com.tencent.qcloud.tuikit.deskchat.TUIChatService;

import java.io.Serializable;

public class CustomHelloMessage implements Serializable {
    public static final int CUSTOM_HELLO_ACTION_ID = 3;

    public String businessID = TUIChatConstants.BUSINESS_ID_CUSTOM_HELLO;
    public String text = TUIChatService.getAppContext().getString(R.string.welcome_tip);
    public String link = "https://cloud.tencent.com/document/product/269/3794";

    public int version = TUIChatConstants.JSON_VERSION_UNKNOWN;
}
