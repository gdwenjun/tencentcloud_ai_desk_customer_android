package com.tencent.qcloud.tuikit.deskcontact.util;

import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.qcloud.deskcore.util.ErrorMessageConverter;
import com.tencent.qcloud.tuikit.deskcommon.component.interfaces.IUIKitCallback;

public class ContactUtils {
    public static <T> void callbackOnError(IUIKitCallback<T> callBack, String module, int errCode, String desc) {
        if (callBack != null) {
            callBack.onError(module, errCode, ErrorMessageConverter.convertIMError(errCode, desc));
        }
    }

    public static <T> void callbackOnError(IUIKitCallback<T> callBack, int errCode, String desc) {
        if (callBack != null) {
            callBack.onError(null, errCode, ErrorMessageConverter.convertIMError(errCode, desc));
        }
    }

    public static <T> void callbackOnSuccess(IUIKitCallback<T> callBack, T data) {
        if (callBack != null) {
            callBack.onSuccess(data);
        }
    }

    public static String getLoginUser() {
        return V2TIMManager.getInstance().getLoginUser();
    }
}
