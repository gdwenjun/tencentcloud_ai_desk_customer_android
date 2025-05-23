package com.tencent.qcloud.tuikit.deskchat.bean.message;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.TUIChatService;
import com.tencent.qcloud.tuikit.deskchat.bean.CustomHelloMessage;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.CustomLinkReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class CustomLinkMessageBean extends TUIMessageBean {
    private CustomHelloMessage customHelloMessage;

    @Override
    public String onGetDisplayString() {
        return getText();
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        String data = new String(v2TIMMessage.getCustomElem().getData());
        if (!TextUtils.isEmpty(data)) {
            try {
                customHelloMessage = new Gson().fromJson(data, CustomHelloMessage.class);
            } catch (Exception e) {
                TUIChatLog.e("CustomLinkMessageBean", "exception e = " + e);
            }
        }
        if (customHelloMessage != null) {
            setExtra(customHelloMessage.text);
        } else {
            String text = TUIChatService.getAppContext().getString(R.string.no_support_msg);
            setExtra(text);
        }
    }

    public String getText() {
        if (customHelloMessage != null) {
            return customHelloMessage.text;
        }
        return getExtra();
    }

    public String getLink() {
        if (customHelloMessage != null) {
            return customHelloMessage.link;
        }
        return "";
    }

    @Override
    public Class<? extends TUIReplyQuoteBean> getReplyQuoteBeanClass() {
        return CustomLinkReplyQuoteBean.class;
    }
}
