package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.view.XViewDelegate;
import com.xiaopeng.xui.view.font.XFontScaleHelper;
/* loaded from: classes.dex */
public class XGroupHeader extends XLinearLayout {
    private static final int LOCATION_LEFT = 0;
    private static final int LOCATION_RIGHT = 1;
    private static final int MODE_BUTTON = 2;
    private static final int MODE_CUSTOM = 4;
    private static final int MODE_ICON = 1;
    private static final int MODE_LOADING = 3;
    private static final int MODE_NONE = 0;
    private int mActionMode;
    private View mRightView;
    private TextView mText;

    public XGroupHeader(Context context) {
        this(context, null);
    }

    public XGroupHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XGroupHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public XGroupHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XGroupHeader);
        int location = a.getInt(R.styleable.XGroupHeader_group_header_action_location, 0);
        if (location == 1) {
            LayoutInflater.from(context).inflate(R.layout.x_groupheader_right, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.x_groupheader, this);
        }
        initView();
        String text = a.getString(R.styleable.XGroupHeader_group_header_text);
        setText(text);
        this.mActionMode = a.getInt(R.styleable.XGroupHeader_group_header_action_mode, 0);
        int action = a.getResourceId(R.styleable.XGroupHeader_group_header_action, -1);
        setView(action);
        float size = a.getDimensionPixelSize(R.styleable.XGroupHeader_group_header_text_size, 0);
        this.mText.setTextSize(0, size);
        final XFontScaleHelper xFontScaleHelper = XFontScaleHelper.create(a, R.styleable.XGroupHeader_group_header_text_size);
        if (xFontScaleHelper != null && this.mXViewDelegate != null) {
            this.mXViewDelegate.setFontScaleChangeCallback(new XViewDelegate.onFontScaleChangeCallback() { // from class: com.xiaopeng.xui.widget.-$$Lambda$XGroupHeader$dhqqrWj8tZqUOmdYRAnzR5XJ-2A
                @Override // com.xiaopeng.xui.view.XViewDelegate.onFontScaleChangeCallback
                public final void onFontScaleChanged() {
                    XGroupHeader.this.lambda$new$0$XGroupHeader(xFontScaleHelper);
                }
            });
        }
        a.recycle();
    }

    public /* synthetic */ void lambda$new$0$XGroupHeader(XFontScaleHelper xFontScaleHelper) {
        xFontScaleHelper.refreshTextSize(this.mText);
    }

    private void setView(int action) {
        switch (this.mActionMode) {
            case 0:
            default:
                return;
            case 1:
                View inflate = LayoutInflater.from(getContext()).inflate(R.layout.x_groupheader_action_icon, (ViewGroup) this, false);
                this.mRightView = inflate;
                addView(inflate);
                if (action > 0) {
                    setIcon(action);
                    return;
                }
                return;
            case 2:
                View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.x_groupheader_action_button, (ViewGroup) this, false);
                this.mRightView = inflate2;
                addView(inflate2);
                if (action > 0) {
                    ((TextView) this.mRightView).setText(action);
                    return;
                }
                return;
            case 3:
                View inflate3 = LayoutInflater.from(getContext()).inflate(R.layout.x_groupheader_action_loading, (ViewGroup) this, false);
                this.mRightView = inflate3;
                addView(inflate3);
                return;
            case 4:
                if (action > 0) {
                    View inflate4 = LayoutInflater.from(getContext()).inflate(action, (ViewGroup) this, false);
                    this.mRightView = inflate4;
                    addView(inflate4);
                    return;
                }
                return;
        }
    }

    private void initView() {
        this.mText = (TextView) findViewById(R.id.x_groupheader_tv);
    }

    public void setText(CharSequence title) {
        this.mText.setText(title);
    }

    public void setCustom(int resId) {
        this.mActionMode = 4;
        removeView(this.mRightView);
        setView(resId);
    }

    public void setIcon(int resId) {
        if (this.mActionMode == 1) {
            ((ImageButton) this.mRightView).setImageResource(resId);
            return;
        }
        this.mActionMode = 1;
        removeView(this.mRightView);
        setView(resId);
    }

    public void setButtonText(int resId) {
        if (this.mActionMode == 2) {
            ((TextView) this.mRightView).setText(resId);
            return;
        }
        this.mActionMode = 2;
        removeView(this.mRightView);
        setView(resId);
    }

    public void showLoading(boolean show) {
        if (this.mActionMode == 3) {
            this.mRightView.setVisibility(show ? 0 : 4);
            return;
        }
        this.mActionMode = 3;
        removeView(this.mRightView);
        setView(0);
        this.mRightView.setVisibility(show ? 0 : 4);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        if (view != null) {
            view.setVisibility(4);
        }
        super.removeView(view);
    }

    public View getRightView() {
        return this.mRightView;
    }
}
