package com.tencent.qcloud.tuikit.deskchat.bean.message.reply;

import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.FileMessageBean;

public class FileReplyQuoteBean extends TUIReplyQuoteBean {
    private String fileName;

    @Override
    public void onProcessReplyQuoteBean(TUIMessageBean messageBean) {
        if (messageBean instanceof FileMessageBean) {
            fileName = ((FileMessageBean) messageBean).getFileName();
        }
    }

    public String getFileName() {
        return fileName;
    }
}
