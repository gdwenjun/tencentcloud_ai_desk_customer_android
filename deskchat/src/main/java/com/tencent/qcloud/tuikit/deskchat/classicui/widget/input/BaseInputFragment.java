package com.tencent.qcloud.tuikit.deskchat.classicui.widget.input;

import androidx.fragment.app.Fragment;

import com.tencent.qcloud.tuikit.deskchat.classicui.interfaces.IChatLayout;

public class BaseInputFragment extends Fragment {
    private IChatLayout mChatLayout;

    public IChatLayout getChatLayout() {
        return mChatLayout;
    }

    public BaseInputFragment setChatLayout(IChatLayout layout) {
        mChatLayout = layout;
        return this;
    }
}
