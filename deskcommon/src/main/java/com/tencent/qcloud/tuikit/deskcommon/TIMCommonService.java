package com.tencent.qcloud.tuikit.deskcommon;

import android.content.Context;

import com.google.auto.service.AutoService;
import com.tencent.qcloud.deskcore.ServiceInitializer;
import com.tencent.qcloud.deskcore.TUIThemeManager;
import com.tencent.qcloud.deskcore.annotations.TUIInitializerID;
import com.tencent.qcloud.deskcore.interfaces.TUIInitializer;
import com.tencent.qcloud.tuikit.deskcommon.R;

@AutoService(TUIInitializer.class)
@TUIInitializerID("TIMCommon")
public class TIMCommonService implements TUIInitializer {

    @Override
    public void init(Context context) {
        TUIThemeManager.addLightTheme(R.style.TIMCommonLightTheme);
        TUIThemeManager.addLivelyTheme(R.style.TIMCommonLivelyTheme);
        TUIThemeManager.addSeriousTheme(R.style.TIMCommonSeriousTheme);
    }

    public static Context getAppContext() {
        return ServiceInitializer.getAppContext();
    }
}
