package com.tencent.qcloud.tuikit.deskcontact.bean;

import android.text.TextUtils;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMFriendInfo;
import com.tencent.imsdk.v2.V2TIMGroupInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberFullInfo;
import com.tencent.imsdk.v2.V2TIMUserStatus;
import com.tencent.qcloud.deskcore.interfaces.TUIExtensionEventListener;
import com.tencent.qcloud.tuikit.deskcontact.component.indexlib.indexbar.bean.BaseIndexPinyinBean;

public class ContactItemBean extends BaseIndexPinyinBean implements Comparable<ContactItemBean> {
    public static final int TYPE_C2C = V2TIMConversation.V2TIM_C2C;
    public static final int TYPE_GROUP = V2TIMConversation.V2TIM_GROUP;
    public static final int TYPE_INVALID = V2TIMConversation.CONVERSATION_TYPE_INVALID;

    public static final int ITEM_BEAN_TYPE_CONTACT = 1;
    public static final int ITEM_BEAN_TYPE_CONTROLLER = 2;

    public static final String INDEX_STRING_TOP = "↑";
    private String id;
    private boolean isTop;
    private boolean isSelected;
    private boolean isBlackList;
    private String remark;
    private String nickName;
    private String nameCard;
    private String avatarUrl;
    private String signature;
    private boolean isGroup;
    private String groupType;
    private boolean isFriend = false;
    private boolean isEnable = true;
    private int statusType = V2TIMUserStatus.V2TIM_USER_STATUS_UNKNOWN;
    private int itemBeanType = ITEM_BEAN_TYPE_CONTACT;
    private int avatarResID;
    private int weight;
    private TUIExtensionEventListener extensionListener;
    private int unreadCount;

    public ContactItemBean() {}

    public ContactItemBean(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ContactItemBean setId(String id) {
        this.id = id;
        return this;
    }

    public ContactItemBean setItemBeanType(int itemBeanType) {
        this.itemBeanType = itemBeanType;
        return this;
    }

    public int getItemBeanType() {
        return itemBeanType;
    }

    public boolean isTop() {
        return isTop;
    }

    public ContactItemBean setTop(boolean top) {
        isTop = top;
        return this;
    }

    @Override
    public String getTarget() {
        return getDisplayName();
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }

    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public boolean isBlackList() {
        return isBlackList;
    }

    public void setBlackList(boolean blacklist) {
        isBlackList = blacklist;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public ContactItemBean covertTIMFriend(V2TIMFriendInfo friendInfo) {
        if (friendInfo == null) {
            return this;
        }
        setId(friendInfo.getUserID());
        setNickName(friendInfo.getUserProfile().getNickName());
        setAvatarUrl(friendInfo.getUserProfile().getFaceUrl());
        setSignature(friendInfo.getUserProfile().getSelfSignature());
        setRemark(friendInfo.getFriendRemark());
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getNameCard() {
        return nameCard;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getStatusType() {
        return statusType;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

    public int getAvatarResID() {
        return avatarResID;
    }

    public void setAvatarResID(int avatarResID) {
        this.avatarResID = avatarResID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public TUIExtensionEventListener getExtensionListener() {
        return extensionListener;
    }

    public void setExtensionListener(TUIExtensionEventListener extensionListener) {
        this.extensionListener = extensionListener;
    }

    public ContactItemBean covertTIMGroupBaseInfo(V2TIMGroupInfo group) {
        if (group == null) {
            return this;
        }
        setId(group.getGroupID());
        setRemark(group.getGroupName());
        setAvatarUrl(group.getFaceUrl());
        setGroup(true);
        setGroupType(group.getGroupType());
        return this;
    }

    public ContactItemBean covertTIMGroupMemberFullInfo(V2TIMGroupMemberFullInfo member) {
        if (member == null) {
            return this;
        }
        setId(member.getUserID());
        setNameCard(member.getNameCard());
        setRemark(member.getFriendRemark());
        setNickName(member.getNickName());

        setAvatarUrl(member.getFaceUrl());
        setGroup(false);
        return this;
    }

    public String getDisplayName() {
        if (!TextUtils.isEmpty(nameCard)) {
            return nameCard;
        } else if (!TextUtils.isEmpty(remark)) {
            return remark;
        } else if (!TextUtils.isEmpty(nickName)) {
            return nickName;
        } else {
            return id;
        }
    }

    @Override
    public int compareTo(ContactItemBean contactItemBean) {
        return contactItemBean.getWeight() - this.weight;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public int getUnreadCount() {
        return unreadCount;
    }
}
