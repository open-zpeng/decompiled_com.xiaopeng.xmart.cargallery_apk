package com.xiaopeng.xui.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.core.content.ContextCompat;
import com.xiaopeng.vui.commons.IVuiEngine;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.vui.IVuiViewScene;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.popPanelView.XPopPanelView;
import com.xiaopeng.xuimanager.systemui.osd.IOsdListener;
import com.xiaopeng.xuimanager.systemui.osd.OsdManager;
import com.xiaopeng.xuimanager.systemui.osd.OsdRegionRecord;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
/* loaded from: classes.dex */
public class XPopPanel implements IVuiViewScene {
    private static final String TAG = "XPopPanel";
    private static final String VUI_SCENE_TAG = "PopPanel";
    private Context mContext;
    private XPopPanelView mPopPanelView;
    private TN mTN;

    private XPopPanel(Context context, TN tn, View view) {
        this.mContext = context;
        this.mTN = tn;
        if (view != null) {
            XPopPanelView xPopPanelView = new XPopPanelView(view);
            this.mPopPanelView = xPopPanelView;
            xPopPanelView.setPopPanelViewInterface(new XPopPanelView.IPopPanelViewInterface() { // from class: com.xiaopeng.xui.app.XPopPanel.1
                @Override // com.xiaopeng.xui.widget.popPanelView.XPopPanelView.IPopPanelViewInterface
                public int getDisplayScreenId() {
                    return XPopPanel.this.getScreenId();
                }

                @Override // com.xiaopeng.xui.widget.popPanelView.XPopPanelView.IPopPanelViewInterface
                public void onVuiEventExecuted() {
                    Log.d(XPopPanel.TAG, "onVuiEventExecuted");
                    XPopPanel.this.handleUserEvent();
                }
            });
        }
    }

    public static void init(Context context) {
    }

    public void show() {
        OsdManager osdManager = OsdManager.get();
        TN tn = this.mTN;
        osdManager.requestShow(tn, tn.mType, this.mTN.mRegionId);
    }

    public void cancel() {
        TN tn = this.mTN;
        if (tn == null || !tn.getWindowState()) {
            return;
        }
        this.mTN.handleHide(102);
        OsdManager osdManager = OsdManager.get();
        TN tn2 = this.mTN;
        osdManager.notifyHide(tn2, tn2.mRegionId);
    }

    public boolean isShowing() {
        TN tn = this.mTN;
        if (tn != null) {
            return tn.getWindowState();
        }
        return false;
    }

    public int getScreenId() {
        TN tn = this.mTN;
        if (tn != null) {
            return tn.getScreenId();
        }
        return 1;
    }

    public void setOnStateListener(PopPanelInterface listener) {
        TN tn = this.mTN;
        if (tn == null) {
            return;
        }
        tn.setListener(listener);
    }

    public void handleUserEvent() {
        TN tn = this.mTN;
        if (tn == null) {
            return;
        }
        tn.holdPanel();
        this.mTN.reHidePanel();
    }

    public void setItemView(View itemView) {
        TN tn = this.mTN;
        if (tn != null && itemView != null) {
            tn.resetItemView(itemView);
        }
    }

    public void removeItemView() {
        TN tn = this.mTN;
        if (tn != null) {
            tn.removeItemView();
        }
    }

    /* loaded from: classes.dex */
    public static class XPopPanelBuilder {
        public static final int ID_SHARED_PRIMARY = 1;
        public static final int ID_SHARED_SECONDARY = 2;
        public static final int LENGTH_LONG = 5000;
        public static final int LENGTH_LONGER = 10000;
        public static final int LENGTH_SHORT = 2000;
        public static final int TYPE_NORMAL_WINDOW = 1;
        public static final int TYPE_STACK_WINDOW = 2;
        private boolean mClickable;
        private Context mContext;
        private View mItemView;
        private String mRegionId;
        private int mDuration = 2000;
        private int mWidth = 400;
        private int mHeight = 400;
        private int mType = 1;
        private int mBg = R.drawable.x_osd_background;
        private boolean mAutoHeight = true;
        private boolean mAutoHide = true;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes.dex */
        public @interface Duration {
        }

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes.dex */
        public @interface Type {
        }

        public XPopPanelBuilder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        public XPopPanelBuilder setAutoHide(boolean autoHide) {
            this.mAutoHide = autoHide;
            return this;
        }

        public XPopPanelBuilder setDurationLength(int duration) {
            this.mDuration = duration;
            return this;
        }

        public XPopPanelBuilder setWidth(int width) {
            this.mWidth = width;
            return this;
        }

        public XPopPanelBuilder setHeight(int height) {
            this.mHeight = height;
            return this;
        }

        public XPopPanelBuilder setItemView(View itemView) {
            this.mItemView = itemView;
            return this;
        }

        public XPopPanelBuilder setRegionId(String regionId) {
            this.mRegionId = regionId;
            return this;
        }

        public XPopPanelBuilder setType(int type) {
            this.mType = type;
            return this;
        }

        public XPopPanelBuilder setBackground(int resource) {
            this.mBg = resource;
            return this;
        }

        public XPopPanelBuilder setClickable(boolean clickable) {
            this.mClickable = clickable;
            return this;
        }

        public XPopPanelBuilder setSelfAdapterHeight(boolean autoHeight) {
            this.mAutoHeight = autoHeight;
            return this;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public XPopPanel create() {
            Log.i(XPopPanel.TAG, "create: mContext = " + this.mContext + "  mWidth = " + this.mWidth + "  mHeight = " + this.mHeight + "  mDuration = " + this.mDuration + "  mRegionId = " + this.mRegionId + "  mType = " + this.mType + "  mBg = " + this.mBg + "  mClickable = " + this.mClickable + "  mItemView = " + this.mItemView);
            TN tn = new TN(this.mContext, this.mWidth, this.mHeight, this.mDuration, this.mRegionId, this.mType, this.mBg, this.mClickable, this.mItemView, this.mAutoHeight, this.mAutoHide);
            return new XPopPanel(this.mContext, tn, this.mItemView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class TN extends IOsdListener.Stub {
        private static final int HIDE = 1;
        private PopPanelInterface listener;
        private boolean mAutoHeight;
        private boolean mAutoHide;
        private BackgroundLayout mBackgroundLayout;
        private final int mBg;
        private final boolean mClickable;
        private final Context mContext;
        private final int mDuration;
        private final Handler mHandler;
        private final int mHeight;
        private View mItemView;
        private final WindowManager.LayoutParams mParams;
        private final String mRegionId;
        private int mScreenId;
        private final int mType;
        private final WindowManager mWM;
        private final int mWidth;

        public TN(Context context, int width, int height, int duration, String regionId, int type, int bg, boolean clickable, View itemView, boolean autoHeight, boolean autoHide) {
            this.mType = type;
            this.mDuration = duration;
            this.mContext = context;
            this.mWidth = width;
            this.mHeight = height;
            this.mBg = bg;
            this.mRegionId = regionId;
            this.mClickable = clickable;
            this.mItemView = itemView;
            this.mAutoHeight = autoHeight;
            this.mAutoHide = autoHide;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            this.mParams = layoutParams;
            this.mWM = (WindowManager) context.getSystemService("window");
            layoutParams.gravity = 51;
            layoutParams.format = -3;
            layoutParams.flags = 168;
            layoutParams.packageName = context.getPackageName();
            layoutParams.windowAnimations = R.style.XAnimation_XOsd;
            this.mHandler = new Handler(Looper.myLooper(), null) { // from class: com.xiaopeng.xui.app.XPopPanel.TN.1
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:
                            TN.this.handleHide(200);
                            return;
                        default:
                            return;
                    }
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setListener(PopPanelInterface listener) {
            this.listener = listener;
        }

        private void setBackgroundLayout(View itemView, int minHeight, int setWidth, int setHeight, int maxHeight) {
            if (this.mAutoHeight) {
                setHeight = 0;
            }
            if (this.mBackgroundLayout == null) {
                BackgroundLayout backgroundLayout = new BackgroundLayout(this.mContext, this.mBg, this.mClickable, setWidth, setHeight, maxHeight, this.listener);
                this.mBackgroundLayout = backgroundLayout;
                backgroundLayout.setClipToOutline(true);
                this.mBackgroundLayout.setTN(this);
                this.mBackgroundLayout.setBackground(ContextCompat.getDrawable(this.mContext, this.mBg));
                this.mBackgroundLayout.setMinimumHeight(minHeight);
            }
            if (itemView != null) {
                if (this.mItemView.getParent() != null) {
                    ViewGroup parent = (ViewGroup) this.mItemView.getParent();
                    parent.removeAllViews();
                    Log.d(XPopPanel.TAG, "remove child");
                }
                this.mBackgroundLayout.addView(itemView);
            }
        }

        private static void setXpFlags(WindowManager.LayoutParams layoutParams, int screenId) {
            try {
                Field field = WindowManager.LayoutParams.class.getField("xpFlags");
                Field displayIdFlags = WindowManager.LayoutParams.class.getField("displayId");
                int currentXpFlags = field.getInt(layoutParams);
                if (screenId == 1) {
                    field.setInt(layoutParams, currentXpFlags | 16);
                } else if (screenId == 2) {
                    field.setInt(layoutParams, currentXpFlags | 32);
                } else if (screenId == 3) {
                    displayIdFlags.setInt(layoutParams, screenId);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        private void handleShow() {
            if (this.mBackgroundLayout.getParent() != null) {
                this.mWM.removeView(this.mBackgroundLayout);
            }
            if (this.mWM != null) {
                Log.d(XPopPanel.TAG, "BackgroundLayout : " + this.mBackgroundLayout);
                this.mWM.addView(this.mBackgroundLayout, this.mParams);
            }
            PopPanelInterface popPanelInterface = this.listener;
            if (popPanelInterface != null) {
                popPanelInterface.onShow();
            }
            if (this.mAutoHide) {
                this.mHandler.sendEmptyMessageDelayed(1, this.mDuration);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleHide(int code) {
            this.mHandler.removeMessages(1);
            if (this.mBackgroundLayout.getParent() != null) {
                this.mWM.removeView(this.mBackgroundLayout);
            }
            PopPanelInterface popPanelInterface = this.listener;
            if (popPanelInterface != null) {
                popPanelInterface.onHide();
                this.listener.onHide(code);
            }
            OsdManager.get().notifyHide(this, this.mRegionId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void holdPanel() {
            this.mHandler.removeMessages(1);
        }

        public void reHidePanel() {
            if (this.mAutoHide) {
                this.mHandler.sendEmptyMessageDelayed(1, this.mDuration);
            }
        }

        public boolean getWindowState() {
            BackgroundLayout backgroundLayout = this.mBackgroundLayout;
            return (backgroundLayout == null || backgroundLayout.getParent() == null) ? false : true;
        }

        public int getScreenId() {
            return this.mScreenId;
        }

        public void setScreenId(int mScreenId) {
            this.mScreenId = mScreenId;
        }

        public void resetItemView(View itemView) {
            BackgroundLayout backgroundLayout = this.mBackgroundLayout;
            if (backgroundLayout != null) {
                this.mItemView = itemView;
                backgroundLayout.addView(itemView);
            }
        }

        public void removeItemView() {
            View view = this.mItemView;
            if (view != null) {
                if (view.getParent() != null) {
                    ViewGroup parent = (ViewGroup) this.mItemView.getParent();
                    parent.removeAllViews();
                }
                this.mItemView = null;
            }
        }

        public void hideOsd() {
            handleHide(101);
        }

        public void showOsd(OsdRegionRecord record) {
            Log.i(XPopPanel.TAG, "showPanel: recordID = " + record.getScreenId() + " recordMinWidth = " + record.getMinWidth() + " MaxWidth = " + record.getMaxWidth() + " WindowType = " + record.getWindowType());
            if (record != null) {
                this.mParams.x = record.getX();
                this.mParams.y = record.getY();
                if (this.mWidth >= record.getMinWidth() && this.mWidth <= record.getMaxWidth()) {
                    this.mParams.width = this.mWidth;
                } else if (this.mWidth < record.getMinWidth()) {
                    this.mParams.width = record.getMinWidth();
                } else if (this.mWidth > record.getMaxWidth()) {
                    this.mParams.width = record.getMaxWidth();
                }
                if (this.mHeight >= record.getMinHeight() && this.mHeight <= record.getMaxHeight()) {
                    this.mParams.height = this.mHeight;
                } else if (this.mHeight < record.getMinHeight()) {
                    this.mParams.height = record.getMinHeight();
                } else if (this.mHeight > record.getMaxHeight()) {
                    this.mParams.height = record.getMaxHeight();
                }
                this.mParams.type = record.getWindowType();
                setScreenId(record.getScreenId());
                setXpFlags(this.mParams, record.getScreenId());
                setBackgroundLayout(this.mItemView, record.getMinHeight(), this.mParams.width, this.mParams.height, record.getMaxWidth());
                Log.d(XPopPanel.TAG, "setBackground Success");
                handleShow();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class BackgroundLayout extends XLinearLayout {
        private final int mBg;
        private final boolean mClickable;
        private final Context mContext;
        private final PopPanelInterface mListener;
        private final int mMaxHeight;
        private final int mSetHeight;
        private final int mSetWidth;
        private TN mTN;

        public BackgroundLayout(Context context, int bg, boolean clickable, int setWidth, int setHeight, int maxHeight, PopPanelInterface listener) {
            super(context);
            this.mContext = context;
            this.mBg = bg;
            this.mSetWidth = setWidth;
            this.mSetHeight = setHeight;
            this.mMaxHeight = maxHeight;
            this.mClickable = clickable;
            this.mListener = listener;
            this.mXViewDelegate.getThemeViewModel().setBackgroundResource(bg);
        }

        public void setTN(TN mTN) {
            this.mTN = mTN;
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int wSpec = View.MeasureSpec.makeMeasureSpec(this.mSetWidth, BasicMeasure.EXACTLY);
            int i = this.mSetHeight;
            if (i <= 0) {
                int i2 = this.mMaxHeight;
                if (i2 >= 0) {
                    int hSpec = View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE);
                    super.onMeasure(wSpec, hSpec);
                    return;
                }
                super.onMeasure(wSpec, heightMeasureSpec);
                return;
            }
            int hSpec2 = View.MeasureSpec.makeMeasureSpec(i, BasicMeasure.EXACTLY);
            super.onMeasure(wSpec, hSpec2);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
        public void onConfigurationChanged(Configuration newConfig) {
            Log.i(XPopPanel.TAG, "onConfigurationChanged: X = " + this.mTN.mParams.x + "  Y = " + this.mTN.mParams.y);
            super.onConfigurationChanged(newConfig);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case 0:
                    if (this.mClickable) {
                        this.mTN.holdPanel();
                        break;
                    } else {
                        return false;
                    }
                case 1:
                    this.mTN.reHidePanel();
                    break;
                case 3:
                    this.mTN.handleHide(100);
                    break;
            }
            return super.dispatchTouchEvent(motionEvent);
        }
    }

    /* loaded from: classes.dex */
    public interface PopPanelInterface {
        default void onShow() {
        }

        default void onHide() {
        }

        default void onHide(int code) {
            switch (code) {
                case 100:
                    Log.d(XPopPanel.TAG, "code : " + code + "pop panel is hidden by MotionEvent.ACTION_CANCEL");
                    return;
                case 101:
                    Log.d(XPopPanel.TAG, "code : " + code + "pop panel is hidden by server");
                    return;
                case 102:
                    Log.d(XPopPanel.TAG, "code : " + code + "pop panel is hidden by user event");
                    return;
                case 200:
                    Log.d(XPopPanel.TAG, "code : " + code + "pop panel hides normally");
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.xiaopeng.xui.vui.IVuiViewScene
    public void initVuiScene(String sceneId, IVuiEngine vuiEngine) {
        initVuiScene(sceneId, vuiEngine, null);
    }

    @Override // com.xiaopeng.xui.vui.IVuiViewScene
    public void initVuiScene(String sceneId, IVuiEngine vuiEngine, IVuiSceneListener listener) {
        if (this.mPopPanelView != null) {
            if (!sceneId.endsWith(VUI_SCENE_TAG)) {
                sceneId = sceneId + VUI_SCENE_TAG;
            }
            this.mPopPanelView.initVuiScene(sceneId, vuiEngine, listener);
        }
    }
}
