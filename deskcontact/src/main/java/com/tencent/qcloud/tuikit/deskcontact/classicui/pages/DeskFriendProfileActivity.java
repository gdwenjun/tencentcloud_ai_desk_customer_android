package com.tencent.qcloud.tuikit.deskcontact.classicui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.tencent.imsdk.v2.V2TIMGroupApplication;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.tuikit.deskcommon.component.activities.BaseLightActivity;
import com.tencent.qcloud.tuikit.deskcommon.component.activities.ImageSelectActivity;
import com.tencent.qcloud.tuikit.deskcommon.util.ScreenUtil;
import com.tencent.qcloud.tuikit.deskcontact.R;
import com.tencent.qcloud.tuikit.deskcontact.TUIContactConstants;
import com.tencent.qcloud.tuikit.deskcontact.TUIContactService;
import com.tencent.qcloud.tuikit.deskcontact.bean.ContactGroupApplyInfo;
import com.tencent.qcloud.tuikit.deskcontact.classicui.widget.FriendProfileLayout;
import com.tencent.qcloud.tuikit.deskcontact.presenter.FriendProfilePresenter;
import com.tencent.qcloud.tuikit.deskcontact.util.TUIContactLog;
import java.util.ArrayList;
import java.util.HashMap;

public class DeskFriendProfileActivity extends BaseLightActivity {
    private FriendProfilePresenter presenter;
    private FriendProfileLayout layout;
    private String userID;
    private String mChatBackgroundThumbnailUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.desk_contact_friend_profile_activity);
        layout = findViewById(R.id.friend_profile);

        presenter = new FriendProfilePresenter();
        layout.setPresenter(presenter);
        presenter.setFriendProfileLayout(layout);
        Intent intent = getIntent();
        userID = intent.getStringExtra(TUIConstants.TUIContact.USER_ID);
        if (TextUtils.isEmpty(userID)) {
            userID = intent.getStringExtra(TUIConstants.TUIChat.CHAT_ID);
        }
        mChatBackgroundThumbnailUrl = intent.getStringExtra(TUIConstants.TUIChat.CHAT_BACKGROUND_URI);
        String fromUser = intent.getStringExtra("fromUser");
        String fromUserNickName = intent.getStringExtra("fromUserNickName");
        String requestMsg = intent.getStringExtra("requestMsg");
        V2TIMGroupApplication application = (V2TIMGroupApplication) intent.getSerializableExtra("groupApplication");

        if (!TextUtils.isEmpty(userID)) {
            layout.initData(userID);
        } else if (!TextUtils.isEmpty(fromUser)) {
            ContactGroupApplyInfo contactGroupApplyInfo = new ContactGroupApplyInfo();
            contactGroupApplyInfo.setFromUser(fromUser);
            contactGroupApplyInfo.setFromUserNickName(fromUserNickName);
            contactGroupApplyInfo.setRequestMsg(requestMsg);
            contactGroupApplyInfo.setTimGroupApplication(application);
            layout.initData(contactGroupApplyInfo);
        } else {
            layout.initData(intent.getSerializableExtra(TUIContactConstants.ProfileType.CONTENT));
        }
        layout.setOnButtonClickListener(new FriendProfileLayout.OnButtonClickListener() {

            @Override
            public void onDeleteFriendClick(String id) {
                finish();
            }

            @Override
            public void onSetChatBackground() {
                ArrayList<ImageSelectActivity.ImageBean> faceList = new ArrayList<>();
                ImageSelectActivity.ImageBean defaultFace = new ImageSelectActivity.ImageBean();
                defaultFace.setDefault(true);
                faceList.add(defaultFace);
                for (int i = 0; i < TUIConstants.TUIChat.CHAT_CONVERSATION_BACKGROUND_COUNT; i++) {
                    ImageSelectActivity.ImageBean imageBean = new ImageSelectActivity.ImageBean();
                    imageBean.setImageUri(String.format(TUIConstants.TUIChat.CHAT_CONVERSATION_BACKGROUND_URL, (i + 1) + ""));
                    imageBean.setThumbnailUri(String.format(TUIConstants.TUIChat.CHAT_CONVERSATION_BACKGROUND_THUMBNAIL_URL, (i + 1) + ""));
                    faceList.add(imageBean);
                }

                Intent intent = new Intent(DeskFriendProfileActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.TITLE, getResources().getString(R.string.chat_background_title));
                intent.putExtra(ImageSelectActivity.SPAN_COUNT, 2);
                int itemWidth = (int) (ScreenUtil.getScreenWidth(TUIContactService.getAppContext()) * 0.42f);
                int itemHeight = (int) (itemWidth / 1.5f);
                intent.putExtra(ImageSelectActivity.ITEM_WIDTH, itemWidth);
                intent.putExtra(ImageSelectActivity.ITEM_HEIGHT, itemHeight);
                intent.putExtra(ImageSelectActivity.DATA, faceList);
                if (TextUtils.isEmpty(mChatBackgroundThumbnailUrl)
                    || TextUtils.equals(TUIConstants.TUIChat.CHAT_CONVERSATION_BACKGROUND_DEFAULT_URL, mChatBackgroundThumbnailUrl)) {
                    intent.putExtra(ImageSelectActivity.SELECTED, defaultFace);
                } else {
                    intent.putExtra(ImageSelectActivity.SELECTED, new ImageSelectActivity.ImageBean(mChatBackgroundThumbnailUrl, "", false));
                }
                intent.putExtra(ImageSelectActivity.NEED_DOWNLOAD_LOCAL, true);
                startActivityForResult(intent, TUIConstants.TUIChat.CHAT_REQUEST_BACKGROUND_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TUIConstants.TUIChat.CHAT_REQUEST_BACKGROUND_CODE) {
            if (data == null) {
                return;
            }
            ImageSelectActivity.ImageBean imageBean = (ImageSelectActivity.ImageBean) data.getSerializableExtra(ImageSelectActivity.DATA);
            if (imageBean == null) {
                TUIContactLog.e("FriendProfileActivity", "onActivityResult imageBean is null");
                return;
            }

            String backgroundUri = imageBean.getLocalPath();
            String thumbnailUri = imageBean.getThumbnailUri();
            TUIContactLog.d("FriendProfileActivity", "onActivityResult backgroundUri = " + backgroundUri);
            mChatBackgroundThumbnailUrl = thumbnailUri;
            HashMap<String, Object> param = new HashMap<>();
            param.put(TUIConstants.TUIChat.CHAT_ID, userID);
            String dataUri = thumbnailUri + "," + backgroundUri;
            param.put(TUIConstants.TUIChat.CHAT_BACKGROUND_URI, dataUri);
            TUICore.callService(TUIConstants.TUIChat.SERVICE_NAME, TUIConstants.TUIChat.METHOD_UPDATE_DATA_STORE_CHAT_URI, param);
        }
    }
}
