package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksCollectionListAdapter extends RecyclerView.Adapter{

    private TUICustomerServicePresenter presenter;
    private List<TasksCollectionBean.FormItem> formItemList = new ArrayList<>();
    private Map<Integer,Integer> updateInputStatus = new HashMap();
    private boolean isShowDetail = false;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        updateInputStatus.clear();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.tasks_collection_item_layout, parent, false);
        TasksCollectionFormItemHolder holder = new TasksCollectionFormItemHolder(rootView);
        holder.setAdapter(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TasksCollectionBean.FormItem branchItem = formItemList.get(position);
        TasksCollectionFormItemHolder branchItemHolder = (TasksCollectionFormItemHolder) holder;
        branchItemHolder.setIsShowDetail(this.isShowDetail);
        if (updateInputStatus.containsKey(position)) {
            branchItemHolder.updateInputStauts(branchItem, position);
            updateInputStatus.remove(position);
        } else {
            branchItemHolder.layoutViews(branchItem, position);
        }
    }


    public boolean onSubmit() {
        boolean isOk = true;
        for (int i = 0; i < this.formItemList.size(); i++) {
            TasksCollectionBean.FormItem item = formItemList.get(i);
            if (item.getIsRequired() != 1) {
                continue;
            }
            String value = item.getVariableValue();
            if (TextUtils.isEmpty(value)) {
                isOk = false;
                updateInputStatus.put(i,i);
                notifyItemChanged(i);
            }
        }
        if (isOk) {
            presenter.triggerTasksCollection(this.formItemList);
        }
        return isOk;
    }

    @Override
    public int getItemCount() {
        return formItemList.size();
    }

    public List<TasksCollectionBean.FormItem> getItemList() {
        return formItemList;
    }

    public void setPresenter(TUICustomerServicePresenter presenter) {
        this.presenter = presenter;
    }

    public TUICustomerServicePresenter getPresenter() {
        return this.presenter;
    }

    public void setIsShowDetail(boolean value) {
        isShowDetail = value;
    }

    public void setFormItemList(List<TasksCollectionBean.FormItem> itemList) {
        this.formItemList = itemList;
        notifyDataSetChanged();
    }

}
