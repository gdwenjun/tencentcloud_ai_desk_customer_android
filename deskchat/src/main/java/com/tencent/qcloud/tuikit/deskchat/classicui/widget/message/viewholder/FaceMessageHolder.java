package com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.viewholder;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.deskcommon.component.face.FaceManager;
import com.tencent.qcloud.tuikit.deskcommon.util.ScreenUtil;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.message.FaceMessageBean;

public class FaceMessageHolder extends MessageContentHolder {
    private static final int DEFAULT_FACE_MAX_SIZE = 80; // dp

    private final ImageView contentImage;
    private final ImageView videoPlayBtn;
    private final TextView videoDurationText;

    public FaceMessageHolder(View itemView) {
        super(itemView);
        contentImage = itemView.findViewById(R.id.content_image_iv);
        videoPlayBtn = itemView.findViewById(R.id.video_play_btn);
        videoDurationText = itemView.findViewById(R.id.video_duration_tv);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.desk_message_adapter_content_image;
    }

    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        performCustomFace((FaceMessageBean) msg);
    }

    private void performCustomFace(final FaceMessageBean msg) {
        videoPlayBtn.setVisibility(View.GONE);
        videoDurationText.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = contentImage.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(0, 0);
        }
        int defaultFaceSize = ScreenUtil.dip2px(DEFAULT_FACE_MAX_SIZE);
        params.width = defaultFaceSize;
        params.height = defaultFaceSize;
        contentImage.setLayoutParams(params);
        String faceKey = null;
        if (msg.getData() != null) {
            faceKey = new String(msg.getData());
        }
        FaceManager.loadFace(msg.getIndex(), faceKey, contentImage);
    }

    @Override
    public void setHighLightBackground(int color) {
        Drawable drawable = contentImage.getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_OVER);
        }
    }

    @Override
    public void clearHighLightBackground() {
        Drawable drawable = contentImage.getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(null);
        }
    }
}
