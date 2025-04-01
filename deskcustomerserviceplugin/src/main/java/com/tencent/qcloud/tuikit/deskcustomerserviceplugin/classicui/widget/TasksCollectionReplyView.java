package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.content.Context;
import android.widget.TextView;

import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.TUIReplyQuoteView;
import com.tencent.qcloud.tuikit.deskcommon.component.face.FaceManager;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionMessageReplyQuoteBean;

public class TasksCollectionReplyView  extends TUIReplyQuoteView<TasksCollectionMessageReplyQuoteBean> {
    private TextView textView;

    @Override
    public int getLayoutResourceId() {
        return R.layout.branch_reply_quote_text_layout;
    }

    public TasksCollectionReplyView(Context context) {
        super(context);
        textView = findViewById(R.id.text_quote_tv);
    }

    @Override
    public void onDrawReplyQuote(TasksCollectionMessageReplyQuoteBean quoteBean) {
        String text = quoteBean.getText();
        FaceManager.handlerEmojiText(textView, text, false);
    }
}
