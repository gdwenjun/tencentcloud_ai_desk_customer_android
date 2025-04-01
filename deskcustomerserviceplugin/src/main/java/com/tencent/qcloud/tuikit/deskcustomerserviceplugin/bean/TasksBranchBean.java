package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import java.io.Serializable;
import java.util.List;

public class TasksBranchBean implements Serializable {

    private String head;
    private List<TasksBranchBean.Item> itemList;
    private TasksBranchBean.Item selectedItem;
    private String tail;
    private TasksBranchBean.NodeStatus nodeStatus;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<TasksBranchBean.Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<TasksBranchBean.Item> itemList) {
        this.itemList = itemList;
    }

    public TasksBranchBean.Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(TasksBranchBean.Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public void setNodeStatus(TasksBranchBean.NodeStatus nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public TasksBranchBean.NodeStatus getNodeStatus() {
        return this.nodeStatus;
    }

    public static class Item {
        private String content;
        private String description;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static enum NodeStatus {
        CanEdit,
        NotSubmitted,
        Submitted;
    }
}
