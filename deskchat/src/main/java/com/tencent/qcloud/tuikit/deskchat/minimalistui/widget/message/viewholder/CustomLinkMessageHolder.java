package com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.tencent.qcloud.deskcore.TUIThemeManager;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.minimalistui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.TUIChatService;
import com.tencent.qcloud.tuikit.deskchat.bean.message.CustomLinkMessageBean;

public class CustomLinkMessageHolder extends MessageContentHolder {
    private TextView textView;
    private TextView linkView;

    public CustomLinkMessageHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.test_custom_message_tv);
        linkView = itemView.findViewById(R.id.link_tv);
        timeInLineTextLayout = itemView.findViewById(R.id.time_in_line_text);
    }

    public static final String TAG = CustomLinkMessageHolder.class.getSimpleName();

    @Override
    public int getVariableLayout() {
        return R.layout.desk_test_custom_message_layout1;
    }

    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        String text = "";
        String link = "";
        if (msg instanceof CustomLinkMessageBean) {
            text = ((CustomLinkMessageBean) msg).getText();
            link = ((CustomLinkMessageBean) msg).getLink();
        }

        if (!msg.isSelf()) {
            textView.setTextColor(
                textView.getResources().getColor(TUIThemeManager.getAttrResId(textView.getContext(), R.attr.chat_other_custom_msg_text_color)));
            linkView.setTextColor(
                textView.getResources().getColor(TUIThemeManager.getAttrResId(textView.getContext(), R.attr.chat_other_custom_msg_link_color)));
        } else {
            textView.setTextColor(
                textView.getResources().getColor(TUIThemeManager.getAttrResId(textView.getContext(), R.attr.chat_self_custom_msg_text_color)));
            linkView.setTextColor(
                textView.getResources().getColor(TUIThemeManager.getAttrResId(textView.getContext(), R.attr.chat_self_custom_msg_link_color)));
        }

        textView.setText(text);
        msgContentFrame.setClickable(true);
        String finalLink = link;
        msgContentFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri contentUrl = Uri.parse(finalLink);
                intent.setData(contentUrl);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                TUIChatService.getAppContext().startActivity(intent);
            }
        });
    }
}
