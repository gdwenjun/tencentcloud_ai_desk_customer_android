package com.tencent.qcloud.tuikit.deskchat.classicui.widget.input;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.tencent.qcloud.tuikit.deskchat.R;

public class ReplyPreviewBar extends RelativeLayout {
    public ReplyPreviewBar(Context context) {
        super(context);
        initViews();
    }

    public ReplyPreviewBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ReplyPreviewBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.desk_reply_preview_layout, this);
    }
}
