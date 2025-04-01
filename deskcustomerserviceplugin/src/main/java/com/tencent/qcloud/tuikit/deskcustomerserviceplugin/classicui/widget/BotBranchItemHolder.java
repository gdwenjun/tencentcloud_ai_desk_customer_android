package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BotBranchBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BotBranchMessageBean;

public class BotBranchItemHolder extends RecyclerView.ViewHolder {
    private View rootView;
//    private View vBranchLine;
//    private TextView tvNumber;
    private TextView tvContent;
//    private ImageView ivArrow;
    private BotBranchListAdapter adapter;
    public BotBranchItemHolder(@NonNull View itemView) {
        super(itemView);
        rootView = itemView;
//        vBranchLine = itemView.findViewById(R.id.v_branch_line);
//        tvNumber = itemView.findViewById(R.id.branch_item_number);
        tvContent = itemView.findViewById(R.id.branch_item_content);
//        ivArrow = itemView.findViewById(R.id.branch_item_arrow);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            ivArrow.getBackground().setAutoMirrored(true);
//        }
    }

    public void layoutViews(BotBranchBean.Item item, int position) {
        if (item == null) {
            return;
        }

//        if (position == 0) {
//            vBranchLine.setVisibility(View.GONE);
//        } else {
//            vBranchLine.setVisibility(View.GONE);
//        }

        BotBranchMessageBean botBranchMessageBean = (BotBranchMessageBean) adapter.getPresenter().getMessageBean();
        if (botBranchMessageBean != null) {
//            if (TextUtils.equals(botBranchMessageBean.getBotBranchBean().getSubType(), TUICustomerServiceConstants.BOT_SUBTYPE_WELCOME_MSG)) {
//                tvNumber.setText(String.valueOf((position + 1)));
//                tvNumber.setVisibility(View.GONE);
//            } else {
//                tvNumber.setVisibility(View.GONE);
//            }
        }

        tvContent.setText(item.getContent());
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter != null && adapter.getPresenter() != null) {
                    adapter.getPresenter().OnItemContentSelected(item.getContent());
                }
            }
        });
    }

    public void setAdapter(BotBranchListAdapter adapter) {
        this.adapter = adapter;
    }
}
