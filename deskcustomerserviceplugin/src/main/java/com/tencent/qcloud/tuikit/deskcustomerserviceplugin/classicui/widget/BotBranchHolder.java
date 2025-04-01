package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.MessageBaseHolder;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.TUICustomerServiceConstants;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BotBranchBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.BotBranchMessageBean;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;

public class BotBranchHolder extends MessageBaseHolder {
    private LinearLayout llWelcomeContent;
    private LinearLayout llRefresh;
    private TextView tvWelcomeContent;
    private TextView tvClarifyMessage;
    private ImageView ivRefresh;
    private BotBranchListLayout listLayout;

    public BotBranchHolder(View itemView) {
        super(itemView);
        llWelcomeContent = itemView.findViewById(R.id.ll_title_welcome_content);
        tvWelcomeContent = itemView.findViewById(R.id.tv_welcome_content);
        llRefresh = itemView.findViewById(R.id.ll_refresh);
        tvClarifyMessage = itemView.findViewById(R.id.tv_title_clarify_message);
        listLayout = itemView.findViewById(R.id.branch_question_item_list);
        ivRefresh = itemView.findViewById(R.id.iv_refresh);

        float[] sendRadii = new float[]{
                36f, 36f, // 左上角
                0f, 36f,   // 右上角
                0f, 0f,   // 右下角
                0f, 0f  // 左下角
        };
        float[] receiveRadii = new float[]{
                36f, 0f, // 左上角
                36f, 36f,   // 右上角
                36f, 36f,   // 右下角
                36f, 36f  // 左下角
        };
        GradientDrawable welcomContentDrawable = new GradientDrawable();
        welcomContentDrawable.setCornerRadii(receiveRadii);
        welcomContentDrawable.setShape(GradientDrawable.RECTANGLE);
        welcomContentDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        welcomContentDrawable.setOrientation(GradientDrawable.Orientation.TR_BL);
        welcomContentDrawable.setColor(0xFFFFFFFF);
        llWelcomeContent.setBackground(welcomContentDrawable);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            llWelcomeContent.getBackground().setAutoMirrored(true);
            ivRefresh.getBackground().setAutoMirrored(true);
        }
    }

    @Override
    public int getVariableLayout() {
        return R.layout.bot_message_adapter_branch;
    }

    @Override
    public void layoutViews(TUIMessageBean msg, int position) {

        BotBranchMessageBean botBranchMessageBean = (BotBranchMessageBean) msg;
        TUICustomerServicePresenter presenter = new TUICustomerServicePresenter();
        presenter.setMessage(botBranchMessageBean);
        BotBranchBean branchBean = botBranchMessageBean.getBotBranchBean();
        if (branchBean != null) {
            if (TextUtils.equals(branchBean.getSubType(), TUICustomerServiceConstants.BOT_SUBTYPE_WELCOME_MSG)) {
                tvClarifyMessage.setVisibility(View.GONE);
                tvWelcomeContent.setText(branchBean.getTitle());
                llWelcomeContent.setVisibility(View.VISIBLE);
                llRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listLayout.refreshData();
                    }
                });
            } else {
                llWelcomeContent.setVisibility(View.GONE);
                tvClarifyMessage.setText(branchBean.getTitle());
                tvClarifyMessage.setVisibility(View.VISIBLE);
            }

            listLayout.setPresenter(presenter);
            listLayout.setBotBranchItemList(branchBean.getItemList());
        }
    }
}
