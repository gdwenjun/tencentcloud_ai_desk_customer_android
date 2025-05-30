package com.tencent.qcloud.tuikit.deskcontact.minimalistui.widget;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.tencent.imsdk.v2.V2TIMUserStatus;
import com.tencent.qcloud.deskcore.TUIThemeManager;
import com.tencent.qcloud.tuikit.deskcommon.component.gatherimage.ShadeImageView;
import com.tencent.qcloud.tuikit.deskcommon.component.impl.GlideEngine;
import com.tencent.qcloud.tuikit.deskcommon.util.ScreenUtil;
import com.tencent.qcloud.tuikit.deskcommon.util.TUIUtil;
import com.tencent.qcloud.tuikit.deskcontact.R;
import com.tencent.qcloud.tuikit.deskcontact.TUIContactService;
import com.tencent.qcloud.tuikit.deskcontact.bean.ContactItemBean;
import com.tencent.qcloud.tuikit.deskcontact.config.minimalistui.TUIContactConfigMinimalist;
import com.tencent.qcloud.tuikit.deskcontact.presenter.ContactPresenter;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<ContactItemBean> mData;
    private ContactListView.OnSelectChangedListener mOnSelectChangedListener;
    private ContactListView.OnItemClickListener mOnClickListener;

    private boolean isSingleSelectMode;
    private ContactPresenter presenter;
    private boolean isGroupList = false;
    private int dataSourceType = ContactListView.DataSource.UNKNOWN;
    private ArrayList<String> alreadySelectedList;

    public ContactAdapter(List<ContactItemBean> data) {
        this.mData = data;
    }

    public void setPresenter(ContactPresenter presenter) {
        this.presenter = presenter;
    }

    public void setIsGroupList(boolean groupList) {
        isGroupList = groupList;
    }

    public void setAlreadySelectedList(ArrayList<String> alreadySelectedList) {
        this.alreadySelectedList = alreadySelectedList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ContactItemBean.ITEM_BEAN_TYPE_CONTACT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_contact_minimalist_selecable_adapter_item, parent, false);
            return new ContactItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_contact_minimalist_controller_item_layout, parent, false);
            return new ContactControllerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        final ContactItemBean contactBean = mData.get(position);
        if (viewHolder instanceof ContactItemViewHolder) {
            ContactItemViewHolder itemViewHolder = (ContactItemViewHolder) viewHolder;
            itemViewHolder.tvName.setText(contactBean.getDisplayName());
            if (!isSingleSelectMode && mOnSelectChangedListener != null) {
                itemViewHolder.ccSelect.setVisibility(View.VISIBLE);
                itemViewHolder.ccSelect.setChecked(contactBean.isSelected());
            }

            itemViewHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!contactBean.isEnable()) {
                        return;
                    }

                    itemViewHolder.ccSelect.setChecked(!itemViewHolder.ccSelect.isChecked());
                    if (mOnSelectChangedListener != null) {
                        mOnSelectChangedListener.onSelectChanged(getItem(position), itemViewHolder.ccSelect.isChecked());
                    }
                    contactBean.setSelected(itemViewHolder.ccSelect.isChecked());
                    if (mOnClickListener != null) {
                        mOnClickListener.onItemClick(position, contactBean);
                    }
                }
            });
            itemViewHolder.unreadText.setVisibility(View.GONE);
            itemViewHolder.userStatusView.setVisibility(View.GONE);
            int radius = ScreenUtil.dip2px(20);
            if (TUIContactConfigMinimalist.getContactAvatarRadius() != TUIContactConfigMinimalist.UNDEFINED) {
                radius = TUIContactConfigMinimalist.getContactAvatarRadius();
            }
            itemViewHolder.avatar.setRadius(radius);
            if (isGroupList) {
                int defaultIconResId = TUIUtil.getDefaultGroupIconResIDByGroupType(itemViewHolder.itemView.getContext(), contactBean.getGroupType());
                GlideEngine.loadUserIcon(itemViewHolder.avatar, contactBean.getAvatarUrl(), defaultIconResId, radius);
            } else {
                GlideEngine.loadUserIcon(itemViewHolder.avatar, contactBean.getAvatarUrl(), radius);
            }
            if (dataSourceType == ContactListView.DataSource.CONTACT_LIST && TUIContactConfigMinimalist.isShowUserOnlineStatusIcon()) {
                itemViewHolder.userStatusView.setVisibility(View.VISIBLE);
                if (contactBean.getStatusType() == V2TIMUserStatus.V2TIM_USER_STATUS_ONLINE) {
                    itemViewHolder.userStatusView.setBackgroundResource(
                        TUIThemeManager.getAttrResId(TUIContactService.getAppContext(), com.tencent.qcloud.tuikit.deskcommon.R.attr.user_status_online));
                } else {
                    itemViewHolder.userStatusView.setBackgroundResource(
                        TUIThemeManager.getAttrResId(TUIContactService.getAppContext(), com.tencent.qcloud.tuikit.deskcommon.R.attr.user_status_offline));
                }
            } else {
                itemViewHolder.userStatusView.setVisibility(View.GONE);
            }
            setAlreadySelected(itemViewHolder, contactBean);
        } else {
            ContactControllerViewHolder controllerViewHolder = (ContactControllerViewHolder) viewHolder;
            String newFriendString = TUIContactService.getAppContext().getResources().getString(R.string.new_friend);
            String myGroupString = TUIContactService.getAppContext().getResources().getString(R.string.group);
            String blokeListString = TUIContactService.getAppContext().getResources().getString(R.string.blacklist);
            if (presenter.newContacts == contactBean) {
                controllerViewHolder.controllerName.setText(newFriendString);
                controllerViewHolder.unreadText.setText(contactBean.getUnreadCount() + "");
                if (contactBean.getUnreadCount() > 0) {
                    controllerViewHolder.unreadText.setVisibility(View.VISIBLE);
                } else {
                    controllerViewHolder.unreadText.setVisibility(View.GONE);
                }
            } else if (TextUtils.equals(myGroupString, contactBean.getId())) {
                controllerViewHolder.controllerName.setText(myGroupString);
                controllerViewHolder.unreadText.setVisibility(View.GONE);
            } else if (TextUtils.equals(blokeListString, contactBean.getId())) {
                controllerViewHolder.controllerName.setText(blokeListString);
                controllerViewHolder.unreadText.setVisibility(View.GONE);
            }
            controllerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null) {
                        mOnClickListener.onItemClick(position, contactBean);
                    }
                }
            });
        }
    }

    public void setSelected(String userID, boolean isSelected) {
        ContactItemBean contact = null;
        for (ContactItemBean item : mData) {
            if (TextUtils.equals(item.getId(), userID)) {
                contact = item;
                break;
            }
        }
        if (contact == null) {
            return;
        }
        contact.setSelected(isSelected);
        if (mOnSelectChangedListener != null) {
            mOnSelectChangedListener.onSelectChanged(contact, isSelected);
        }
        notifyDataSetChanged();
    }

    private void setAlreadySelected(ContactItemViewHolder holder, ContactItemBean contactItemBean) {
        if (alreadySelectedList != null && alreadySelectedList.contains(contactItemBean.getId())) {
            holder.ccSelect.setChecked(true);
            holder.itemView.setEnabled(false);
            holder.ccSelect.setEnabled(false);
        } else {
            holder.itemView.setEnabled(true);
            holder.ccSelect.setEnabled(true);
            holder.ccSelect.setSelected(contactItemBean.isSelected());
        }
    }

    @Override
    public int getItemViewType(int position) {
        final ContactItemBean contactBean = mData.get(position);
        return contactBean.getItemBeanType();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof ContactItemViewHolder) {
            GlideEngine.clear(((ContactItemViewHolder) holder).avatar);
            ((ContactItemViewHolder) holder).avatar.setImageResource(0);
        }
        super.onViewRecycled(holder);
    }

    private ContactItemBean getItem(int position) {
        if (position < mData.size()) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void setDataSource(List<ContactItemBean> datas) {
        this.mData = datas;
        notifyDataSetChanged();
    }

    public void setSingleSelectMode(boolean mode) {
        isSingleSelectMode = mode;
    }

    public void setOnSelectChangedListener(ContactListView.OnSelectChangedListener selectListener) {
        mOnSelectChangedListener = selectListener;
    }

    public void setOnItemClickListener(ContactListView.OnItemClickListener listener) {
        mOnClickListener = listener;
    }

    public void setDataSourceType(int dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public void onDataChanged(ContactItemBean data) {
        int index = mData.indexOf(data);
        if (index != -1) {
            notifyItemChanged(index);
        }
    }

    public static class ContactItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView unreadText;
        ShadeImageView avatar;
        CheckBox ccSelect;
        View content;
        View userStatusView;

        public ContactItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCity);
            unreadText = itemView.findViewById(R.id.conversation_unread);
            unreadText.setVisibility(View.GONE);
            avatar = itemView.findViewById(R.id.ivAvatar);
            ccSelect = itemView.findViewById(R.id.contact_check_box);
            content = itemView.findViewById(R.id.selectable_contact_item);
            userStatusView = itemView.findViewById(R.id.user_status);

            avatar.setRadius(ScreenUtil.dip2px(20));
        }
    }

    public static class ContactControllerViewHolder extends RecyclerView.ViewHolder {
        TextView controllerName;
        TextView unreadText;
        ImageView arrow;

        public ContactControllerViewHolder(View itemView) {
            super(itemView);
            controllerName = itemView.findViewById(R.id.controller_name);
            unreadText = itemView.findViewById(R.id.unread_count);
            arrow = itemView.findViewById(R.id.arrow_icon);
            Drawable arrowDrawable = arrow.getBackground();
            if (arrowDrawable != null) {
                arrowDrawable.setAutoMirrored(true);
            }
            unreadText.setVisibility(View.GONE);
        }
    }
}
