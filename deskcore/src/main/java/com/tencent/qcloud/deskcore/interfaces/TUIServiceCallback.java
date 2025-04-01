package com.tencent.qcloud.deskcore.interfaces;

import android.os.Bundle;

public abstract class TUIServiceCallback {
    public abstract void onServiceCallback(int errorCode, String errorMessage, Bundle bundle);
}
