package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.bean;

import java.io.Serializable;

public class SeatStatusBean implements Serializable {
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    private String command;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;
}
