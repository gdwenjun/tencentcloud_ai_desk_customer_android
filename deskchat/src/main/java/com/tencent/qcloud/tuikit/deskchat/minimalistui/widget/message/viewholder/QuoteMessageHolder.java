package com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.viewholder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.deskcore.interfaces.TUIValueCallback;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskcommon.component.face.FaceManager;
import com.tencent.qcloud.tuikit.deskcommon.component.impl.GlideEngine;
import com.tencent.qcloud.tuikit.deskcommon.util.FileUtil;
import com.tencent.qcloud.tuikit.deskcommon.util.ScreenUtil;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.message.ImageMessageBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.QuoteMessageBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.VideoMessageBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.FaceReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.FileReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.ImageReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.MergeReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.SoundReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.TextReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.VideoReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.presenter.ChatFileDownloadPresenter;
import com.tencent.qcloud.tuikit.deskchat.util.ChatMessageParser;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class QuoteMessageHolder extends TextMessageHolder {
    private final TextView senderNameTv;
    private FrameLayout quoteContentFrameLayout;

    // text/merge
    private final FrameLayout textFrame;
    private final TextView textTv;
    // image/video/face
    private final FrameLayout imageFrame;
    private final ImageView imageIv;
    private final ImageView playIv;
    protected String mImagePath = null;
    // file
    private final FrameLayout fileFrame;
    private final TextView fileNameTv;
    private final ImageView fileIconIv;
    // sound
    private final FrameLayout soundFrame;
    private final TextView soundTimeTv;
    private final ImageView soundIconIv;

    private TUIValueCallback downloadVideoSnapshotCallback;
    private TUIValueCallback downloadImageCallback;

    public QuoteMessageHolder(View itemView) {
        super(itemView);
        quoteContentFrameLayout = itemView.findViewById(com.tencent.qcloud.tuikit.deskcommon.R.id.quote_content_fl);
        LayoutInflater.from(itemView.getContext()).inflate(R.layout.desk_minimalist_quote_message_content_layout, quoteContentFrameLayout);
        senderNameTv = quoteContentFrameLayout.findViewById(R.id.sender_name_tv);

        textFrame = quoteContentFrameLayout.findViewById(R.id.text_msg_area);
        textTv = quoteContentFrameLayout.findViewById(R.id.msg_abstract_tv);

        imageFrame = quoteContentFrameLayout.findViewById(R.id.image_video_msg_area);
        imageIv = quoteContentFrameLayout.findViewById(R.id.msg_image_iv);
        playIv = quoteContentFrameLayout.findViewById(R.id.msg_play_iv);

        fileFrame = quoteContentFrameLayout.findViewById(R.id.file_msg_area);
        fileNameTv = quoteContentFrameLayout.findViewById(R.id.file_name_tv);
        fileIconIv = quoteContentFrameLayout.findViewById(R.id.file_icon_iv);

        soundFrame = quoteContentFrameLayout.findViewById(R.id.sound_msg_area);
        soundTimeTv = quoteContentFrameLayout.findViewById(R.id.sound_msg_time_tv);
        soundIconIv = quoteContentFrameLayout.findViewById(R.id.sound_msg_icon_iv);
    }

    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        extraInfoArea.setVisibility(View.VISIBLE);
        applyCustomConfig();
        msg.setSelectText(msg.getExtra());
        QuoteMessageBean quoteMessageBean = (QuoteMessageBean) msg;
        TUIMessageBean replyContentBean = quoteMessageBean.getContentMessageBean();
        String replyContent = replyContentBean.getExtra();
        FaceManager.handlerEmojiText(timeInLineTextLayout.getTextView(), replyContent, false);
        String senderName = quoteMessageBean.getOriginMsgSender();
        TUIMessageBean originMessage = quoteMessageBean.getOriginMessageBean();
        if (originMessage != null) {
            if (originMessage.isRevoked()) {
                senderNameTv.setVisibility(View.GONE);
            } else {
                senderNameTv.setVisibility(View.VISIBLE);
            }
            senderName = originMessage.getUserDisplayName();
        }
        senderNameTv.setText(senderName + ": ");
        setOnTimeInLineTextClickListener(msg);
        if (quoteMessageBean.isAbstractEnable()) {
            performMsgAbstract(quoteMessageBean);
            quoteContentFrameLayout.setVisibility(View.VISIBLE);
        } else {
            quoteContentFrameLayout.setVisibility(View.GONE);
        }

        msgArea.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        quoteContentFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onReplyMessageClick(view, quoteMessageBean);
                }
            }
        });
        if (isForwardMode || isMessageDetailMode) {
            return;
        }
        if (!TextUtils.isEmpty(replyContent)) {
            FaceManager.handlerEmojiText(timeInLineTextLayout.getTextView(), replyContent, false);
        }

        if (floatMode) {
            quoteContentFrameLayout.setVisibility(View.GONE);
        }
    }

    private void performMsgAbstract(QuoteMessageBean quoteMessageBean) {
        resetAll();
        TUIMessageBean originMessage = quoteMessageBean.getOriginMessageBean();

        TUIReplyQuoteBean replyQuoteBean = quoteMessageBean.getReplyQuoteBean();
        if (originMessage != null) {
            if (originMessage.isRevoked()) {
                performTextMessage(itemView.getResources().getString(R.string.chat_quote_origin_message_revoked));
            } else {
                performQuote(replyQuoteBean, quoteMessageBean);
            }
        } else {
            performNotFound(replyQuoteBean, quoteMessageBean);
        }
    }

    private void resetAll() {
        textFrame.setVisibility(View.GONE);
        imageFrame.setVisibility(View.GONE);
        fileFrame.setVisibility(View.GONE);
        soundFrame.setVisibility(View.GONE);
        playIv.setVisibility(View.GONE);
    }

    private void performNotFound(TUIReplyQuoteBean quoteMessageBean, QuoteMessageBean replyMessageBean) {
        String typeStr = ChatMessageParser.getMsgTypeStr(quoteMessageBean.getMessageType());
        String abstractStr = quoteMessageBean.getDefaultAbstract();
        if (ChatMessageParser.isFileType(quoteMessageBean.getMessageType())) {
            abstractStr = "";
        }
        performTextMessage(typeStr + abstractStr);
    }

    private void performTextMessage(String text) {
        textFrame.setVisibility(View.VISIBLE);
        FaceManager.handlerEmojiText(textTv, text, false);
    }

    private void performImage(TUIReplyQuoteBean replyQuoteBean) {
        imageFrame.setVisibility(View.VISIBLE);
        int imageRadius = ScreenUtil.dip2px(1.92f);

        if (replyQuoteBean instanceof FaceReplyQuoteBean) {
            FaceReplyQuoteBean faceReplyQuoteBean = (FaceReplyQuoteBean) replyQuoteBean;
            String faceKey = new String(faceReplyQuoteBean.getData());
            ViewGroup.LayoutParams params = imageIv.getLayoutParams();
            if (params != null) {
                int maxSize = itemView.getResources().getDimensionPixelSize(R.dimen.reply_message_image_size);
                params.width = maxSize;
                params.height = maxSize;
                imageIv.setLayoutParams(params);
            }
            FaceManager.loadFace(faceReplyQuoteBean.getIndex(), faceKey, imageIv);

        } else if (replyQuoteBean instanceof ImageReplyQuoteBean) {
            ImageMessageBean messageBean = (ImageMessageBean) replyQuoteBean.getMessageBean();
            imageIv.setLayoutParams(getImageParams(imageIv.getLayoutParams(), messageBean.getImgWidth(), messageBean.getImgHeight()));
            String imagePath = ChatFileDownloadPresenter.getImagePath(messageBean);
            if (FileUtil.isFileExists(imagePath)) {
                GlideEngine.loadCornerImageWithoutPlaceHolder(imageIv, imagePath, null, imageRadius);
            } else {
                GlideEngine.clear(imageIv);
                downloadImageCallback = new TUIValueCallback() {
                    @Override
                    public void onProgress(long currentSize, long totalSize) {
                        TUIChatLog.i("downloadImage progress current:", currentSize + ", total:" + totalSize);
                    }

                    @Override
                    public void onError(int code, String desc) {
                        TUIChatLog.e("MessageAdapter img getImage", code + ":" + desc);
                    }

                    @Override
                    public void onSuccess(Object obj) {
                        GlideEngine.loadCornerImageWithoutPlaceHolder(imageIv, imagePath, null, imageRadius);
                    }
                };
                ChatFileDownloadPresenter.downloadImage(messageBean, downloadImageCallback);
            }
        } else if (replyQuoteBean instanceof VideoReplyQuoteBean) {
            VideoMessageBean messageBean = (VideoMessageBean) replyQuoteBean.getMessageBean();
            ViewGroup.LayoutParams layoutParams = getImageParams(imageIv.getLayoutParams(), messageBean.getImgWidth(), messageBean.getImgHeight());
            imageIv.setLayoutParams(layoutParams);
            playIv.setLayoutParams(layoutParams);
            playIv.setVisibility(View.VISIBLE);
            String snapshotPath = ChatFileDownloadPresenter.getVideoSnapshotPath(messageBean);
            if (FileUtil.isFileExists(snapshotPath)) {
                GlideEngine.loadCornerImageWithoutPlaceHolder(imageIv, snapshotPath, null, imageRadius);
            } else {
                GlideEngine.clear(imageIv);
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
                        GlideEngine.loadCornerImageWithoutPlaceHolder(imageIv, snapshotPath, null, imageRadius);
                    }
                };
                ChatFileDownloadPresenter.downloadVideoSnapshot(messageBean, downloadVideoSnapshotCallback);
            }
        }
    }

    protected ViewGroup.LayoutParams getImageParams(ViewGroup.LayoutParams params, int width, int height) {
        if (width == 0 || height == 0) {
            return params;
        }
        int maxSize = itemView.getResources().getDimensionPixelSize(R.dimen.reply_message_image_size);
        if (width > height) {
            params.width = maxSize;
            params.height = maxSize * height / width;
        } else {
            params.width = maxSize * width / height;
            params.height = maxSize;
        }
        return params;
    }

    private void performFile(FileReplyQuoteBean fileReplyQuoteBean) {
        fileFrame.setVisibility(View.VISIBLE);
        fileNameTv.setText(fileReplyQuoteBean.getFileName());
    }

    private void performSound(SoundReplyQuoteBean soundReplyQuoteBean) {
        soundFrame.setVisibility(View.VISIBLE);
        soundTimeTv.setText(soundReplyQuoteBean.getDuring() + "''");
    }

    private void performQuote(TUIReplyQuoteBean replyQuoteBean, QuoteMessageBean messageBean) {
        if (replyQuoteBean instanceof TextReplyQuoteBean || replyQuoteBean instanceof MergeReplyQuoteBean) {
            String text;
            if (replyQuoteBean instanceof TextReplyQuoteBean) {
                text = ((TextReplyQuoteBean) replyQuoteBean).getText();
            } else {
                text = messageBean.getOriginMsgAbstract();
            }
            performTextMessage(text);
        } else if (replyQuoteBean instanceof FileReplyQuoteBean) {
            performFile((FileReplyQuoteBean) replyQuoteBean);
        } else if (replyQuoteBean instanceof SoundReplyQuoteBean) {
            performSound((SoundReplyQuoteBean) replyQuoteBean);
        } else if (replyQuoteBean instanceof ImageReplyQuoteBean || replyQuoteBean instanceof VideoReplyQuoteBean
            || replyQuoteBean instanceof FaceReplyQuoteBean) {
            performImage(replyQuoteBean);
        }
    }
}
