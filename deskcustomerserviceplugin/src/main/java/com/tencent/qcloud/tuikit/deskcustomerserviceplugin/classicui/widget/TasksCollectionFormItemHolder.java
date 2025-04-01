package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean.TasksCollectionBean;

public class TasksCollectionFormItemHolder extends RecyclerView.ViewHolder{
    private TasksCollectionListAdapter adapter;
    private TasksCollectionBean.FormItem currentItem;
    private int currentIndex = -1;
    private boolean isShowDetail = false;

    private LinearLayout lllName;
    private TextView lblName;
    private TextView lblRequired;

    private LinearLayout lllValue;
    private RadioGroup lllRadioGroup;
    private EditText inputValue;
    private TextView lblValueText;
    private TextView lblValueError;
    private View vmDashLine;

    public TasksCollectionFormItemHolder(@NonNull View itemView) {
        super(itemView);
        lllName = itemView.findViewById(R.id.lll_name);
        lblName = itemView.findViewById(R.id.collection_name);
        lblRequired = itemView.findViewById(R.id.collection_required);

        lllValue = itemView.findViewById(R.id.lll_value);
        lllRadioGroup = itemView.findViewById(R.id.collection_value_radio_group);
        inputValue = itemView.findViewById(R.id.collection_value_input);
        lblValueText = itemView.findViewById(R.id.collection_value_text);
        lblValueError = itemView.findViewById(R.id.collection_value_error);
        vmDashLine = itemView.findViewById(R.id.collection_dash_line);
    }

    public void layoutViews(TasksCollectionBean.FormItem item, int position) {
        if (item == null) {
            return;
        }
        this.currentItem = item;
        this.currentIndex = position;
        initItemUI();
        initItemEvent();
    }

    public void updateInputStauts(TasksCollectionBean.FormItem item, int position) {
        if (item == null) {
            return;
        }
        this.currentItem = item;
        this.currentIndex = position;
        refreshItemStatus();
    }

    private void initItemUI() {
        lblName.setText(currentItem.getName());
        if (currentItem.getIsRequired() == 1) {
            lblRequired.setVisibility(View.VISIBLE);
        } else {
            lblRequired.setVisibility(View.GONE);
        }
        String variableValue = currentItem.getVariableValue();
        if (TextUtils.isEmpty(variableValue)) {
            variableValue = "";
        }
        if (isShowDetail) {
            lllRadioGroup.setVisibility(View.GONE);
            inputValue.setVisibility(View.GONE);
            lblValueError.setVisibility(TextView.GONE);
            lblValueText.setVisibility(TextView.VISIBLE);
            lblValueText.setText(variableValue);
            lblRequired.setVisibility(View.GONE);
            vmDashLine.setVisibility(View.VISIBLE);
            return;
        }
        vmDashLine.setVisibility(View.VISIBLE);
        if (currentItem.getFormType() == 1) {
            lllRadioGroup.setVisibility(View.VISIBLE);
            inputValue.setVisibility(View.GONE);
            lllRadioGroup.removeAllViews();
            int selectId = -1;
            for (String chooseItem : currentItem.getChooseItemList()) {
                RadioButton radioButton = new RadioButton(lllRadioGroup.getContext());
                radioButton.setText(chooseItem);
                radioButton.setButtonDrawable(R.drawable.tasks_collection_ic_check);
                LinearLayout.LayoutParams radioParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                radioParams.bottomMargin = 15;
                radioParams.leftMargin = 0;

                radioButton.setLayoutParams(radioParams);
                radioButton.setPadding(15,-5,0,0);
                lllRadioGroup.addView(radioButton);
                radioButton.setId(View.generateViewId());
                if (chooseItem.equals(variableValue)) {
                    selectId = radioButton.getId();
                }
            }
            if (selectId != -1) {
                lllRadioGroup.check(selectId);
            }
        } else {
            lllRadioGroup.setVisibility(View.GONE);
            inputValue.setVisibility(View.VISIBLE);
            inputValue.setText(variableValue);
            inputValue.setHint(currentItem.getPlaceholder());
        }
    }

    private void initItemEvent() {
        lllRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = group.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    currentItem.setVariableValue("");
                    return;
                }
                RadioButton selectedRadioButton = group.findViewById(checkedId);
                currentItem.setVariableValue(selectedRadioButton.getText().toString());
                refreshItemStatus();
            }
        });
        inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentItem.setVariableValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                refreshItemStatus();
            }
        });
        inputValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    refreshItemStatus();
                }
            }
        });
    }

    private void refreshItemStatus() {
        if (currentItem.getIsRequired() == 0) {
            lblValueError.setVisibility(TextView.GONE);
            return;
        }
        if (currentItem.getVariableValue() == null || currentItem.getVariableValue().trim().isEmpty()) {
            lblValueError.setVisibility(TextView.VISIBLE);
        } else {
            lblValueError.setVisibility(TextView.GONE);
        }
    }

    public void setAdapter(TasksCollectionListAdapter adapter) {
        this.adapter = adapter;
    }

    public void setIsShowDetail(boolean value) {
        isShowDetail = value;
    }

    public TasksCollectionBean.FormItem getCurrentItemValue() {
        return currentItem;
    }

}
