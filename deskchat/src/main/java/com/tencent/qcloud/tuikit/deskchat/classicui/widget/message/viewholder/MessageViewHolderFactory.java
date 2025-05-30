package com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.tencent.qcloud.tuikit.deskcommon.classicui.widget.message.MessageBaseHolder;
import com.tencent.qcloud.tuikit.deskcommon.interfaces.ICommonMessageAdapter;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.classicui.ClassicUIService;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MessageViewHolderFactory {
    public static final int VIEW_TYPE_HEAD = -99;
    public static final int VIEW_TYPE_TAIL = -98;

    public static RecyclerView.ViewHolder getInstance(ViewGroup parent, ICommonMessageAdapter adapter, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder = null;
        View view = null;

        if (viewType == VIEW_TYPE_HEAD) {
            view = inflater.inflate(R.layout.desk_chat_loading_progress_bar, parent, false);
            return new MessageHeadHolder(view);
        }
        if (viewType == VIEW_TYPE_TAIL) {
            return new MessageTailHolder(new View(parent.getContext()));
        }

        if (ClassicUIService.getInstance().isNeedEmptyViewGroup(viewType)) {
            view = inflater.inflate(R.layout.desk_message_adapter_item_empty, parent, false);
            holder = getViewHolder(view, viewType);
        } else {
            view = inflater.inflate(com.tencent.qcloud.tuikit.deskcommon.R.layout.desk_message_adapter_item_content, parent, false);
            holder = getViewHolder(view, viewType);
        }

        if (holder == null) {
            holder = new TextMessageHolder(view);
        }
        if (holder instanceof MessageBaseHolder) {
            ((MessageBaseHolder) holder).setAdapter(adapter);
        }

        return holder;
    }

    private static RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        Class<? extends RecyclerView.ViewHolder> messageHolderClazz = ClassicUIService.getInstance().getMessageViewHolderClass(viewType);
        ;
        if (messageHolderClazz != null) {
            Constructor<? extends RecyclerView.ViewHolder> constructor;
            try {
                constructor = messageHolderClazz.getConstructor(View.class);
                return constructor.newInstance(view);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
