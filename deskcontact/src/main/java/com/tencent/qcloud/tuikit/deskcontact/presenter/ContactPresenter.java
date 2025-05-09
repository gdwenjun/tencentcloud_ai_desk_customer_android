package com.tencent.qcloud.tuikit.deskcontact.presenter;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMUserStatus;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.deskcore.TUILogin;
import com.tencent.qcloud.deskcore.interfaces.TUIExtensionEventListener;
import com.tencent.qcloud.deskcore.interfaces.TUIExtensionInfo;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuikit.deskcommon.util.ThreadUtils;
import com.tencent.qcloud.tuikit.deskcontact.R;
import com.tencent.qcloud.tuikit.deskcontact.TUIContactConstants;
import com.tencent.qcloud.tuikit.deskcontact.TUIContactService;
import com.tencent.qcloud.tuikit.deskcontact.bean.ContactItemBean;
import com.tencent.qcloud.tuikit.deskcontact.bean.FriendApplicationBean;
import com.tencent.qcloud.tuikit.deskcontact.bean.GroupInfo;
import com.tencent.qcloud.tuikit.deskcontact.bean.MessageCustom;
import com.tencent.qcloud.tuikit.deskcontact.config.TUIContactConfig;
import com.tencent.qcloud.tuikit.deskcontact.interfaces.ContactEventListener;
import com.tencent.qcloud.tuikit.deskcontact.interfaces.IContactListView;
import com.tencent.qcloud.tuikit.deskcontact.model.ContactProvider;
import com.tencent.qcloud.tuikit.deskcontact.util.ContactUtils;
import com.tencent.qcloud.tuikit.deskcontact.util.TUIContactLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ContactPresenter {
    private static final String TAG = ContactPresenter.class.getSimpleName();

    private final ContactProvider provider;

    private IContactListView contactListView;

    private final List<ContactItemBean> dataSource = new ArrayList<>();

    // Strong references avoid GC
    private ContactEventListener friendListListener;
    private ContactEventListener blackListListener;
    private IUIKitCallback<Void> getUserStatusCallback;
    private boolean isClassicStyle;

    private boolean isSelectForCall = false;

    public ContactItemBean newContacts;
    public ContactItemBean groupChats;
    public ContactItemBean blackList;

    public ContactPresenter() {
        initDefaultContactItemBean();
        provider = new ContactProvider();
        provider.setNextSeq(0);
    }

    private void initDefaultContactItemBean() {
        newContacts = new ContactItemBean(TUIContactService.getAppContext().getResources().getString(R.string.new_friend))
                .setItemBeanType(ContactItemBean.ITEM_BEAN_TYPE_CONTROLLER)
                .setTop(true);
        newContacts.setBaseIndexTag(ContactItemBean.INDEX_STRING_TOP);

        groupChats = new ContactItemBean(TUIContactService.getAppContext().getResources().getString(R.string.group))
                .setItemBeanType(ContactItemBean.ITEM_BEAN_TYPE_CONTROLLER)
                .setTop(true);
        groupChats.setBaseIndexTag(ContactItemBean.INDEX_STRING_TOP);

        blackList = new ContactItemBean(TUIContactService.getAppContext().getResources().getString(R.string.blacklist))
                .setItemBeanType(ContactItemBean.ITEM_BEAN_TYPE_CONTROLLER)
                .setTop(true);
        blackList.setBaseIndexTag(ContactItemBean.INDEX_STRING_TOP);
    }

    public void setContactListView(IContactListView contactListView) {
        this.contactListView = contactListView;
    }

    public void setIsClassicStyle(boolean isClassicStyle) {
        this.isClassicStyle = isClassicStyle;
    }

    public void setFriendListListener() {
        friendListListener = new ContactEventListener() {
            @Override
            public void onFriendListAdded(List<ContactItemBean> users) {
                onDataListAdd(users);
            }

            @Override
            public void onFriendListDeleted(List<String> userList) {
                onDataListDeleted(userList);
            }

            @Override
            public void onFriendInfoChanged(List<ContactItemBean> infoList) {
                ContactPresenter.this.onFriendInfoChanged(infoList);
            }

            @Override
            public void onFriendRemarkChanged(String id, String remark) {
                ContactPresenter.this.onFriendRemarkChanged(id, remark);
            }

            @Override
            public void onFriendApplicationListAdded(List<FriendApplicationBean> applicationList) {
                ContactPresenter.this.getFriendApplicationUnReadCount();
            }

            @Override
            public void onFriendApplicationListDeleted(List<String> userIDList) {
                ContactPresenter.this.getFriendApplicationUnReadCount();
            }

            @Override
            public void onFriendApplicationListRead() {
                ContactPresenter.this.getFriendApplicationUnReadCount();
            }

            @Override
            public void onUserStatusChanged(List<V2TIMUserStatus> userStatusList) {
                ContactPresenter.this.onUserStatusChanged(userStatusList);
            }

            @Override
            public void refreshUserStatusFragmentUI() {
                ContactPresenter.this.notifyDataSourceChanged();
            }
        };
        TUIContactService.getInstance().addContactEventListener(friendListListener);
    }

    public void setBlackListListener() {
        blackListListener = new ContactEventListener() {
            @Override
            public void onBlackListAdd(List<ContactItemBean> infoList) {
                onDataListAdd(infoList);
            }

            @Override
            public void onBlackListDeleted(List<String> userList) {
                onDataListDeleted(userList);
            }
        };
        TUIContactService.getInstance().addContactEventListener(blackListListener);
    }

    public void setIsForCall(boolean isSelectForCall) {
        this.isSelectForCall = isSelectForCall;
    }

    public void loadDataSource(int dataSourceType) {
        IUIKitCallback<List<ContactItemBean>> callback = new IUIKitCallback<List<ContactItemBean>>() {
            @Override
            public void onSuccess(List<ContactItemBean> data) {
                TUIContactLog.i(TAG, "load data source success , loadType = " + dataSourceType);
                onDataLoaded(data, dataSourceType);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                TUIContactLog.e(TAG,
                    "load data source error , loadType = " + dataSourceType + "  "
                        + "errCode = " + errCode + " errMsg = " + errMsg);
                onDataLoaded(new ArrayList<>(), dataSourceType);
            }
        };

        dataSource.clear();
        switch (dataSourceType) {
            case IContactListView.DataSource.FRIEND_LIST:
                provider.loadFriendListDataAsync(callback);
                break;
            case IContactListView.DataSource.BLACK_LIST:
                provider.loadBlackListData(callback);
                break;
            case IContactListView.DataSource.GROUP_LIST:
                provider.loadGroupListData(callback);
                break;
            case IContactListView.DataSource.CONTACT_LIST:
                dataSource.add(newContacts);
                dataSource.add(groupChats);
                dataSource.add(blackList);
                dataSource.addAll(getExtensionControllerMoreList());
                provider.loadFriendListDataAsync(callback);
                getFriendApplicationUnReadCount();
                break;
            default:
                break;
        }
    }

    public void reloadContactList() {
        loadDataSource(IContactListView.DataSource.CONTACT_LIST);
    }

    private List<ContactItemBean> getExtensionControllerMoreList() {
        List<ContactItemBean> contactItemBeanList = new ArrayList<>();
        if (!isClassicStyle) {
            return contactItemBeanList;
        }

        List<TUIExtensionInfo> extensionInfoList = TUICore.getExtensionList(TUIConstants.TUIContact.Extension.ContactItem.CLASSIC_EXTENSION_ID, null);
        for (TUIExtensionInfo extensionInfo : extensionInfoList) {
            if (extensionInfo != null) {
                String name = extensionInfo.getText();
                int iconResID = (int) extensionInfo.getIcon();
                int weight = extensionInfo.getWeight();
                TUIExtensionEventListener listener = extensionInfo.getExtensionListener();
                ContactItemBean contactItemBean = new ContactItemBean(name);
                contactItemBean.setItemBeanType(ContactItemBean.ITEM_BEAN_TYPE_CONTROLLER);
                contactItemBean.setBaseIndexTag(ContactItemBean.INDEX_STRING_TOP);
                contactItemBean.setTop(true);
                contactItemBean.setAvatarResID(iconResID);
                contactItemBean.setWeight(weight);
                contactItemBean.setExtensionListener(listener);
                contactItemBeanList.add(contactItemBean);
            }
        }

        if (contactItemBeanList.size() > 0) {
            Collections.sort(contactItemBeanList);
        }

        return contactItemBeanList;
    }

    public long getNextSeq() {
        return provider == null ? 0 : provider.getNextSeq();
    }

    public void loadGroupMemberList(String groupId) {
        if (!isSelectForCall && getNextSeq() == 0) {
            dataSource.add((ContactItemBean) new ContactItemBean(TUIContactService.getAppContext().getResources().getString(R.string.at_all))
                               .setTop(true)
                               .setBaseIndexTag(ContactItemBean.INDEX_STRING_TOP));
        }
        provider.loadGroupMembers(groupId, new IUIKitCallback<List<ContactItemBean>>() {
            @Override
            public void onSuccess(List<ContactItemBean> data) {
                TUIContactLog.i(TAG, "load data source success , loadType = " + IContactListView.DataSource.GROUP_MEMBER_LIST);
                onDataLoaded(data, IContactListView.DataSource.GROUP_MEMBER_LIST);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                TUIContactLog.e(TAG,
                    "load data source error , loadType = " + IContactListView.DataSource.GROUP_MEMBER_LIST + "  "
                        + "errCode = " + errCode + "  errMsg = " + errMsg);
                onDataLoaded(new ArrayList<>(), IContactListView.DataSource.GROUP_MEMBER_LIST);
            }
        });
    }

    private void onDataLoaded(List<ContactItemBean> loadedData, int dataSourceType) {
        Iterator<ContactItemBean> iterator = loadedData.iterator();
        while (iterator.hasNext()) {
            ContactItemBean loadedBean = iterator.next();
            for (int i = 0; i < dataSource.size(); i++) {
                ContactItemBean cacheBean = dataSource.get(i);
                if (cacheBean.getId().equals(loadedBean.getId())) {
                    dataSource.set(i, loadedBean);
                    iterator.remove();
                    break;
                }
            }
        }

        dataSource.addAll(loadedData);
        notifyDataSourceChanged();
        if (dataSourceType != IContactListView.DataSource.GROUP_LIST) {
            loadContactUserStatus(dataSource);
        }
    }

    private void loadContactUserStatus(List<ContactItemBean> loadedData) {
        getUserStatusCallback = new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                TUIContactLog.i(TAG, "loadContactUserStatus success");
                notifyDataSourceChanged();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                TUIContactLog.e(TAG, "loadContactUserStatus error code = " + errCode + ",des = " + errMsg);
            }
        };
        GetUserStatusHelper.GetUserStatusTask task = new GetUserStatusHelper.GetUserStatusTask();
        task.setLoadedData(loadedData);
        task.setCallback(getUserStatusCallback);
        GetUserStatusHelper.enqueue(task);
    }

    private void notifyDataSourceChanged() {
        if (contactListView != null) {
            contactListView.onDataSourceChanged(dataSource);
        }
    }

    private void onDataListDeleted(List<String> userList) {
        Iterator<ContactItemBean> userIterator = dataSource.iterator();
        while (userIterator.hasNext()) {
            ContactItemBean contactItemBean = userIterator.next();
            for (String id : userList) {
                if (TextUtils.equals(id, contactItemBean.getId())) {
                    userIterator.remove();
                }
            }
        }
        notifyDataSourceChanged();
    }

    public void onUserStatusChanged(List<V2TIMUserStatus> userStatusList) {
        if (!TUIContactConfig.getInstance().isShowUserStatus()) {
            return;
        }
        HashMap<String, ContactItemBean> dataSourceMap = new HashMap<>();
        for (ContactItemBean itemBean : dataSource) {
            dataSourceMap.put(itemBean.getId(), itemBean);
        }

        boolean isrefresh = false;
        for (V2TIMUserStatus timUserStatus : userStatusList) {
            String userid = timUserStatus.getUserID();
            ContactItemBean bean = dataSourceMap.get(userid);
            if (bean != null && bean.getStatusType() != timUserStatus.getStatusType()) {
                isrefresh = true;
                bean.setStatusType(timUserStatus.getStatusType());
            }
        }

        if (isrefresh) {
            notifyDataSourceChanged();
        }
    }

    private void onDataListAdd(List<ContactItemBean> users) {
        List<ContactItemBean> addUserList = new ArrayList<>(users);
        Iterator<ContactItemBean> beanIterator = addUserList.iterator();
        while (beanIterator.hasNext()) {
            ContactItemBean contactItemBean = beanIterator.next();
            for (ContactItemBean dataItemBean : dataSource) {
                if (TextUtils.equals(contactItemBean.getId(), dataItemBean.getId())) {
                    beanIterator.remove();
                }
            }
        }
        dataSource.addAll(addUserList);
        notifyDataSourceChanged();

        loadContactUserStatus(addUserList);
    }

    public void createGroupChat(GroupInfo groupInfo, IUIKitCallback<String> callback) {
        provider.createGroupChat(groupInfo, new IUIKitCallback<String>() {
            @Override
            public void onSuccess(String groupId) {
                groupInfo.setId(groupId);
                MessageCustom messageCustom = new MessageCustom();
                messageCustom.version = TUIContactConstants.version;
                messageCustom.businessID = MessageCustom.BUSINESS_ID_GROUP_CREATE;
                messageCustom.opUser = TUILogin.getLoginUser();
                messageCustom.content = TUIContactService.getAppContext().getString(R.string.create_group);
                messageCustom.cmd = TextUtils.equals(groupInfo.getGroupType(), TUIContactConstants.GroupType.TYPE_COMMUNITY) ? 1 : 0;
                Gson gson = new Gson();
                String data = gson.toJson(messageCustom);

                ThreadUtils.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sendGroupTipsMessage(groupId, data, new IUIKitCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                ThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ContactUtils.callbackOnSuccess(callback, result);
                                    }
                                });
                            }

                            @Override
                            public void onError(String module, int errCode, String errMsg) {
                                ThreadUtils.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ContactUtils.callbackOnError(callback, module, errCode, errMsg);
                                    }
                                });
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ContactUtils.callbackOnError(callback, module, errCode, errMsg);
            }
        });
    }

    public void onFriendInfoChanged(List<ContactItemBean> infoList) {
        for (ContactItemBean changedItem : infoList) {
            for (int i = 0; i < dataSource.size(); i++) {
                if (TextUtils.equals(dataSource.get(i).getId(), changedItem.getId())) {
                    if (changedItem.getStatusType() == V2TIMUserStatus.V2TIM_USER_STATUS_UNKNOWN) {
                        changedItem.setStatusType(dataSource.get(i).getStatusType());
                    }
                    dataSource.set(i, changedItem);
                    break;
                }
            }
        }
        notifyDataSourceChanged();
    }

    public void onFriendRemarkChanged(String id, String remark) {
        loadDataSource(IContactListView.DataSource.CONTACT_LIST);
    }

    public void getFriendApplicationUnReadCount() {
        provider.getFriendApplicationListUnreadCount(new IUIKitCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                if (newContacts.getUnreadCount() != data) {
                    newContacts.setUnreadCount(data);
                    notifyDataChanged(newContacts);
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                TUIContactLog.e(TAG, "getFriendApplicationUnreadCount failed, errCode = " + errCode + ",des = " + errMsg);
            }
        });
    }

    public void sendGroupTipsMessage(String groupId, String messageData, IUIKitCallback<String> callback) {
        provider.sendGroupTipsMessage(groupId, messageData, callback);
    }

    private void notifyDataChanged(ContactItemBean contactItemBean) {
        if (contactListView != null) {
            contactListView.onDataChanged(contactItemBean);
        }
    }
}
