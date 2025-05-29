package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.os.Handler;
import android.view.View;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.ThinkingBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.ThinkingMessageBean;

public class ThinkingHolder extends MessageContentHolder {
    private View rootView;
    private ImageView circle1, circle2, circle3;

    public ThinkingHolder(View itemView) {
        super(itemView);
        rootView = itemView;
        circle1 = itemView.findViewById(R.id.circle1);
        circle2 = itemView.findViewById(R.id.circle2);
        circle3 = itemView.findViewById(R.id.circle3);
        startAnimation();
    }

    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        ThinkingMessageBean thinkingMsgBean = (ThinkingMessageBean)msg;
        if (thinkingMsgBean.getThinkingBean() == null) {
            return;
        }
        ThinkingBean thinkingBean = thinkingMsgBean.getThinkingBean();
        if (thinkingBean.getThinkingStatus() == 0) {
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rootView.setVisibility(View.VISIBLE);
            rootView.setLayoutParams(params);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (position < 0 || mAdapter == null || rootView == null) {
                        return;
                    }
                    try {
                        ThinkingMessageBean newThinkingMsgBean = (ThinkingMessageBean)mAdapter.getItem(position);
                        if (newThinkingMsgBean != null) {
                            newThinkingMsgBean.getThinkingBean().setThinkingStatus(1);
                            rootView.setVisibility(View.GONE);
                            mAdapter.onItemRefresh(newThinkingMsgBean);
                        }
                    } catch (ClassCastException e) {
                        TUIChatLog.e("ThinkingHolder", "ClassCastException failed");
                        e.printStackTrace();
                    } catch (Exception e) {
                        TUIChatLog.e("ThinkingHolder", "Exception failed");
                        e.printStackTrace();
                    }
                }
            }, 60000);// 60000
        } else {
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = 0;
            params.width = 0;
            rootView.setVisibility(View.GONE);
            rootView.setLayoutParams(params);
        }
    }

    @Override
    public int getVariableLayout() {
        return R.layout.thinking_layout;
    }
    private void startAnimation() {
        long animationDuration = 1000; // 动画时长，单位毫秒
        long delay = 200; // 延迟时间，单位毫秒

        // 第一个圆圈的动画
        AnimatorSet animatorSet1 = createAnimatorSet(circle1, animationDuration);
        animatorSet1.start();

        // 第二个圆圈的动画，添加延迟
        AnimatorSet animatorSet2 = createAnimatorSet(circle2, animationDuration);
        animatorSet2.setStartDelay(delay);
        animatorSet2.start();

        // 第三个圆圈的动画，添加两倍延迟
        AnimatorSet animatorSet3 = createAnimatorSet(circle3, animationDuration);
        animatorSet3.setStartDelay(2 * delay);
        animatorSet3.start();
    }

    private AnimatorSet createAnimatorSet(ImageView imageView, long duration) {
        // 透明度动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.2f);
        alphaAnimator.setDuration(duration);
        alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        alphaAnimator.setRepeatMode(ObjectAnimator.REVERSE);

        // 缩放动画
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0.6f);
        scaleXAnimator.setDuration(duration);
        scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        scaleXAnimator.setRepeatMode(ObjectAnimator.REVERSE);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0.6f);
        scaleYAnimator.setDuration(duration);
        scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        scaleYAnimator.setRepeatMode(ObjectAnimator.REVERSE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        return animatorSet;
    }

}
