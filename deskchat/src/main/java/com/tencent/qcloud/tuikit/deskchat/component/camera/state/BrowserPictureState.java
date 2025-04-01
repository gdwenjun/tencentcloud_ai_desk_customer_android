package com.tencent.qcloud.tuikit.deskchat.component.camera.state;

import android.text.TextUtils;
import android.view.SurfaceHolder;

import com.tencent.qcloud.tuikit.deskcommon.util.FileUtil;
import com.tencent.qcloud.tuikit.deskchat.component.camera.view.CameraView;
import com.tencent.qcloud.tuikit.deskchat.util.TUIChatLog;

public class BrowserPictureState implements State {
    private static final String TAG = BrowserPictureState.class.getSimpleName();
    private CameraMachine machine;
    private String dataPath;

    public BrowserPictureState(CameraMachine machine) {
        this.machine = machine;
    }

    @Override
    public void cancel(SurfaceHolder holder, float screenProp) {
        TUIChatLog.i(TAG, "cancel");
        if (!TextUtils.isEmpty(dataPath)) {
            FileUtil.deleteFile(dataPath);
        }
        dataPath = null;
        machine.getCameraView().resetState(CameraView.TYPE_PICTURE);
        machine.setState(machine.getPreviewState());
        machine.startPreview(holder, screenProp);
    }

    @Override
    public void confirm() {
        TUIChatLog.i(TAG, "confirm");
        machine.getCameraView().confirmState(CameraView.TYPE_PICTURE);
        machine.setState(machine.getPreviewState());
    }

    @Override
    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    @Override
    public String getDataPath() {
        return dataPath;
    }
}
