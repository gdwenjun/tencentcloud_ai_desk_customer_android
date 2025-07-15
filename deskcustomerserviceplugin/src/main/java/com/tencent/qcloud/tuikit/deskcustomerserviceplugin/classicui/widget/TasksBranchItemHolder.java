package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.TUICustomerServiceConstants;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksBranchBean;

import org.json.JSONException;
import org.json.JSONObject;

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
                TasksBranchBean parent = item.getParent();
                if (adapter != null && adapter.getPresenter() != null && adapter.getPresenter().allowSelection()) {
                    if(parent != null && parent.getOptionType() == 1) {
                        JSONObject jsonObject = new JSONObject();
                        JSONObject branchOptionInfoJson =new JSONObject();
                        try {
                            branchOptionInfoJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_TASKID, parent.getTaskInfo().getTaskID());
                            branchOptionInfoJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_ENV, parent.getTaskInfo().getEnv());
                            branchOptionInfoJson.put(TUICustomerServiceConstants.CUSTOMER_SERVICE_NODEID, parent.getTaskInfo().getNodeID());
                            jsonObject.putOpt("BranchOptionInfo", branchOptionInfoJson);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        String cloudCustomData = jsonObject.toString();
                        adapter.getPresenter().OnItemContentSelected(item.getContent(), cloudCustomData);
                    } else {
                        adapter.getPresenter().OnItemContentSelected(item.getContent());
                    }
                }
            }
        });
    }

    public void setAdapter(TasksBranchListAdapter adapter) {
        this.adapter = adapter;
    }
}
