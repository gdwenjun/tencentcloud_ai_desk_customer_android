package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksBranchBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;

import java.util.ArrayList;
import java.util.List;

public class TasksBranchListAdapter extends RecyclerView.Adapter {
    private TUICustomerServicePresenter presenter;
    private List<TasksBranchBean.Item> branchItemList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.tasks_branch_item_layout, parent, false);
        TasksBranchItemHolder holder = new TasksBranchItemHolder(rootView);
        holder.setAdapter(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TasksBranchBean.Item branchItem = branchItemList.get(position);
        TasksBranchItemHolder branchItemHolder = (TasksBranchItemHolder) holder;
        branchItemHolder.layoutViews(branchItem, position);
    }

    @Override
    public int getItemCount() {
        return branchItemList.size();
    }

    public void setPresenter(TUICustomerServicePresenter presenter) {
        this.presenter = presenter;
    }

    public TUICustomerServicePresenter getPresenter() {
        return this.presenter;
    }

    public void setBranchItemList(List<TasksBranchBean.Item> itemList) {
        this.branchItemList = itemList;
        notifyDataSetChanged();
    }
}
