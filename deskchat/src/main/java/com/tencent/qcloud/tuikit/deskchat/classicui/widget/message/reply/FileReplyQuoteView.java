package com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.reply;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.deskcore.TUIThemeManager;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.TUIReplyQuoteView;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.FileReplyQuoteBean;

public class FileReplyQuoteView extends TUIReplyQuoteView {
    private View fileMsgLayout;
    private ImageView fileMsgIcon;
    private TextView fileMsgTv;
    
    @Override
    public int getLayoutResourceId() {
        return R.layout.desk_chat_reply_quote_file_layout;
    }

    public FileReplyQuoteView(Context context) {
        super(context);
        fileMsgLayout = findViewById(R.id.file_msg_layout);
        fileMsgIcon = findViewById(R.id.file_msg_icon_iv);
        fileMsgTv = findViewById(R.id.file_msg_name_tv);
    }

    @Override
    public void onDrawReplyQuote(TUIReplyQuoteBean quoteBean) {
        fileMsgLayout.setVisibility(View.VISIBLE);
        if (quoteBean instanceof FileReplyQuoteBean) {
            fileMsgTv.setText(((FileReplyQuoteBean) quoteBean).getFileName());
        }
    }

    @Override
    public void setSelf(boolean isSelf) {
        if (!isSelf) {
            fileMsgTv.setTextColor(
                fileMsgTv.getResources().getColor(TUIThemeManager.getAttrResId(fileMsgTv.getContext(), R.attr.chat_other_reply_quote_text_color)));
        } else {
            fileMsgTv.setTextColor(
                fileMsgTv.getResources().getColor(TUIThemeManager.getAttrResId(fileMsgTv.getContext(), R.attr.chat_self_reply_quote_text_color)));
        }
    }
}
