package com.tencent.qcloud.tuikit.deskchat.classicui.widget.input.inputmore;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.bean.InputMoreItem;

import java.util.List;

public class InputMoreLayout extends LinearLayout {

    public InputMoreLayout(Context context) {
        super(context);
        init();
    }

    public InputMoreLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InputMoreLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.desk_chat_inputmore_layout, this);
    }

    public void init(List<InputMoreItem> actions) {
        final ViewPager2 viewPager = findViewById(R.id.viewPager);
        ActionsPagerAdapter adapter = new ActionsPagerAdapter(actions);
        viewPager.setAdapter(adapter);
    }
}
