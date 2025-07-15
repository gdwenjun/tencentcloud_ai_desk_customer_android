package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import java.io.Serializable;
import java.util.List;

public class TasksBranchBean implements Serializable {

    private String head;
    private List<TasksBranchBean.Item> itemList;
    private TasksBranchBean.Item selectedItem;
    private String tail;
    private TasksBranchBean.NodeStatus nodeStatus;
    // 0表示快捷选项（点击后选项消失），1表示固定按钮（点击后选项不消失）
    private int optionType;

    private TaskInfo taskInfo;

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public int getOptionType() {
        return optionType;
    }

    public void setOptionType(int optionType) {
        this.optionType = optionType;
    }

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
        private TasksBranchBean parent;

        public TasksBranchBean getParent() {
            return parent;
        }

        public void setParent(TasksBranchBean parent) {
            this.parent = parent;
        }

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


    public static class TaskInfo {
        public int getTaskID() {
            return taskID;
        }

        public void setTaskID(int taskID) {
            this.taskID = taskID;
        }

        public String getNodeID() {
            return nodeID;
        }

        public void setNodeID(String nodeID) {
            this.nodeID = nodeID;
        }

        public String getEnv() {
            return env;
        }

        public void setEnv(String env) {
            this.env = env;
        }

        private int taskID;
        private String nodeID;
        private String env;
    }
}
