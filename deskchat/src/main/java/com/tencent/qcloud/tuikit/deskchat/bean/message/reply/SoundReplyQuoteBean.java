package com.tencent.qcloud.tuikit.deskchat.bean.message.reply;

import com.tencent.imsdk.v2.V2TIMSoundElem;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.deskcommon.bean.TUIReplyQuoteBean;
import com.tencent.qcloud.tuikit.deskchat.bean.message.SoundMessageBean;

public class SoundReplyQuoteBean extends TUIReplyQuoteBean {
    private V2TIMSoundElem soundElem;

    @Override
    public void onProcessReplyQuoteBean(TUIMessageBean messageBean) {
        if (messageBean instanceof SoundMessageBean) {
            soundElem = messageBean.getV2TIMMessage().getSoundElem();
        }
    }

    public int getDuring() {
        if (soundElem != null) {
            return soundElem.getDuration();
        }
        return 0;
    }
}
