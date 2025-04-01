package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.tencent.qcloud.tuikit.deskcommon.component.CustomLinearLayoutManager;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;

import java.util.List;

public class TasksCollectionListLayout extends RecyclerView {
    private TasksCollectionListAdapter adapter;

    public TasksCollectionListLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public TasksCollectionListLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TasksCollectionListLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setItemViewCacheSize(0);
        setHasFixedSize(true);
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(linearLayoutManager);
        SimpleItemAnimator animator = (SimpleItemAnimator) getItemAnimator();
        if (animator != null) {
            animator.setSupportsChangeAnimations(false);
        }

        adapter = new TasksCollectionListAdapter();
        super.setAdapter(adapter);
    }

    public void setIsShowDetail(boolean value) {
        if (adapter != null) {
            adapter.setIsShowDetail(value);
        }
    }

    public void setPresenter(TUICustomerServicePresenter presenter) {
        if (presenter != null) {
            adapter.setPresenter(presenter);
        }
    }

    public void setFormItemList(List<TasksCollectionBean.FormItem> itemList) {
        adapter.setFormItemList(itemList);
    }

    public boolean onSubmit() {
        return adapter.onSubmit();
    }
}
