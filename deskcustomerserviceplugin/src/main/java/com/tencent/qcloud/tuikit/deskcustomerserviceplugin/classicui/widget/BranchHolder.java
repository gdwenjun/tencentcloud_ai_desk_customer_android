package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BranchBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BranchMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;

public class BranchHolder extends MessageContentHolder {
    private TextView tvTitle;
    private BranchListLayout listLayout;

    public BranchHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_branch_title);
        listLayout = itemView.findViewById(R.id.branch_item_list);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.message_adapter_branch;
    }

    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        BranchMessageBean branchMessageBean = (BranchMessageBean) msg;
        TUICustomerServicePresenter presenter = new TUICustomerServicePresenter();
        presenter.setMessage(branchMessageBean);
        BranchBean branchBean = branchMessageBean.getBranchBean();
        if (branchBean != null) {
            if (TextUtils.isEmpty(branchBean.getHead())) {
                tvTitle.setText("");
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(branchBean.getHead());
            }
            listLayout.setPresenter(presenter);
            listLayout.setBranchItemList(branchBean.getItemList());
        }
    }
}
