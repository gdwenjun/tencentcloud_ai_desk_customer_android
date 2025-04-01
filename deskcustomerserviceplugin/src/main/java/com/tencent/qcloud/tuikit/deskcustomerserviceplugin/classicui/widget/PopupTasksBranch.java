package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tencent.qcloud.tuikit.deskcommon.util.SoftKeyBoardUtil;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;

public class PopupTasksBranch {
    private TasksCollectionMessageBean collectionMessageBean;
    private PopupWindow popupWindow;

    private TextView titleTv;
    private View closeBtn;
    private Button submitBtn;

    private TasksCollectionListLayout listLayout;

    public PopupTasksBranch(Activity activity, TasksCollectionMessageBean msgBean, TUICustomerServicePresenter presenter) {
        init(activity, msgBean, presenter);
        initEvent(activity);
    }

    private void init(Activity activity, TasksCollectionMessageBean msgBean,TUICustomerServicePresenter presenter) {
        View popupView = LayoutInflater.from(activity).inflate(R.layout.popup_tasks_collection_layout, null);
        collectionMessageBean = msgBean;

        titleTv = popupView.findViewById(R.id.popup_card_title);
        closeBtn = popupView.findViewById(R.id.close_btn);
        submitBtn = popupView.findViewById(R.id.popup_submit_btn);

        boolean isShowDetail = !presenter.allowSelection();
        listLayout = popupView.findViewById(R.id.collection_item_list);
        listLayout.setIsShowDetail(isShowDetail);
        listLayout.setPresenter(presenter);
        listLayout.setFormItemList(msgBean.getCollectionBean().getFormItemList());

        if (isShowDetail) {
            submitBtn.setVisibility(View.GONE);
        } else {
            submitBtn.setVisibility(View.VISIBLE);
        }

        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true) {
            @Override
            public void showAtLocation(View anchor, int gravity, int x, int y) {
                if (activity != null && !activity.isFinishing()) {
                    Window dialogWindow = activity.getWindow();
                    startAnimation(dialogWindow, true);
                }
                assert activity != null;
                super.showAtLocation(anchor, gravity, x, y);
            }

            @Override
            public void dismiss() {
                if (activity != null && !activity.isFinishing()) {
                    Window dialogWindow = activity.getWindow();
                    startAnimation(dialogWindow, false);
                }

                super.dismiss();
            }
        };
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(com.tencent.qcloud.tuikit.deskcommon.R.style.PopupInputCardAnim);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    private void initEvent(Activity activity) {
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (activity.getWindow() != null) {
                    SoftKeyBoardUtil.hideKeyBoard(activity.getWindow());
                }
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSubmit = listLayout.onSubmit();
                if (isSubmit)
                    popupWindow.dismiss();
            }
        });
    }

    private void startAnimation(Window window, boolean isShow) {
        ValueAnimator animator;
        if (isShow) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = (float) animation.getAnimatedValue();
                window.setAttributes(lp);
            }
        });
        LinearInterpolator interpolator = new LinearInterpolator();
        animator.setDuration(200);
        animator.setInterpolator(interpolator);
        animator.start();
    }

    public void show(View rootView, int gravity) {
        if (popupWindow != null) {
            popupWindow.showAtLocation(rootView, gravity, 0, 0);
        }
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

}
