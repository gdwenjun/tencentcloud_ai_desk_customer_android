package com.tencent.qcloud.tuikit.deskcontact.presenter;

import android.text.TextUtils;

import com.tencent.qcloud.deskcore.interfaces.TUIValueCallback;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuikit.deskcontact.bean.ContactItemBean;
import com.tencent.qcloud.tuikit.deskcontact.bean.GroupInfo;
import com.tencent.qcloud.tuikit.deskcontact.interfaces.IAddMoreActivity;
import com.tencent.qcloud.tuikit.deskcontact.model.ContactProvider;
import com.tencent.qcloud.tuikit.deskcontact.util.ContactUtils;
import java.util.ArrayList;
import java.util.List;

public class AddMorePresenter {
    private static final String TAG = AddMorePresenter.class.getSimpleName();

    private ContactProvider provider;

    private IAddMoreActivity addMoreActivity;

    public AddMorePresenter() {
        provider = new ContactProvider();
    }

    public void setAddMoreActivity(IAddMoreActivity addMoreActivity) {
        this.addMoreActivity = addMoreActivity;
    }

    public void getUserInfo(String userId, TUIValueCallback<ContactItemBean> callback) {
        List<String> userIdList = new ArrayList<>();
        userIdList.add(userId);
        provider.getUserInfo(userIdList, new TUIValueCallback<List<ContactItemBean>>() {
            @Override
            public void onSuccess(List<ContactItemBean> data) {
                ContactItemBean itemBean = data.get(0);
                provider.isFriend(userId, itemBean, new TUIValueCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        TUIValueCallback.onSuccess(callback, itemBean);
                    }

                    @Override
                    public void onError(int errCode, String errMsg) {
                        TUIValueCallback.onSuccess(callback, itemBean);
                    }
                });
                TUIValueCallback.onSuccess(callback, itemBean);
            }

            @Override
            public void onError(int errCode, String errMsg) {
                TUIValueCallback.onError(callback, errCode, errMsg);
            }
        });
    }

    public void getGroupInfo(String groupId, IUIKitCallback<GroupInfo> callback) {
        if (TextUtils.isEmpty(groupId)) {
            return;
        }
        List<String> groupIds = new ArrayList<>();
        groupIds.add(groupId);
        provider.getGroupInfo(groupIds, new IUIKitCallback<List<GroupInfo>>() {
            @Override
            public void onSuccess(List<GroupInfo> data) {
                if (data == null || data.isEmpty()) {
                    return;
                }
                ContactUtils.callbackOnSuccess(callback, data.get(0));
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ContactUtils.callbackOnError(callback, errCode, errMsg);
            }
        });
    }
}
