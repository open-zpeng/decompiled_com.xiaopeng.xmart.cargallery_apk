package com.xiaopeng.xui.widget.prompt;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import com.xiaopeng.xpui.R;
@Deprecated
/* loaded from: classes.dex */
public class XPrompt extends Prompt {
    private ViewGroup mHostView;

    public XPrompt(Context context) {
        super(context);
    }

    public static XPrompt makePrompt(Activity activity) {
        return makePrompt(activity, findHostViewFromActivity(activity));
    }

    public static XPrompt makePrompt(Activity activity, CharSequence text) {
        return makePrompt(activity, text, 0);
    }

    public static XPrompt makePrompt(Activity activity, CharSequence text, int duration) {
        return makePrompt(activity, findHostViewFromActivity(activity), text, duration);
    }

    public static XPrompt makePrompt(Context context, ViewGroup hostView, CharSequence text, int duration) {
        XPrompt xPrompt = makePrompt(context, hostView);
        xPrompt.addMessage(new XPromptMessage(duration, text));
        return xPrompt;
    }

    private static ViewGroup findHostViewFromActivity(Activity activity) {
        return (ViewGroup) activity.findViewById(16908290);
    }

    public static XPrompt makePrompt(Context context, ViewGroup hostView) {
        XPromptView xPromptView = (XPromptView) hostView.findViewById(R.id.x_prompt);
        XPrompt xPrompt = null;
        if (xPromptView != null) {
            xPrompt = (XPrompt) xPromptView.getPrompt();
        }
        if (xPrompt == null) {
            xPrompt = new XPrompt(context);
        }
        xPrompt.setHostView(hostView);
        return xPrompt;
    }

    public XPrompt setHostView(Activity activity) {
        setHostView(findHostViewFromActivity(activity));
        return this;
    }

    public XPrompt setHostView(ViewGroup hostView) {
        ViewGroup viewGroup = this.mHostView;
        if (viewGroup != null && !viewGroup.equals(hostView)) {
            this.mHostView.removeView(this.mXPromptView);
        }
        this.mHostView = hostView;
        return this;
    }

    @Override // com.xiaopeng.xui.widget.prompt.Prompt
    protected boolean addView() {
        ViewGroup viewGroup = this.mHostView;
        if (viewGroup != null) {
            viewGroup.addView(this.mXPromptView);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.xui.widget.prompt.Prompt
    protected void removeView() {
        if (this.mXPromptView.getParent() instanceof ViewGroup) {
            ((ViewGroup) this.mXPromptView.getParent()).removeView(this.mXPromptView);
        }
    }
}
