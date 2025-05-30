package com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.reply;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.tencent.qcloud.deskcore.interfaces.TUIValueCallback;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcommon.component.impl.GlideEngine;
import com.tencent.qcloud.tuikit.deskcommon.util.FileUtil;
import com.tencent.qcloud.tuikit.deskchat.bean.message.VideoMessageBean;
import com.tencent.qcloud.tuikit.deskchat.presenter.ChatFileDownloadPresenter;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class VideoReplyQuoteView extends ImageReplyQuoteView {
    private TUIValueCallback downloadVideoSnapshotCallback;

    public VideoReplyQuoteView(Context context) {
        super(context);
    }

    @Override
    public void onDrawReplyQuote(TUIReplyQuoteBean quoteBean) {
        VideoMessageBean messageBean = (VideoMessageBean) quoteBean.getMessageBean();

        ViewGroup.LayoutParams layoutParams = getImageParams(imageMsgIv.getLayoutParams(), messageBean.getImgWidth(), messageBean.getImgHeight());
        imageMsgIv.setLayoutParams(layoutParams);
        videoPlayIv.setLayoutParams(layoutParams);
        videoPlayIv.setVisibility(View.VISIBLE);
        String snapshotPath = ChatFileDownloadPresenter.getVideoSnapshotPath(messageBean);
        if (FileUtil.isFileExists(snapshotPath)) {
            GlideEngine.loadCornerImageWithoutPlaceHolder(imageMsgIv, snapshotPath, null, DEFAULT_RADIUS);
        } else {
            GlideEngine.clear(imageMsgIv);
            downloadVideoSnapshotCallback = new TUIValueCallback() {
                @Override
                public void onProgress(long currentSize, long totalSize) {
                    TUIChatLog.i("downloadSnapshot progress current:", currentSize + ", total:" + totalSize);
                }

                @Override
                public void onError(int code, String desc) {
                    TUIChatLog.e("MessageAdapter video getImage", code + ":" + desc);
                }

                @Override
                public void onSuccess(Object obj) {
                    GlideEngine.loadCornerImageWithoutPlaceHolder(imageMsgIv, snapshotPath, null, DEFAULT_RADIUS);
                }
            };
            ChatFileDownloadPresenter.downloadVideoSnapshot(messageBean, downloadVideoSnapshotCallback);
        }
    }
}
