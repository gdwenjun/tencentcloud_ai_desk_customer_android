package com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.reply;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tencent.qcloud.deskcore.TUIConstants;
import com.tencent.qcloud.deskcore.TUICore;
import com.tencent.qcloud.tuikit.deskcommon.bean.MessageRepliesBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.component.face.FaceManager;
import com.tencent.qcloud.tuikit.deskcommon.component.gatherimage.UserIconView;
import com.tencent.qcloud.tuikit.deskcommon.util.DateTimeUtil;
import com.tencent.qcloud.tuikit.deskchat.R;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplyDetailsView extends RecyclerView {
    private ReplyDetailsAdapter adapter;
    private LinearLayoutManager layoutManager;

    public ReplyDetailsView(@NonNull Context context) {
        super(context);
        initView();
    }

    public ReplyDetailsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ReplyDetailsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(layoutManager);
        adapter = new ReplyDetailsAdapter();
        setAdapter(adapter);
    }

    public void setData(Map<MessageRepliesBean.ReplyBean, TUIMessageBean> messageBeanMap) {
        adapter.setData(messageBeanMap);
        adapter.notifyDataSetChanged();
    }

    public class ReplyDetailsAdapter extends Adapter<ReplyDetailsViewHolder> {
        Map<MessageRepliesBean.ReplyBean, TUIMessageBean> data;

        public void setData(Map<MessageRepliesBean.ReplyBean, TUIMessageBean> messageBeanMap) {
            this.data = messageBeanMap;
        }

        @NonNull
        @Override
        public ReplyDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_chat_reply_details_item_layout, parent, false);
            return new ReplyDetailsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReplyDetailsViewHolder holder, int position) {
            MessageRepliesBean.ReplyBean replyBean = new ArrayList<>(data.keySet()).get(position);
            TUIMessageBean messageBean = data.get(replyBean);
            String userName;
            String messageText;
            List<Object> iconList = new ArrayList<>();
            if (messageBean == null) {
                userName = replyBean.getSenderShowName();
                messageText = replyBean.getMessageAbstract();
                iconList.add(replyBean.getSenderFaceUrl());
            } else {
                messageText = messageBean.getExtra();
                userName = messageBean.getUserDisplayName();
                iconList.add(messageBean.getFaceUrl());
                holder.timeText.setText(DateTimeUtil.getTimeFormatText(new Date(messageBean.getMessageTime() * 1000)));
            }
            holder.userFaceView.setIconUrls(iconList);
            holder.userNameTv.setText(userName);
            FaceManager.handlerEmojiText(holder.messageText, messageText, false);

            setBottomContent(messageBean, holder);
        }

        @Override
        public int getItemCount() {
            if (data == null) {
                return 0;
            }
            return data.size();
        }
    }

    private void setBottomContent(TUIMessageBean msg, ReplyDetailsViewHolder holder) {
        HashMap<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUIChat.MESSAGE_BEAN, msg);
        param.put(TUIConstants.TUIChat.CHAT_RECYCLER_VIEW, ReplyDetailsView.this);

        TUICore.raiseExtension(TUIConstants.TUIChat.Extension.MessageBottom.CLASSIC_EXTENSION_ID, holder.bottomContentFrameLayout, param);
    }

    static class ReplyDetailsViewHolder extends ViewHolder {
        public UserIconView userFaceView;
        protected TextView userNameTv;
        protected TextView messageText;
        protected TextView timeText;
        protected View bottomContentFrameLayout;

        public ReplyDetailsViewHolder(View itemView) {
            super(itemView);
            userFaceView = itemView.findViewById(R.id.user_icon);
            userNameTv = itemView.findViewById(R.id.user_name_tv);
            messageText = itemView.findViewById(R.id.msg_abstract);
            timeText = itemView.findViewById(R.id.msg_time);
            bottomContentFrameLayout = itemView.findViewById(R.id.bottom_content_fl);
        }
    }
}
