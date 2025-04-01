package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.MessageBaseHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksBranchBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksBranchMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUICustomerServiceConfig;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;

public class TasksBranchHolder extends MessageBaseHolder {
    private TextView tvTitle;
    private TasksBranchListLayout listLayout;


    public TasksBranchHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_branch_title);
        float[] receiveRadii = new float[]{
                36f, 0f, // 左上角
                36f, 36f,   // 右上角
                36f, 36f,   // 右下角
                36f, 36f  // 左下角
        };
        GradientDrawable titleGradientDrawable = new GradientDrawable();
        titleGradientDrawable.setCornerRadii(receiveRadii);
        titleGradientDrawable.setShape(GradientDrawable.RECTANGLE);
        titleGradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        titleGradientDrawable.setOrientation(GradientDrawable.Orientation.TR_BL);
        titleGradientDrawable.setColor(0xFFFFFFFF);
        tvTitle.setBackground(titleGradientDrawable);
        listLayout = itemView.findViewById(R.id.branch_item_list);
    }

    @Override
    public void layoutViews(TUIMessageBean msg, int position)  {
        TasksBranchMessageBean branchMessageBean = (TasksBranchMessageBean) msg;
        TUICustomerServicePresenter presenter = new TUICustomerServicePresenter();
        presenter.setMessage(branchMessageBean);
        TasksBranchBean branchBean = branchMessageBean.getTasksBranchBean();
        if (branchBean != null) {
            if (TextUtils.isEmpty(branchBean.getHead())) {
                tvTitle.setText("");
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(branchBean.getHead());
            }
            listLayout.setPresenter(presenter);
            listLayout.setVisibility(View.VISIBLE);
            if (!presenter.allowSelection() && !TUICustomerServiceConfig.getInstance().getShowItemsAfterClick()) {
                listLayout.setVisibility(View.GONE);
            }
            listLayout.setBranchItemList(branchBean.getItemList());
        }
    }

    @Override
    public int getVariableLayout() {
        return R.layout.message_adapter_tasks_branch;
    }
}
