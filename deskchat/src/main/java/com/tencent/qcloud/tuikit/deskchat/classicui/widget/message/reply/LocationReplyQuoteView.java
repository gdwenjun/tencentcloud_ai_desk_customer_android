package com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.reply;

import android.content.Context;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.TUIReplyQuoteView;

public class LocationReplyQuoteView extends TUIReplyQuoteView {
    @Override
    public int getLayoutResourceId() {
        return 0;
    }

    public LocationReplyQuoteView(Context context) {
        super(context);
    }

    @Override
    public void onDrawReplyQuote(TUIReplyQuoteBean quoteBean) {}
}
