package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.content.Context;
import android.widget.TextView;

import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.TUIReplyQuoteView;
import com.tencent.qcloud.tuikit.deskcommon.component.face.FaceManager;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.ClientTipsBeanReplyQuoteBean;

public class ClientTipsReplyView extends TUIReplyQuoteView<ClientTipsBeanReplyQuoteBean> {
    private TextView textView;

    public ClientTipsReplyView(Context context) {
        super(context);
        textView = findViewById(R.id.text_quote_tv);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.client_tips_quote_text_layout;
    }

    @Override
    public void onDrawReplyQuote(ClientTipsBeanReplyQuoteBean quoteBean) {
        String text = quoteBean.getText();
        FaceManager.handlerEmojiText(textView, text, false);
    }
}
