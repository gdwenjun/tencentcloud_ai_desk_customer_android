package com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.reply;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.deskcore.TUIThemeManager;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcommon.minimalistui.widget.message.TUIReplyQuoteView;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.SoundReplyQuoteBean;

public class SoundReplyQuoteView extends TUIReplyQuoteView {
    private View soundMsgLayout;
    private ImageView soundMsgIcon;
    private TextView soundMsgTv;
    
    public SoundReplyQuoteView(Context context) {
        super(context);
        soundMsgLayout = findViewById(R.id.sound_msg_layout);
        soundMsgIcon = findViewById(R.id.sound_msg_icon_iv);
        soundMsgTv = findViewById(R.id.sound_msg_time_tv);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.desk_chat_reply_quote_sound_layout;
    }

    @Override
    public void onDrawReplyQuote(TUIReplyQuoteBean quoteBean) {
        soundMsgLayout.setVisibility(View.VISIBLE);
        if (quoteBean instanceof SoundReplyQuoteBean) {
            soundMsgTv.setText(((SoundReplyQuoteBean) quoteBean).getDuring() + "''");
        }
    }

    @Override
    public void setSelf(boolean isSelf) {
        if (!isSelf) {
            soundMsgTv.setTextColor(
                soundMsgTv.getResources().getColor(TUIThemeManager.getAttrResId(soundMsgTv.getContext(), R.attr.chat_other_reply_quote_text_color)));
        } else {
            soundMsgTv.setTextColor(
                soundMsgTv.getResources().getColor(TUIThemeManager.getAttrResId(soundMsgTv.getContext(), R.attr.chat_self_reply_quote_text_color)));
        }
    }
}
