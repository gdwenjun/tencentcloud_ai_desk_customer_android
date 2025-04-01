package com.tencent.qcloud.tuikit.deskchat.minimalistui.interfaces;

import com.tencent.qcloud.tuikit.deskcommon.interfaces.OnItemClickListener;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.message.MessageAdapter;
import com.tencent.qcloud.tuikit.deskchat.minimalistui.widget.messagepopmenu.ChatPopActivity;
import java.util.List;

/**

 *
 * The message area {@link com.tencent.qcloud.tuikit.deskchat.classicui.widget.message.MessageRecyclerView} inherits from {@link RecyclerView} and provides the
 * display function of the message. This class provides a large number of methods for customization requirements, including appearance settings, event clicks,
 * and display of custom messages.
 */
public interface IMessageLayout {

    void setAdapter(MessageAdapter adapter);

    OnItemClickListener getOnItemClickListener();

    void setOnItemClickListener(OnItemClickListener listener);

    List<ChatPopActivity.ChatPopMenuAction> getPopActions();

    void addPopAction(ChatPopActivity.ChatPopMenuAction action);
}
