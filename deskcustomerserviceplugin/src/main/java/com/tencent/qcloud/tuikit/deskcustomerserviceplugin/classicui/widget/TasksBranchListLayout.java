package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.content.Context;

import androidx.annotation.NonNull;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.tencent.qcloud.tuikit.deskcommon.component.CustomLinearLayoutManager;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksBranchBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;
import java.util.List;

public class TasksBranchListLayout  extends RecyclerView {
    private TasksBranchListAdapter adapter;

    public TasksBranchListLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public TasksBranchListLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TasksBranchListLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        adapter = new TasksBranchListAdapter();
        super.setAdapter(adapter);
    }

    public void setPresenter(TUICustomerServicePresenter presenter) {
        if (presenter != null) {
            adapter.setPresenter(presenter);
        }
    }

    public void setBranchItemList(List<TasksBranchBean.Item> itemList) {
        adapter.setBranchItemList(itemList);
    }
}
