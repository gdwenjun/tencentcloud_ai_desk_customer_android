package com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.deskcore.interfaces.TUIValueCallback;
import com.tencent.qcloud.deskcore.util.ToastUtil;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.minimalistui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.deskcommon.util.DateTimeUtil;
import com.tencent.qcloud.tuikit.deskcommon.util.FileUtil;
import com.tencent.qcloud.tuikit.deskcommon.util.ThreadUtils;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.TUIChatService;
import com.tencent.qcloud.tuikit.deskchat.bean.message.SoundMessageBean;
import com.tencent.qcloud.tuikit.deskchat.component.audio.AudioPlayer;
import com.tencent.qcloud.tuikit.deskchat.presenter.ChatFileDownloadPresenter;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

import java.util.Timer;
import java.util.TimerTask;

public class SoundMessageHolder extends MessageContentHolder {
    private static final String TAG = "SoundMessageHolder";
    private static final int UNREAD = 0;
    private static final int READ = 1;

    private TextView audioTimeText;
    private ImageView audioPlayImage;
    private Timer mTimer;
    private int times = 0;

    private TUIValueCallback downloadSoundCallback;

    public SoundMessageHolder(View itemView) {
        super(itemView);
        audioTimeText = itemView.findViewById(R.id.audio_time_tv);
        audioPlayImage = itemView.findViewById(R.id.audio_play_iv);
        timeInLineTextLayout = itemView.findViewById(R.id.time_in_line_text);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.desk_minimalist_message_adapter_content_audio;
    }

    @Override
    public void layoutVariableViews(final TUIMessageBean msg, final int position) {
        SoundMessageBean message = (SoundMessageBean) msg;

        int duration = (int) message.getDuration();
        if (duration == 0) {
            duration = 1;
        }

        String timeString = DateTimeUtil.formatSecondsTo00(duration);
        int finalDuration = duration;
        resetTimerStatus(timeString);
        msgContentFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMultiSelectMode) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onMessageClick(view, message);
                    }
                    return;
                }
                String soundPath = ChatFileDownloadPresenter.getSoundPath(message);
                if (AudioPlayer.getInstance().isPlaying()) {
                    AudioPlayer.getInstance().stopPlay();
                    resetTimerStatus(timeString);
                    if (TextUtils.equals(AudioPlayer.getInstance().getPath(), soundPath)) {
                        return;
                    }
                }
                if (!FileUtil.isFileExists(soundPath)) {
                    ToastUtil.toastShortMessage(TUIChatService.getAppContext().getString(R.string.voice_play_tip));
                    getSound(message);
                    return;
                }

                if (finalDuration > 1) {
                    if (mTimer == null) {
                        mTimer = new Timer();
                    }
                    mTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (times < finalDuration) {
                                        times++;
                                        String s = DateTimeUtil.formatSecondsTo00(times);
                                        audioTimeText.setText(s);
                                    } else {
                                        audioTimeText.setText(timeString);
                                    }
                                }
                            });
                        }
                    }, 0, 1000);
                }

                audioPlayImage.setImageResource(R.drawable.chat_audio_stop_btn_ic);
                message.setPlayed();
                AudioPlayer.getInstance().startPlay(soundPath, new AudioPlayer.Callback() {
                    @Override
                    public void onCompletion(Boolean success) {
                        audioPlayImage.post(new Runnable() {
                            @Override
                            public void run() {
                                audioPlayImage.setImageResource(R.drawable.chat_audio_play_btn_ic);
                            }
                        });
                        resetTimerStatus(timeString);
                    }
                });
            }
        });
    }

    private void resetTimerStatus(String timeString) {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        audioTimeText.setText(timeString);
        times = 0;
    }

    private void getSound(final SoundMessageBean messageBean) {
        downloadSoundCallback = new TUIValueCallback() {
            @Override
            public void onProgress(long currentSize, long totalSize) {
                TUIChatLog.i(TAG, "downloadSound progress current: " + currentSize + ", total:" + totalSize);
            }

            @Override
            public void onError(int code, String desc) {
                TUIChatLog.e(TAG, "getSoundToFile failed code = " + code + ", info = " + desc);
                ToastUtil.toastLongMessage("getSoundToFile failed code = " + code + ", info = " + desc);
            }

            @Override
            public void onSuccess(Object obj) {
                TUIChatLog.i(TAG, "get sound success");
            }
        };
        ChatFileDownloadPresenter.downloadSound(messageBean, downloadSoundCallback);
    }
}
