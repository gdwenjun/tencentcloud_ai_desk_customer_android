package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksBranchBean;

public class TasksBranchItemHolder extends RecyclerView.ViewHolder {
    private View rootView;
    private TextView tvContent;
    private TasksBranchListAdapter adapter;
    public TasksBranchItemHolder(@NonNull View itemView) {
        super(itemView);
        rootView = itemView;
        tvContent = itemView.findViewById(R.id.branch_item_content);
    }

    public void layoutViews(TasksBranchBean.Item item, int position) {
        if (item == null || tvContent == null) {
            return;
        }
        tvContent.setText(item.getContent());
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 产品要求点击后先隐藏，再发送消息。这里存在无解的情况是消息要是发送失败了。这里就不能再次显示回列表
                if (rootView != null && rootView.getRootView() != null && rootView.getRootView().findViewById(R.id.branch_item_list) != null) {
                    rootView.getRootView().findViewById(R.id.branch_item_list).setVisibility(View.GONE);
                }
                if (adapter != null && adapter.getPresenter() != null && adapter.getPresenter().allowSelection()) {
                    adapter.getPresenter().OnItemContentSelected(item.getContent());
                }
            }
        });
    }

    public void setAdapter(TasksBranchListAdapter adapter) {
        this.adapter = adapter;
    }
}
