package com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.reply;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcommon.component.face.FaceManager;
import com.tencent.qcloud.tuikit.deskcommon.minimalistui.widget.message.TUIReplyQuoteView;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.FaceReplyQuoteBean;

public class FaceReplyQuoteView extends TUIReplyQuoteView {
    private final ImageView contentImage;

    @Override
    public int getLayoutResourceId() {
        return R.layout.desk_chat_reply_quote_face_layout;
    }

    public FaceReplyQuoteView(Context context) {
        super(context);
        contentImage = findViewById(R.id.content_image_iv);
    }

    @Override
    public void onDrawReplyQuote(TUIReplyQuoteBean quoteBean) {
        FaceReplyQuoteBean faceReplyQuoteBean = (FaceReplyQuoteBean) quoteBean;

        String faceKey = new String(faceReplyQuoteBean.getData());
        ViewGroup.LayoutParams params = contentImage.getLayoutParams();
        if (params != null) {
            int maxSize = getResources().getDimensionPixelSize(R.dimen.reply_message_image_size);
            params.width = maxSize;
            params.height = maxSize;
            contentImage.setLayoutParams(params);
        }
        FaceManager.loadFace(faceReplyQuoteBean.getIndex(), faceKey, contentImage);
    }
}
