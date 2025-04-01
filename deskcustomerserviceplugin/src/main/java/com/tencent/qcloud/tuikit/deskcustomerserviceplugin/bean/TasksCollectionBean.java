package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import java.io.Serializable;
import java.util.List;

public class TasksCollectionBean implements Serializable {

    private String head;
    private List<TasksCollectionBean.FormItem> formItemList;
    private TasksCollectionBean.NodeStatus nodeStatus;


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<TasksCollectionBean.FormItem> getFormItemList() {
        return formItemList;
    }

    public void setFormItemList(List<TasksCollectionBean.FormItem> formItemList) {
        this.formItemList = formItemList;
    }

    public void setNodeStatus(TasksCollectionBean.NodeStatus nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public TasksCollectionBean.NodeStatus getNodeStatus() {
        return this.nodeStatus;
    }

    public static enum NodeStatus {
        CanEdit,
        NotSubmitted,
        Submitted;
    }
    public static class FormItem {
        private String type;
        private String name;
        private List<String> chooseItemList;

        public List<String> getChooseItemList() {
            return chooseItemList;
        }

        public void setChooseItemList(List<String> chooseItemList) {
            this.chooseItemList = chooseItemList;
        }

        public void setVariableValue(String variableValue) {
            this.variableValue = variableValue;
        }

        private int formType; // 0代表输入，1代表选择
        private String placeholder;

        public void setIsRequired(int isRequired) {
            this.isRequired = isRequired;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        public void setFormType(int formType) {
            this.formType = formType;
        }

        private int isRequired;

        public String getVariableValue() {
            return variableValue;
        }

        public int getIsRequired() {
            return isRequired;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public int getFormType() {
            return formType;
        }

        private String variableValue;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
