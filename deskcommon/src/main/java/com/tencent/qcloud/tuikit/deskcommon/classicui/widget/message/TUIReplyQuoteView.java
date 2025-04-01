package com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;

public abstract class TUIReplyQuoteView<T extends TUIReplyQuoteBean<?>> extends FrameLayout {

    public abstract int getLayoutResourceId();

    public TUIReplyQuoteView(Context context) {
        super(context);
        int resId = getLayoutResourceId();
        if (resId != 0) {
            LayoutInflater.from(context).inflate(resId, this, true);
        }
    }

    public abstract void onDrawReplyQuote(T quoteBean);

    /**
     *
     * Whether the original message sender is himself, used for different UI displays
     * 
     * @param isSelf
     */
    public void setSelf(boolean isSelf) {}

}
