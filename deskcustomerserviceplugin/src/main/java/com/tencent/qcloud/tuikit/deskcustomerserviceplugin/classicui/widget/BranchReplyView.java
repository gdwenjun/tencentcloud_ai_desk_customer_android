package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.content.Context;
import android.widget.TextView;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.TUIReplyQuoteView;
import com.tencent.qcloud.tuikit.deskcommon.component.face.FaceManager;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BranchMessageReplyQuoteBean;

public class BranchReplyView extends TUIReplyQuoteView<BranchMessageReplyQuoteBean> {
    private TextView textView;

    public BranchReplyView(Context context) {
        super(context);
        textView = findViewById(R.id.text_quote_tv);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.branch_reply_quote_text_layout;
    }

    @Override
    public void onDrawReplyQuote(BranchMessageReplyQuoteBean quoteBean) {
        String text = quoteBean.getText();
        FaceManager.handlerEmojiText(textView, text, false);
    }
}
