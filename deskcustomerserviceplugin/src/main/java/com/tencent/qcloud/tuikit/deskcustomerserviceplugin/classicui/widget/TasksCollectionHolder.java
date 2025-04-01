package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.deskchat.classicui.widget.ChatView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;

public class TasksCollectionHolder extends MessageContentHolder {
    private View rootView;
    private TextView tvTitle;
    private ImageView headerImage;
    private PopupTasksBranch popupTasksBranch;

    public TasksCollectionHolder(View itemView) {
        super(itemView);
        rootView = itemView;
        tvTitle = itemView.findViewById(R.id.tv_collection_title);
        headerImage = itemView.findViewById(R.id.header_image);
    }

    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        TasksCollectionMessageBean collectionMessageBean = (TasksCollectionMessageBean) msg;
        TUICustomerServicePresenter presenter = new TUICustomerServicePresenter();
        presenter.setMessage(collectionMessageBean);
        TasksCollectionBean tasksCollectionBean = collectionMessageBean.getCollectionBean();
        if (tasksCollectionBean == null) {
            return;
        }
        if (msg.isSelf()) {
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = 0;
            params.width = 0;
            rootView.setVisibility(View.GONE);
            rootView.setLayoutParams(params);
            return;
        }

        headerImage.setImageResource(R.drawable.tasks_collection);
        tvTitle.setText(R.string.tasks_collection_submit);
        if (tasksCollectionBean.getNodeStatus() !=  TasksCollectionBean.NodeStatus.CanEdit) {
            headerImage.setImageResource(R.drawable.tasks_collection_selected);
            tvTitle.setText(R.string.tasks_collection_view);
        }

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatView chatView = view.getRootView().findViewById(com.tencent.qcloud.tuikit.deskchat.R.id.chat_layout);
                chatView.getInputLayout().onEmptyClick();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popupTasksBranch = new PopupTasksBranch((Activity) view.getContext(), collectionMessageBean, presenter);
                        popupTasksBranch.setTitle(tasksCollectionBean.getHead());
                        popupTasksBranch.show(view, Gravity.BOTTOM);
                    }
                },200);
            }
        });
    }

    @Override
    public int getVariableLayout() {
        return R.layout.message_adapter_tasks_collection;
    }
}
