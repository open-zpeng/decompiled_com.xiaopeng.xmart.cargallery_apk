package com.xiaopeng.xui.widget.dialogview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.vui.VuiViewScene;
import com.xiaopeng.xui.widget.dialogview.XDialogViewInterface;
/* loaded from: classes.dex */
public class XDialogView extends VuiViewScene {
    private XDialogViewInterface.IScreenPositionCallback mScreenPositionCallback;
    private XDialogViewDelegate mXDialogViewDelegate;

    public XDialogView(Context context) {
        this(context, 0);
    }

    public XDialogView(Context context, int style) {
        XDialogViewDelegate create = XDialogViewDelegate.create(this, context, style);
        this.mXDialogViewDelegate = create;
        setVuiView(create.getContentView());
    }

    public XDialogView setTitle(CharSequence text) {
        this.mXDialogViewDelegate.setTitle(text);
        return this;
    }

    public XDialogView setTitle(int resId) {
        this.mXDialogViewDelegate.setTitle(resId);
        return this;
    }

    public XDialogView setIcon(int iconId) {
        this.mXDialogViewDelegate.setIcon(iconId);
        return this;
    }

    public XDialogView setIcon(Drawable icon) {
        this.mXDialogViewDelegate.setIcon(icon);
        return this;
    }

    public XDialogView setMessage(CharSequence text) {
        this.mXDialogViewDelegate.setMessage(text);
        return this;
    }

    public XDialogView setMessage(int resId) {
        this.mXDialogViewDelegate.setMessage(resId);
        return this;
    }

    public XDialogView setCustomView(View view) {
        this.mXDialogViewDelegate.setCustomView(view);
        return this;
    }

    public XDialogView setCustomView(View view, boolean useScroll) {
        this.mXDialogViewDelegate.setCustomView(view, useScroll);
        return this;
    }

    public XDialogView setCustomView(int resId) {
        this.mXDialogViewDelegate.setCustomView(resId);
        return this;
    }

    public XDialogView setCustomView(int resId, boolean useScroll) {
        this.mXDialogViewDelegate.setCustomView(resId, useScroll);
        return this;
    }

    public XDialogView setCloseVisibility(boolean show) {
        this.mXDialogViewDelegate.setCloseVisibility(show);
        return this;
    }

    public boolean isCloseShowing() {
        return this.mXDialogViewDelegate.isCloseShowing();
    }

    @Deprecated
    public XDialogView setTitleVisibility(boolean show) {
        setTitleBarVisibility(show);
        return this;
    }

    public XDialogView setTitleBarVisibility(boolean show) {
        this.mXDialogViewDelegate.setTitleBarVisibility(show);
        return this;
    }

    public XDialogView setPositiveButton(int resId) {
        this.mXDialogViewDelegate.setPositiveButton(resId);
        return this;
    }

    public XDialogView setPositiveButton(CharSequence text) {
        this.mXDialogViewDelegate.setPositiveButton(text);
        return this;
    }

    public XDialogView setPositiveButtonListener(XDialogViewInterface.OnClickListener listener) {
        this.mXDialogViewDelegate.setPositiveButtonListener(listener);
        return this;
    }

    public XDialogView setPositiveButton(int resId, XDialogViewInterface.OnClickListener listener) {
        this.mXDialogViewDelegate.setPositiveButton(resId, listener);
        return this;
    }

    public XDialogView setPositiveButton(CharSequence text, XDialogViewInterface.OnClickListener listener) {
        this.mXDialogViewDelegate.setPositiveButton(text, listener);
        return this;
    }

    public XDialogView setNegativeButton(int resId) {
        this.mXDialogViewDelegate.setNegativeButton(resId);
        return this;
    }

    public XDialogView setNegativeButton(CharSequence text) {
        this.mXDialogViewDelegate.setNegativeButton(text);
        return this;
    }

    public XDialogView setNegativeButtonListener(XDialogViewInterface.OnClickListener listener) {
        this.mXDialogViewDelegate.setNegativeButtonListener(listener);
        return this;
    }

    public XDialogView setNegativeButton(int resId, XDialogViewInterface.OnClickListener listener) {
        this.mXDialogViewDelegate.setNegativeButton(resId, listener);
        return this;
    }

    public XDialogView setNegativeButton(CharSequence text, XDialogViewInterface.OnClickListener listener) {
        this.mXDialogViewDelegate.setNegativeButton(text, listener);
        return this;
    }

    public XDialogView setPositiveButtonInterceptDismiss(boolean interceptDismiss) {
        this.mXDialogViewDelegate.setPositiveButtonInterceptDismiss(interceptDismiss);
        return this;
    }

    public XDialogView setNegativeButtonInterceptDismiss(boolean interceptDismiss) {
        this.mXDialogViewDelegate.setNegativeButtonInterceptDismiss(interceptDismiss);
        return this;
    }

    public XDialogView setPositiveButtonEnable(boolean enable) {
        this.mXDialogViewDelegate.setPositiveButtonEnable(enable);
        return this;
    }

    public XDialogView setNegativeButtonEnable(boolean enable) {
        this.mXDialogViewDelegate.setNegativeButtonEnable(enable);
        return this;
    }

    public boolean isPositiveButtonEnable() {
        return this.mXDialogViewDelegate.isPositiveButtonEnable();
    }

    public boolean isNegativeButtonEnable() {
        return this.mXDialogViewDelegate.isNegativeButtonEnable();
    }

    public boolean isPositiveButtonShowing() {
        return this.mXDialogViewDelegate.isPositiveButtonShowing();
    }

    public boolean isNegativeButtonShowing() {
        return this.mXDialogViewDelegate.isNegativeButtonShowing();
    }

    public void startPositiveButtonCountDown(int delayDismiss) {
        this.mXDialogViewDelegate.startPositiveButtonCountDown(delayDismiss);
    }

    public void startNegativeButtonCountDown(int delayDismiss) {
        this.mXDialogViewDelegate.startNegativeButtonCountDown(delayDismiss);
    }

    public ViewGroup getContentView() {
        return this.mXDialogViewDelegate.getContentView();
    }

    public XDialogView setOnCloseListener(XDialogViewInterface.OnCloseListener listener) {
        this.mXDialogViewDelegate.setOnCloseListener(listener);
        return this;
    }

    public XDialogView setOnCountDownListener(XDialogViewInterface.OnCountDownListener listener) {
        this.mXDialogViewDelegate.setOnCountDownListener(listener);
        return this;
    }

    public XDialogView setOnDismissListener(XDialogViewInterface.OnDismissListener listener) {
        this.mXDialogViewDelegate.setOnDismissListener(listener);
        return this;
    }

    public XDialogView setScreenPositionCallback(XDialogViewInterface.IScreenPositionCallback listener) {
        this.mScreenPositionCallback = listener;
        return this;
    }

    public void setThemeCallback(ThemeViewModel.OnCallback onCallback) {
        this.mXDialogViewDelegate.setThemeCallback(onCallback);
    }

    public boolean onKey(int keyCode, KeyEvent event) {
        return this.mXDialogViewDelegate.onKey(keyCode, event);
    }

    @Override // com.xiaopeng.xui.vui.VuiViewScene
    public void onBuildScenePrepare() {
        this.mXDialogViewDelegate.onBuildScenePrepare();
    }

    @Override // com.xiaopeng.xui.vui.IVuiViewScene
    public int getVuiDisplayLocation() {
        XDialogViewInterface.IScreenPositionCallback iScreenPositionCallback = this.mScreenPositionCallback;
        if (iScreenPositionCallback == null) {
            return 0;
        }
        return iScreenPositionCallback.getScreenPositionInfo();
    }
}
