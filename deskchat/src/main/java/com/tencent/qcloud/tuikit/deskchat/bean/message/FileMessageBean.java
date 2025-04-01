package com.tencent.qcloud.tuikit.deskchat.bean.message;

import com.tencent.imsdk.v2.V2TIMFileElem;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.R;
import com.tencent.qcloud.tuikit.deskchat.TUIChatService;
import com.tencent.qcloud.tuikit.deskchat.bean.message.reply.FileReplyQuoteBean;

public class FileMessageBean extends TUIMessageBean {
    private V2TIMFileElem fileElem;
    private boolean isDownloading = false;

    @Override
    public String onGetDisplayString() {
        return getExtra();
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        if (getStatus() == TUIMessageBean.MSG_STATUS_REVOKE) {
            return;
        }
        fileElem = v2TIMMessage.getFileElem();
        setExtra(TUIChatService.getAppContext().getString(R.string.file_extra));
    }

    public void setDownloading(boolean downloading) {
        isDownloading = downloading;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public String getFileName() {
        if (fileElem != null) {
            return fileElem.getFileName();
        }
        return "";
    }

    public int getFileSize() {
        if (fileElem != null) {
            return fileElem.getFileSize();
        }
        return 0;
    }

    @Override
    public Class<? extends TUIReplyQuoteBean> getReplyQuoteBeanClass() {
        return FileReplyQuoteBean.class;
    }
}
