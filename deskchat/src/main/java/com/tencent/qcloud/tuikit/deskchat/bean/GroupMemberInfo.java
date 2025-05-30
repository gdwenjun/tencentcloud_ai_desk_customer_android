package com.tencent.qcloud.tuikit.deskchat.bean;

import android.text.TextUtils;

import com.tencent.imsdk.v2.V2TIMGroupMemberFullInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;

import java.io.Serializable;

public class GroupMemberInfo implements Serializable {
    public static final int ROLE_MEMBER = V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_ROLE_MEMBER;
    public static final int ROLE_ADMIN = V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_ROLE_ADMIN;
    public static final int ROLE_OWNER = V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_ROLE_OWNER;

    private String iconUrl;
    private String account;
    private String signature;
    private String location;
    private String birthday;
    private String nameCard;
    private String friendRemark;
    private String nickName;
    private boolean isTopChat;
    private boolean isFriend;
    private long joinTime;
    private long tinyId;
    private int memberType;
    private int role;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public int getMemberType() {
        return memberType;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public String getFriendRemark() {
        return friendRemark;
    }

    public void setFriendRemark(String friendRemark) {
        this.friendRemark = friendRemark;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public String getDisplayName() {
        String displayName;

        if (!TextUtils.isEmpty(nameCard)) {
            displayName = nameCard;
        } else if (!TextUtils.isEmpty(friendRemark)) {
            displayName = friendRemark;
        } else if (!TextUtils.isEmpty(nickName)) {
            displayName = nickName;
        } else {
            displayName = account;
        }
        return displayName;
    }

    public GroupMemberInfo covertTIMGroupMemberInfo(V2TIMGroupMemberInfo info) {
        if (info instanceof V2TIMGroupMemberFullInfo) {
            V2TIMGroupMemberFullInfo v2TIMGroupMemberFullInfo = (V2TIMGroupMemberFullInfo) info;
            setJoinTime(v2TIMGroupMemberFullInfo.getJoinTime());
            setMemberType(v2TIMGroupMemberFullInfo.getRole());
            setRole(v2TIMGroupMemberFullInfo.getRole());
        }
        setAccount(info.getUserID());
        setNameCard(info.getNameCard());
        setIconUrl(info.getFaceUrl());
        setFriendRemark(info.getFriendRemark());
        setNickName(info.getNickName());
        return this;
    }
}
