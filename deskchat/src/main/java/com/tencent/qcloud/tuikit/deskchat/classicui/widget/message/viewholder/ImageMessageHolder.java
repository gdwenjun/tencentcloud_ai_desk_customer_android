package com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.viewholder;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.deskcore.interfaces.TUIValueCallback;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.deskcommon.component.impl.GlideEngine;
import com.tencent.qcloud.tuikit.deskcommon.util.FileUtil;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.TUIChatConstants;
import com.tencent.qcloud.tuikit.deskchat.TUIChatService;
import com.tencent.qcloud.tuikit.deskchat.bean.message.ImageMessageBean;
import com.tencent.qcloud.tuikit.deskchat.component.imagevideoscan.ImageVideoScanActivity;
import com.tencent.qcloud.tuikit.deskchat.component.progress.ChatRingProgressBar;
import com.tencent.qcloud.tuikit.deskchat.component.progress.ProgressPresenter;
import com.tencent.qcloud.tuikit.deskchat.presenter.ChatFileDownloadPresenter;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

import java.io.Serializable;

public class ImageMessageHolder extends MessageContentHolder {
    private static final int DEFAULT_MAX_SIZE = 540;
    private static final int DEFAULT_RADIUS = 10;
    private ImageView contentImage;
    private ImageView videoPlayBtn;
    private TextView videoDurationText;
    private FrameLayout progressContainer;
    private ChatRingProgressBar fileProgressBar;
    private TextView progressText;
    private ImageView progressIcon;

    private TUIValueCallback downloadCallback;
    private ProgressPresenter.ProgressListener progressListener;
    private String msgID;

    public ImageMessageHolder(View itemView) {
        super(itemView);
        contentImage = itemView.findViewById(R.id.content_image_iv);
        videoPlayBtn = itemView.findViewById(R.id.video_play_btn);
        videoDurationText = itemView.findViewById(R.id.video_duration_tv);
        progressContainer = itemView.findViewById(R.id.progress_container);
        fileProgressBar = itemView.findViewById(R.id.file_progress_bar);
        progressText = itemView.findViewById(R.id.file_progress_text);
        progressIcon = itemView.findViewById(R.id.file_progress_icon);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.desk_message_adapter_content_image;
    }

    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        msgID = msg.getId();
        if (hasRiskContent) {
            progressContainer.setVisibility(View.GONE);
            videoPlayBtn.setVisibility(View.GONE);
            videoDurationText.setVisibility(View.GONE);
            sendingProgress.setVisibility(View.GONE);
            downloadCallback = null;
            progressListener = null;
            ViewGroup.LayoutParams params = contentImage.getLayoutParams();
            params.width = itemView.getResources().getDimensionPixelSize(R.dimen.chat_image_message_error_size);
            params.height = itemView.getResources().getDimensionPixelSize(R.dimen.chat_image_message_error_size);
            GlideEngine.loadImage(contentImage, R.drawable.chat_risk_image_replace_icon);
            contentImage.setLayoutParams(params);
            if (msg.getStatus() == TUIMessageBean.MSG_STATUS_SEND_FAIL) {
                setRiskContent(itemView.getResources().getString(R.string.chat_risk_send_message_failed_alert));
            } else {
                setRiskContent(itemView.getResources().getString(R.string.chat_risk_image_message_alert));
            }
            msgContentFrame.setOnClickListener(null);
        } else {
            performImage((ImageMessageBean) msg, position);
        }
    }

    private ViewGroup.LayoutParams getImageParams(ViewGroup.LayoutParams params, final ImageMessageBean msg) {
        if (msg.getImgWidth() == 0 || msg.getImgHeight() == 0) {
            params.width = DEFAULT_MAX_SIZE;
            params.height = DEFAULT_MAX_SIZE;
            return params;
        }
        if (msg.getImgWidth() > msg.getImgHeight()) {
            params.width = DEFAULT_MAX_SIZE;
            params.height = DEFAULT_MAX_SIZE * msg.getImgHeight() / msg.getImgWidth();
        } else {
            params.width = DEFAULT_MAX_SIZE * msg.getImgWidth() / msg.getImgHeight();
            params.height = DEFAULT_MAX_SIZE;
        }
        return params;
    }

    private void performImage(final ImageMessageBean msg, final int position) {
        ViewGroup.LayoutParams params = getImageParams(contentImage.getLayoutParams(), msg);
        contentImage.setLayoutParams(params);
        ViewGroup.LayoutParams progressParams = getImageParams(progressContainer.getLayoutParams(), msg);
        progressContainer.setLayoutParams(progressParams);
        progressContainer.setVisibility(View.GONE);
        progressText.setVisibility(View.GONE);
        videoPlayBtn.setVisibility(View.GONE);
        videoDurationText.setVisibility(View.GONE);
        sendingProgress.setVisibility(View.GONE);

        progressListener = this::updateProgress;
        ProgressPresenter.registerProgressListener(msg.getId(), progressListener);

        String imagePath = ChatFileDownloadPresenter.getImagePath(msg);
        if (FileUtil.isFileExists(imagePath)) {
            loadImage(msg, imagePath);
            progressContainer.setVisibility(View.GONE);
        } else {
            GlideEngine.clear(contentImage);
            progressContainer.setVisibility(View.VISIBLE);
            String finalImagePath = imagePath;
            downloadCallback = new TUIValueCallback() {
                @Override
                public void onProgress(long currentSize, long totalSize) {
                    int progress = Math.round(currentSize * 1.0f * 100 / totalSize);
                    ProgressPresenter.updateProgress(msg.getId(), progress);

                    TUIChatLog.i("downloadImage progress current:", currentSize + ", total:" + totalSize);
                }

                @Override
                public void onError(int code, String desc) {
                    TUIChatLog.e("MessageAdapter img getImage", code + ":" + desc);
                    if (mAdapter != null) {
                        mAdapter.onItemRefresh(msg);
                    }
                }

                @Override
                public void onSuccess(Object obj) {
                    ProgressPresenter.updateProgress(msg.getId(), 100);
                    loadImage(msg, finalImagePath);
                }
            };
            ChatFileDownloadPresenter.downloadImage(msg, downloadCallback);
        }
        if (isMultiSelectMode) {
            contentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onMessageClick(v, msg);
                    }
                }
            });
            return;
        }
        contentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TUIChatService.getAppContext(), ImageVideoScanActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                if (isForwardMode) {
                    if (getForwardDataSource() != null && !getForwardDataSource().isEmpty()) {
                        intent.putExtra(TUIChatConstants.OPEN_MESSAGES_SCAN_FORWARD, (Serializable) getForwardDataSource());
                    }
                }

                intent.putExtra(TUIChatConstants.OPEN_MESSAGE_SCAN, msg);
                intent.putExtra(TUIChatConstants.FORWARD_MODE, isForwardMode);
                TUIChatService.getAppContext().startActivity(intent);
            }
        });
        contentImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onMessageLongClick(view, msg);
                }
                return true;
            }
        });
        if (!msg.isHasReaction()) {
            setMessageBubbleBackground(null);
            setMessageBubbleZeroPadding();
        }
    }

    private void loadImage(TUIMessageBean messageBean, String finalImagePath) {
        if (TextUtils.equals(msgID, messageBean.getId())) {
            GlideEngine.loadCornerImageWithoutPlaceHolder(contentImage, finalImagePath, null, DEFAULT_RADIUS);
        }
    }

    private void updateProgress(int progress) {
        progressContainer.setVisibility(View.VISIBLE);
        progressText.setVisibility(View.VISIBLE);
        fileProgressBar.setVisibility(View.VISIBLE);
        fileProgressBar.setProgress(progress);
        progressText.setText(progress + "%");
        progressIcon.setVisibility(View.GONE);
        if (progress == 100) {
            progressContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void setHighLightBackground(int color) {
        Drawable drawable = contentImage.getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
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
