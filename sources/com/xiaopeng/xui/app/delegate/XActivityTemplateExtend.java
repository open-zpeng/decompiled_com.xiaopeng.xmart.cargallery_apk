package com.xiaopeng.xui.app.delegate;

import android.app.Activity;
import android.os.Bundle;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.app.delegate.XActivityTemplate;
import com.xiaopeng.xui.app.delegate.XActivityTemplateExtend;
import com.xiaopeng.xui.utils.XLogUtils;
import com.xiaopeng.xui.utils.XuiUtils;
import com.xiaopeng.xui.widget.dialogview.XDialogView;
import com.xiaopeng.xui.widget.dialogview.XDialogViewInterface;
/* loaded from: classes.dex */
abstract class XActivityTemplateExtend implements XActivityTemplate, XActivityLifecycle {
    private static final String TAG = "XActivityTemplate";

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void initDismiss(XActivityDismissExtend xActivityDismissExtend);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void initDismissCauseGroup(XActivityDismissCauseGroup xActivityDismissCauseGroup);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void initWindowAttributes(XActivityWindowAttributes xActivityWindowAttributes);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void initWindowVisible(XActivityWindowVisible xActivityWindowVisible);

    XActivityTemplateExtend() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static XActivityTemplateExtend create(Activity activity, int type) {
        switch (type) {
            case 1:
                return new PanelImpl(activity);
            case 2:
                return new FullscreenImpl(activity);
            case 3:
                return new DialogImpl(activity);
            case 4:
                return new SuperPanelImpl(activity);
            default:
                return new NormalImpl(activity);
        }
    }

    /* loaded from: classes.dex */
    private static class BaseTemplate extends XActivityTemplateExtend {
        Activity mActivity;
        XActivityDismissExtend mDismiss;

        BaseTemplate(Activity mActivity) {
            this.mActivity = mActivity;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle var1) {
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowAttributes(XActivityWindowAttributes mBuilder) {
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initDismissCauseGroup(XActivityDismissCauseGroup causeGroup) {
            causeGroup.enableBackScene();
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowVisible(XActivityWindowVisible activityWindowVisible) {
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initDismiss(XActivityDismissExtend dismiss) {
            this.mDismiss = dismiss;
        }
    }

    /* loaded from: classes.dex */
    private static class NormalImpl extends BaseTemplate implements XActivityTemplate.Normal {
        NormalImpl(Activity mActivity) {
            super(mActivity);
        }
    }

    /* loaded from: classes.dex */
    private static class SuperPanelImpl extends BaseTemplate implements XActivityTemplate.SuperPanel {
        SuperPanelImpl(Activity mActivity) {
            super(mActivity);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle var1) {
            super.onCreate(var1);
        }
    }

    /* loaded from: classes.dex */
    private static class FullscreenImpl extends BaseTemplate implements XActivityTemplate.FullScreen {
        FullscreenImpl(Activity mActivity) {
            super(mActivity);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle var1) {
            super.onCreate(var1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class DialogImpl extends BaseTemplate implements XActivityTemplate.Dialog {
        DialogImpl(Activity mActivity) {
            super(mActivity);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle var1) {
            super.onCreate(var1);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initDismissCauseGroup(XActivityDismissCauseGroup causeGroup) {
            super.initDismissCauseGroup(causeGroup);
            causeGroup.enableOnPauseScene();
            causeGroup.enableOutSideScene();
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowAttributes(XActivityWindowAttributes builder) {
            builder.setWidth(-2).setHeight(-2).setGravity(17);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowVisible(XActivityWindowVisible windowVisible) {
            super.initWindowVisible(windowVisible);
            windowVisible.setAutoVisibleEnableOnPause(true);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplate.Dialog
        public void useXDialogView(XDialogView xDialogView) {
            xDialogView.setCloseVisibility(true);
            xDialogView.setOnDismissListener(new XDialogViewInterface.OnDismissListener() { // from class: com.xiaopeng.xui.app.delegate.-$$Lambda$XActivityTemplateExtend$DialogImpl$D6M74gFfgIWQhagroU09yD5oW1Y
                @Override // com.xiaopeng.xui.widget.dialogview.XDialogViewInterface.OnDismissListener
                public final void onDismiss(XDialogView xDialogView2) {
                    XActivityTemplateExtend.DialogImpl.this.lambda$useXDialogView$0$XActivityTemplateExtend$DialogImpl(xDialogView2);
                }
            });
        }

        public /* synthetic */ void lambda$useXDialogView$0$XActivityTemplateExtend$DialogImpl(XDialogView dialogView) {
            XLogUtils.i(XActivityTemplateExtend.TAG, "onDismiss for dialog ");
            if (this.mDismiss != null) {
                this.mDismiss.dismiss(0);
            }
        }
    }

    /* loaded from: classes.dex */
    private static class PanelImpl extends BaseTemplate implements XActivityTemplate.Panel {
        PanelImpl(Activity mActivity) {
            super(mActivity);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle var1) {
            super.onCreate(var1);
            this.mActivity.requestWindowFeature(14);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initDismissCauseGroup(XActivityDismissCauseGroup causeGroup) {
            super.initDismissCauseGroup(causeGroup);
            causeGroup.enableOnPauseScene();
            causeGroup.enableOutSideScene();
            causeGroup.enableSpeedTimeOutScene();
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowAttributes(XActivityWindowAttributes mBuilder) {
            int orientation = this.mActivity.getResources().getConfiguration().orientation;
            int x = XuiUtils.dip2px(this.mActivity, R.dimen.x_compat_app_panel_x);
            int y = XuiUtils.dip2px(this.mActivity, R.dimen.x_compat_app_panel_y);
            int width = XuiUtils.dip2px(this.mActivity, R.dimen.x_compat_app_panel_width);
            int height = XuiUtils.dip2px(this.mActivity, R.dimen.x_compat_app_panel_height);
            switch (orientation) {
                case 1:
                    mBuilder.setX(x).setY(y).setWidth(width).setHeight(height).setGravity(80);
                    return;
                case 2:
                    mBuilder.setX(x).setY(y).setWidth(width).setHeight(height).setGravity(8388627);
                    return;
                default:
                    return;
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowVisible(XActivityWindowVisible windowVisible) {
            super.initWindowVisible(windowVisible);
            windowVisible.setAutoVisibleEnableOnPause(true);
        }
    }
}
