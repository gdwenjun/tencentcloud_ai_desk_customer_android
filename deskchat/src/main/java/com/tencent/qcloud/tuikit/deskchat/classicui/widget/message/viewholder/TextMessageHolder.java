package com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.viewholder;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.deskcore.TUIThemeManager;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.deskcommon.component.face.FaceManager;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.TUIChatService;
import com.tencent.qcloud.tuikit.deskchat.bean.message.TextMessageBean;
import com.tencent.qcloud.tuikit.deskchat.config.classicui.TUIChatConfigClassic;
import com.tencent.qcloud.tuikit.deskchat.config.TUIChatConfigs;

public class TextMessageHolder extends MessageContentHolder {
    protected TextView msgBodyText;
    private View.OnClickListener onTextClickListener;
    private final GestureDetector gestureDetector;

    public TextMessageHolder(View itemView) {
        super(itemView);
        msgBodyText = itemView.findViewById(R.id.msg_body_tv);
        msgBodyText.setTextIsSelectable(true);
        msgBodyText.setHighlightColor(itemView.getResources().getColor(com.tencent.qcloud.tuikit.deskcommon.R.color.timcommon_text_highlight_color));
        gestureDetector = new GestureDetector(itemView.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (onTextClickListener != null) {
                    onTextClickListener.onClick(msgBodyText);
                }
                return super.onSingleTapUp(e);
            }
        });
    }

    @Override
    public int getVariableLayout() {
        return R.layout.desk_message_adapter_content_text;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        if (!(msg instanceof TextMessageBean)) {
            return;
        }
        if (hasRiskContent) {
            setRiskContent(itemView.getResources().getString(R.string.chat_risk_send_message_failed_alert));
        }
        TextMessageBean textMessageBean = (TextMessageBean) msg;

        if (isForwardMode || isReplyDetailMode || !textMessageBean.isSelf()) {
            int otherTextColorResId = TUIThemeManager.getAttrResId(msgBodyText.getContext(), R.attr.chat_other_msg_text_color);
            int otherTextColor = msgBodyText.getResources().getColor(otherTextColorResId);
            msgBodyText.setTextColor(otherTextColor);
        } else {
            int selfTextColorResId = TUIThemeManager.getAttrResId(msgBodyText.getContext(), R.attr.chat_self_msg_text_color);
            int selfTextColor = msgBodyText.getResources().getColor(selfTextColorResId);
            msgBodyText.setTextColor(selfTextColor);
        }

        msgBodyText.setVisibility(View.VISIBLE);

        msgArea.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (selectableTextHelper != null) {
                    selectableTextHelper.selectAll();
                }
                return true;
            }
        });
        applyCustomConfig();

        if (textMessageBean.getText() != null) {
            FaceManager.handlerEmojiText(msgBodyText, textMessageBean.getText(), false);
        } else if (!TextUtils.isEmpty(textMessageBean.getExtra())) {
            FaceManager.handlerEmojiText(msgBodyText, textMessageBean.getExtra(), false);
        } else {
            FaceManager.handlerEmojiText(msgBodyText, TUIChatService.getAppContext().getString(R.string.no_support_msg), false);
        }
        if (isForwardMode || isReplyDetailMode) {
            return;
        }
        setSelectableTextHelper(msg, msgBodyText, position);

        msgBodyText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        setTextClickListener((v) -> {
            if (onItemClickListener != null) {
                onItemClickListener.onMessageClick(v, msg);
            }
        });
        setTextColor(msg);
    }
    public void setTextColor(TUIMessageBean msg){
        if(TUICore.getService(TUIConstants.Service.TUI_CUSTOMER_PLUGIN) == null){
            return;
        }
        int id = TUIChatConfigs.getGeneralConfig().getChatBgSourceID();
        if(id == -1){
            return;
        }
        if(msg.isSelf()){
            msgBodyText.setTextColor(Color.argb(255,255,255,255));
        }else{
            msgBodyText.setTextColor(Color.argb(255,0,0,0));
        }
    }

    protected void setTextClickListener(View.OnClickListener listener) {
        this.onTextClickListener = listener;
    }

    protected void applyCustomConfig() {
        if (isLayoutOnStart) {
            int receiveTextMessageColor = TUIChatConfigClassic.getReceiveTextMessageColor();
            if (receiveTextMessageColor != TUIChatConfigClassic.UNDEFINED) {
                msgBodyText.setTextColor(receiveTextMessageColor);
            }
            int receiveTextMessageFontSize = TUIChatConfigClassic.getReceiveTextMessageFontSize();
            if (receiveTextMessageFontSize != TUIChatConfigClassic.UNDEFINED) {
                msgBodyText.setTextSize(receiveTextMessageFontSize);
            }
        } else {
            int sendTextMessageColor = TUIChatConfigClassic.getSendTextMessageColor();
            if (sendTextMessageColor != TUIChatConfigClassic.UNDEFINED) {
                msgBodyText.setTextColor(sendTextMessageColor);
            }
            int sendTextMessageFontSize = TUIChatConfigClassic.getSendTextMessageFontSize();
            if (sendTextMessageFontSize != TUIChatConfigClassic.UNDEFINED) {
                msgBodyText.setTextSize(sendTextMessageFontSize);
            }
        }
    }
}
