package com.tencent.qcloud.tuikit.deskcontact.classicui.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.deskcore.TUILogin;
import com.tencent.qcloud.deskcore.interfaces.TUIExtensionInfo;
import com.tencent.qcloud.deskcore.util.ToastUtil;
import com.tencent.qcloud.tuikit.deskcommon.component.LineControllerView;
import com.tencent.qcloud.tuikit.deskcommon.component.PopupInputCard;
import com.tencent.qcloud.tuikit.deskcommon.component.TitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.dialog.TUIKitDialog;
import com.tencent.qcloud.tuikit.deskcommon.component.impl.GlideEngine;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.ITitleBarLayout;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuikit.deskcommon.util.TUIUtil;
import com.tencent.qcloud.tuikit.deskcontact.R;
import com.tencent.qcloud.tuikit.deskcontact.bean.ContactGroupApplyInfo;
import com.tencent.qcloud.tuikit.deskcontact.bean.ContactItemBean;
import com.tencent.qcloud.tuikit.deskcontact.bean.FriendApplicationBean;
import com.tencent.qcloud.tuikit.deskcontact.bean.GroupInfo;
import com.tencent.qcloud.tuikit.deskcontact.config.classicui.TUIContactConfigClassic;
import com.tencent.qcloud.tuikit.deskcontact.interfaces.IFriendProfileLayout;
import com.tencent.qcloud.tuikit.deskcontact.presenter.FriendProfilePresenter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendProfileLayout extends LinearLayout implements View.OnClickListener, IFriendProfileLayout {
    private static final String TAG = FriendProfileLayout.class.getSimpleName();

    private TitleBarLayout mTitleBar;
    private ImageView mHeadImageView;
    private TextView mNickNameView;
    private TextView mIDView;
    private TextView mSignatureView;
    private TextView mSignatureTagView;
    private TextView remarkAndGroupTip;
    private LineControllerView mRemarkView;
    private LineControllerView mAddBlackView;
    private LineControllerView mChatTopView;
    private LineControllerView mMessageOptionView;
    private LineControllerView addFriendRemarkLv;
    private LineControllerView mChatBackground;
    private TextView deleteFriendBtn;
    private TextView clearMessageBtn;

    private TextView agreeBtn;

    private ViewGroup extensionListView;
    private ViewGroup warningExtensionListView;

    private TextView addFriendSendBtn;
    private TextView acceptFriendBtn;
    private TextView refuseFriendBtn;
    private View addFriendArea;
    private EditText addWordingEditText;
    private View friendApplicationVerifyArea;
    private TextView friendApplicationAddWording;

    private ContactItemBean mContactInfo;
    private FriendApplicationBean friendApplicationBean;
    private OnButtonClickListener mListener;
    private String mId;
    private String mNickname;
    private boolean isFriend;
    private boolean isGroup = false;

    private FriendProfilePresenter presenter;

    public FriendProfileLayout(Context context) {
        super(context);
        init();
    }

    public FriendProfileLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FriendProfileLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPresenter(FriendProfilePresenter presenter) {
        this.presenter = presenter;
    }

    private void init() {
        inflate(getContext(), R.layout.desk_contact_friend_profile_layout, this);

        mHeadImageView = findViewById(R.id.friend_icon);
        mNickNameView = findViewById(R.id.friend_nick_name);
        mIDView = findViewById(R.id.friend_account);
        mRemarkView = findViewById(R.id.remark);
        mRemarkView.setOnClickListener(this);
        mSignatureTagView = findViewById(R.id.friend_signature_tag);
        mSignatureView = findViewById(R.id.friend_signature);
        mMessageOptionView = findViewById(R.id.msg_rev_opt);
        mMessageOptionView.setOnClickListener(this);
        mChatTopView = findViewById(R.id.chat_to_top);
        mAddBlackView = findViewById(R.id.blackList);
        deleteFriendBtn = findViewById(R.id.btn_delete);
        deleteFriendBtn.setOnClickListener(this);
        clearMessageBtn = findViewById(R.id.btn_clear_chat_history);
        clearMessageBtn.setOnClickListener(this);
        mChatBackground = findViewById(R.id.chat_background);
        mChatBackground.setOnClickListener(this);

        addFriendSendBtn = findViewById(R.id.add_friend_send_btn);
        addFriendSendBtn.setOnClickListener(this);

        acceptFriendBtn = findViewById(R.id.accept_friend_send_btn);
        refuseFriendBtn = findViewById(R.id.refuse_friend_send_btn);

        agreeBtn = findViewById(R.id.agree_button);

        extensionListView = findViewById(R.id.extension_list);
        warningExtensionListView = findViewById(R.id.warning_extension_list);

        addFriendArea = findViewById(R.id.add_friend_verify_area);
        addWordingEditText = findViewById(R.id.add_wording_edit);

        friendApplicationVerifyArea = findViewById(R.id.friend_application_verify_area);
        friendApplicationAddWording = findViewById(R.id.friend_application_add_wording);

        addFriendRemarkLv = findViewById(R.id.friend_remark_lv);
        addFriendRemarkLv.setOnClickListener(this);

        remarkAndGroupTip = findViewById(R.id.remark_and_group_tip);

        mTitleBar = findViewById(R.id.friend_titlebar);
        mTitleBar.setTitle(getResources().getString(R.string.profile_detail), ITitleBarLayout.Position.MIDDLE);
        mTitleBar.getRightGroup().setVisibility(View.GONE);
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        });
    }

    private void setupExtension() {
        HashMap<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUIContact.Extension.FriendProfileItem.USER_ID, mId);
        List<TUIExtensionInfo> extensionInfoList = TUICore.getExtensionList(TUIConstants.TUIContact.Extension.FriendProfileItem.CLASSIC_EXTENSION_ID, param);
        Collections.sort(extensionInfoList);
        extensionListView.removeAllViews();
        for (TUIExtensionInfo extensionInfo : extensionInfoList) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.desk_contact_friend_profile_item_layout, null);
            TextView itemButton = itemView.findViewById(R.id.item_button);
            itemButton.setText(extensionInfo.getText());
            itemButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (extensionInfo.getExtensionListener() != null) {
                        extensionInfo.getExtensionListener().onClicked(null);
                    }
                }
            });
            extensionListView.addView(itemView);
        }

        List<TUIExtensionInfo> warningExtensionList = TUICore.getExtensionList(TUIConstants.TUIContact.Extension.FriendProfileWarningButton.EXTENSION_ID, null);
        Collections.sort(warningExtensionList);
        warningExtensionListView.removeAllViews();
        for (TUIExtensionInfo extensionInfo : warningExtensionList) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.desk_contact_friend_profile_warning_item_layout, null);
            TextView itemButton = itemView.findViewById(R.id.item_button);
            itemButton.setText(extensionInfo.getText());
            itemButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (extensionInfo.getExtensionListener() != null) {
                        extensionInfo.getExtensionListener().onClicked(null);
                    }
                }
            });
            warningExtensionListView.addView(itemView);
        }
    }

    private void initEvent() {
        mChatTopView.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                if (presenter != null) {
                    presenter.setConversationTop(mId, isChecked);
                }
            }
        });

        mAddBlackView.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addBlack();
                } else {
                    deleteBlack();
                }
            }
        });

        refuseFriendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refuse();
            }
        });

        acceptFriendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                accept();
            }
        });
    }

    public void initData(Object data) {
        initEvent();

        if (data instanceof String) {
            mId = (String) data;
            loadUserProfile(mId);
        } else if (data instanceof ContactItemBean) {
            setViewContentFromItemBean((ContactItemBean) data);
        } else if (data instanceof FriendApplicationBean) {
            setViewContentFromFriendApplicationBean((FriendApplicationBean) data);
        } else if (data instanceof ContactGroupApplyInfo) {
            setViewContentFromContactGroupApplyInfo((ContactGroupApplyInfo) data);
        } else if (data instanceof GroupInfo) {
            setViewContentFromGroupInfo((GroupInfo) data);
        }

        if (!TextUtils.isEmpty(mNickname)) {
            mNickNameView.setText(mNickname);
        } else {
            mNickNameView.setText(mId);
        }
        mIDView.setText(mId);

        setupExtension();
    }

    private void setViewContentFromContactGroupApplyInfo(ContactGroupApplyInfo data) {
        final ContactGroupApplyInfo info = data;
        mId = info.getFromUser();
        mNickname = info.getFromUserNickName();
        mRemarkView.setVisibility(GONE);
        mAddBlackView.setVisibility(GONE);
        mMessageOptionView.setVisibility(GONE);
        mChatBackground.setVisibility(GONE);
        deleteFriendBtn.setText(R.string.refuse);
        deleteFriendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refuseJoinGroupApply(info);
            }
        });
        agreeBtn.setVisibility(VISIBLE);
        agreeBtn.setText(R.string.accept);
        agreeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptJoinGroupApply(info);
            }
        });
    }

    private void setViewContentFromGroupInfo(GroupInfo groupInfo) {
        mTitleBar.setTitle(getResources().getString(R.string.add_group), ITitleBarLayout.Position.MIDDLE);
        isGroup = true;
        mId = groupInfo.getId();
        mNickname = groupInfo.getGroupName();
        mSignatureTagView.setText(getResources().getString(R.string.contact_group_type_tag));
        mSignatureView.setText(groupInfo.getGroupType());
        int radius = getResources().getDimensionPixelSize(R.dimen.contact_profile_face_radius);
        GlideEngine.loadUserIcon(
            mHeadImageView, groupInfo.getFaceUrl(), TUIUtil.getDefaultGroupIconResIDByGroupType(getContext(), groupInfo.getGroupType()), radius);
        addFriendSendBtn.setVisibility(VISIBLE);
        addFriendArea.setVisibility(VISIBLE);
        remarkAndGroupTip.setVisibility(GONE);
        addFriendRemarkLv.setVisibility(GONE);
    }

    private void setViewContentFromFriendApplicationBean(FriendApplicationBean data) {
        friendApplicationBean = data;
        mId = friendApplicationBean.getUserId();
        mNickname = friendApplicationBean.getNickName();
        mSignatureTagView.setVisibility(GONE);
        mSignatureView.setVisibility(GONE);
        mRemarkView.setVisibility(GONE);
        mAddBlackView.setVisibility(GONE);
        mMessageOptionView.setVisibility(GONE);
        mChatBackground.setVisibility(GONE);
        friendApplicationVerifyArea.setVisibility(VISIBLE);
        friendApplicationAddWording.setText(friendApplicationBean.getAddWording());

        refuseFriendBtn.setVisibility(VISIBLE);
        acceptFriendBtn.setVisibility(VISIBLE);

        int radius = getResources().getDimensionPixelSize(R.dimen.contact_profile_face_radius);
        GlideEngine.loadUserIcon(mHeadImageView, friendApplicationBean.getFaceUrl(), radius);
    }

    private void setViewContentFromItemBean(ContactItemBean data) {
        mContactInfo = data;
        isFriend = mContactInfo.isFriend();
        mId = mContactInfo.getId();
        mNickname = mContactInfo.getNickName();
        mSignatureView.setText(mContactInfo.getSignature());
        if (TextUtils.isEmpty(mContactInfo.getSignature())) {
            mSignatureTagView.setText(getResources().getString(R.string.contact_no_status));
        } else {
            mSignatureTagView.setText(getResources().getString(R.string.contact_signature_tag));
        }
        int radius = getResources().getDimensionPixelSize(R.dimen.contact_profile_face_radius);
        GlideEngine.loadUserIcon(mHeadImageView, mContactInfo.getAvatarUrl(), radius);
        mChatTopView.setChecked(presenter.isTopConversation(mId));
        mAddBlackView.setChecked(mContactInfo.isBlackList());
        mRemarkView.setContent(mContactInfo.getRemark());

        if (isFriend || mContactInfo.isBlackList()) {
            clearMessageBtn.setVisibility(VISIBLE);
        }

        if (TextUtils.equals(mContactInfo.getId(), TUILogin.getLoginUser())) {
            if (isFriend) {
                mRemarkView.setVisibility(VISIBLE);
                extensionListView.setVisibility(VISIBLE);
                deleteFriendBtn.setVisibility(VISIBLE);
                mAddBlackView.setVisibility(VISIBLE);
                mChatTopView.setVisibility(View.VISIBLE);
                mChatBackground.setVisibility(VISIBLE);
                updateMessageOptionView();
            } else {
                // DO NOTHING
            }
        } else {
            if (mContactInfo.isBlackList()) {
                deleteFriendBtn.setVisibility(GONE);
                extensionListView.setVisibility(VISIBLE);
                mRemarkView.setVisibility(VISIBLE);
                mAddBlackView.setVisibility(VISIBLE);
                mMessageOptionView.setVisibility(VISIBLE);
                mChatTopView.setVisibility(VISIBLE);
                mChatBackground.setVisibility(VISIBLE);
            } else {
                if (!isFriend) {
                    if (isGroup) {
                        addFriendRemarkLv.setVisibility(GONE);
                    } else {
                        addFriendRemarkLv.setVisibility(VISIBLE);
                    }
                    mTitleBar.setTitle(getResources().getString(R.string.add_friend), ITitleBarLayout.Position.MIDDLE);
                    addFriendSendBtn.setVisibility(VISIBLE);
                    addFriendArea.setVisibility(VISIBLE);
                } else {
                    mTitleBar.setTitle(getResources().getString(R.string.profile_detail), ITitleBarLayout.Position.MIDDLE);
                    mRemarkView.setVisibility(VISIBLE);
                    extensionListView.setVisibility(VISIBLE);
                    deleteFriendBtn.setVisibility(VISIBLE);
                    mAddBlackView.setVisibility(VISIBLE);
                    mChatTopView.setVisibility(View.VISIBLE);
                    mChatBackground.setVisibility(VISIBLE);
                    addFriendSendBtn.setVisibility(GONE);
                    addFriendArea.setVisibility(GONE);
                    updateMessageOptionView();
                }
            }
        }
    }

    private void updateMessageOptionView() {
        mMessageOptionView.setVisibility(VISIBLE);
        // get
        final ArrayList<String> userIdList = new ArrayList<>();
        userIdList.add(mId);

        presenter.getC2CReceiveMessageOpt(userIdList, new IUIKitCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                mMessageOptionView.setChecked(data);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                mMessageOptionView.setChecked(false);
            }
        });

        // set
        mMessageOptionView.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.setC2CReceiveMessageOpt(userIdList, isChecked);
            }
        });
    }

    @Override
    public void onDataSourceChanged(ContactItemBean bean) {
        setViewContentFromItemBean(bean);
        setupExtension();
        if (bean.isFriend()) {
            updateMessageOptionView();
            clearMessageBtn.setVisibility(VISIBLE);
        }

        if (!TextUtils.isEmpty(mNickname)) {
            mNickNameView.setText(mNickname);
        } else {
            mNickNameView.setText(mId);
        }

        if (!TextUtils.isEmpty(bean.getAvatarUrl())) {
            int radius = getResources().getDimensionPixelSize(R.dimen.contact_profile_face_radius);
            GlideEngine.loadUserIcon(mHeadImageView, bean.getAvatarUrl(), radius);
        }
        mIDView.setText(mId);

        applyCustomConfig();
    }

    public void applyCustomConfig() {
        if (!TUIContactConfigClassic.isShowAlias()) {
            mRemarkView.setVisibility(GONE);
        }
        if (!TUIContactConfigClassic.isShowMuteAndPin()) {
            mChatTopView.setVisibility(GONE);
            mMessageOptionView.setVisibility(GONE);
        }
        if (!TUIContactConfigClassic.isShowBackground()) {
            mChatBackground.setVisibility(GONE);
        }
        if (!TUIContactConfigClassic.isShowBlock()) {
            mAddBlackView.setVisibility(GONE);
        }
        if (!TUIContactConfigClassic.isShowClearChatHistory()) {
            clearMessageBtn.setVisibility(GONE);
        }
        if (!TUIContactConfigClassic.isShowDelete()) {
            deleteFriendBtn.setVisibility(GONE);
        }
        if (!TUIContactConfigClassic.isShowAddFriend()) {
            addFriendArea.setVisibility(GONE);
        }
    }

    private void loadUserProfile(String id) {
        final ContactItemBean bean = new ContactItemBean();
        presenter.getUsersInfo(id, bean);
    }

    private void accept() {
        presenter.acceptFriendApplication(friendApplicationBean, FriendApplicationBean.FRIEND_ACCEPT_AGREE_AND_ADD, new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                agreeBtn.setVisibility(VISIBLE);
                agreeBtn.setText(R.string.accepted);
                ((Activity) getContext()).finish();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtil.toastShortMessage("accept Error code = " + errCode + ", desc = " + errMsg);
            }
        });
    }

    private void refuse() {
        presenter.refuseFriendApplication(friendApplicationBean, new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                deleteFriendBtn.setText(R.string.refused);
                ((Activity) getContext()).finish();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtil.toastShortMessage("refuse Error code = " + errCode + ", desc = " + errMsg);
            }
        });
    }

    public void acceptJoinGroupApply(final ContactGroupApplyInfo item) {
        presenter.acceptJoinGroupApply(item, new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                ((Activity) getContext()).finish();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtil.toastLongMessage(errMsg);
            }
        });
    }

    public void refuseJoinGroupApply(final ContactGroupApplyInfo item) {
        presenter.refuseJoinGroupApply(item, "", new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                ((Activity) getContext()).finish();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtil.toastLongMessage(errMsg);
            }
        });
    }

    private void delete() {
        List<String> identifiers = new ArrayList<>();
        identifiers.add(mId);

        presenter.deleteFriend(identifiers, new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                if (mListener != null) {
                    mListener.onDeleteFriendClick(mId);
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtil.toastShortMessage("deleteFriend Error code = " + errCode + ", desc = " + errMsg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == deleteFriendBtn) {
            delete();
        } else if (v == clearMessageBtn) {
            new TUIKitDialog(getContext())
                .builder()
                .setCancelable(true)
                .setCancelOutside(true)
                .setTitle(getContext().getString(R.string.clear_msg_tip))
                .setDialogWidth(0.75f)
                .setPositiveButton(getContext().getString(com.tencent.qcloud.deskcore.R.string.sure),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String, Object> hashMap = new HashMap<>();
                            hashMap.put(TUIConstants.TUIContact.FRIEND_ID, mId);
                            TUICore.notifyEvent(TUIConstants.TUIContact.EVENT_USER, TUIConstants.TUIContact.EVENT_SUB_KEY_CLEAR_MESSAGE, hashMap);
                        }
                    })
                .setNegativeButton(getContext().getString(com.tencent.qcloud.deskcore.R.string.cancel),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {}
                    })
                .show();
        } else if (v == addFriendSendBtn) {
            String addWording = addWordingEditText.getText().toString();
            String friendGroup = "";
            String remark = addFriendRemarkLv.getContent();

            if (isGroup) {
                presenter.joinGroup(mId, addWording, new IUIKitCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        ToastUtil.toastShortMessage(getContext().getString(R.string.success));
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        ToastUtil.toastShortMessage(getContext().getString(R.string.contact_add_failed) + " " + errMsg);
                    }
                });
            } else {
                presenter.addFriend(mId, addWording, remark, new IUIKitCallback<Pair<Integer, String>>() {
                    @Override
                    public void onSuccess(Pair<Integer, String> data) {
                        ToastUtil.toastShortMessage(data.second);
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        ToastUtil.toastShortMessage(getContext().getString(R.string.contact_add_failed));
                    }
                });
            }
        } else if (v == addFriendRemarkLv) {
            PopupInputCard popupInputCard = new PopupInputCard((Activity) getContext());
            popupInputCard.setContent(addFriendRemarkLv.getContent());
            popupInputCard.setTitle(getResources().getString(R.string.contact_friend_remark));
            popupInputCard.setOnPositive((result -> {
                addFriendRemarkLv.setContent(result);
                if (TextUtils.isEmpty(result)) {
                    result = "";
                }
                modifyRemark(result);
            }));
            popupInputCard.show(addFriendRemarkLv, Gravity.BOTTOM);
        } else if (v == mRemarkView) {
            PopupInputCard popupInputCard = new PopupInputCard((Activity) getContext());
            popupInputCard.setContent(mRemarkView.getContent());
            popupInputCard.setTitle(getResources().getString(R.string.profile_remark_edit));
            String description = getResources().getString(R.string.contact_modify_remark_rule);
            popupInputCard.setDescription(description);
            popupInputCard.setOnPositive((result -> {
                mRemarkView.setContent(result);
                if (TextUtils.isEmpty(result)) {
                    result = "";
                }
                modifyRemark(result);
            }));
            popupInputCard.show(mRemarkView, Gravity.BOTTOM);

        } else if (v == mChatBackground) {
            if (mListener != null) {
                mListener.onSetChatBackground();
            }
        }
    }

    private void modifyRemark(final String txt) {
        presenter.modifyRemark(mId, txt, new IUIKitCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mContactInfo.setRemark(txt);
                Map<String, Object> param = new HashMap<>();
                param.put(TUIConstants.TUIContact.FRIEND_ID, mId);
                param.put(TUIConstants.TUIContact.FRIEND_REMARK, txt);
                TUICore.notifyEvent(TUIConstants.TUIContact.EVENT_FRIEND_INFO_CHANGED, TUIConstants.TUIContact.EVENT_SUB_KEY_FRIEND_REMARK_CHANGED, param);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {}
        });
    }

    private void addBlack() {
        String[] idStringList = mId.split(",");
        List<String> idList = new ArrayList<>(Arrays.asList(idStringList));
        presenter.addToBlackList(idList);
    }

    private void deleteBlack() {
        String[] idStringList = mId.split(",");
        List<String> idList = new ArrayList<>(Arrays.asList(idStringList));
        presenter.deleteFromBlackList(idList);
    }

    public void setOnButtonClickListener(OnButtonClickListener l) {
        mListener = l;
    }

    public interface OnButtonClickListener {

        void onDeleteFriendClick(String id);

        default void onSetChatBackground(){}
    }
}
