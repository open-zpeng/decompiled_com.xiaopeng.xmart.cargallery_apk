package com.xiaopeng.xui.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.timepicker.XTimePicker;
/* loaded from: classes.dex */
public class XTimePickerDialog extends XDialog implements XTimePicker.OnTimeChangedListener, XDialogInterface.OnClickListener {
    private final XTimePicker mTimePicker;
    private final OnXTimeSetListener mTimeSetListener;

    /* loaded from: classes.dex */
    public interface OnXTimeSetListener {
        void onTimeSet(XTimePicker xTimePicker, int i, int i2);
    }

    public XTimePickerDialog(Context context, OnXTimeSetListener listener, int hourOfDay, int minute) {
        this(context, 0, listener, hourOfDay, minute);
    }

    public XTimePickerDialog(Context context, int themeResId, OnXTimeSetListener listener, int hourOfDay, int minute) {
        super(context, resolveDialogTheme(context, themeResId));
        this.mTimeSetListener = listener;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.x_time_picker, getContentView(), false);
        setCustomView(view, false);
        setPositiveButton(" ", this);
        setNegativeButton(" ", this);
        XTimePicker xTimePicker = (XTimePicker) view.findViewById(R.id.x_time_picker);
        this.mTimePicker = xTimePicker;
        xTimePicker.setCurrentHour(Integer.valueOf(hourOfDay));
        xTimePicker.setCurrentMinute(Integer.valueOf(minute));
        xTimePicker.setOnTimeChangedListener(this);
    }

    static int resolveDialogTheme(Context context, int resId) {
        if (resId == 0) {
            return R.style.XDialogView_Large;
        }
        return resId;
    }

    public XTimePicker getXTimePicker() {
        return this.mTimePicker;
    }

    public void setPositiveButtonText(String text) {
        setPositiveButton(text, this);
    }

    @Override // com.xiaopeng.xui.app.XDialog
    @Deprecated
    public XDialog setPositiveButton(CharSequence text, XDialogInterface.OnClickListener listener) {
        return super.setPositiveButton(text, this);
    }

    public void setNegativeButtonText(String text) {
        setNegativeButton(text, this);
    }

    @Override // com.xiaopeng.xui.app.XDialog
    @Deprecated
    public XDialog setNegativeButton(CharSequence text, XDialogInterface.OnClickListener listener) {
        return super.setNegativeButton(text, this);
    }

    public void updateTime(int hourOfDay, int minuteOfHour) {
        this.mTimePicker.setCurrentHour(Integer.valueOf(hourOfDay));
        this.mTimePicker.setCurrentMinute(Integer.valueOf(minuteOfHour));
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.OnTimeChangedListener
    public void onTimeChanged(XTimePicker view, int hourOfDay, int minute) {
    }

    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
    public void onClick(XDialog dialog, int which) {
        switch (which) {
            case -2:
                super.getDialog().cancel();
                return;
            case -1:
                OnXTimeSetListener onXTimeSetListener = this.mTimeSetListener;
                if (onXTimeSetListener != null) {
                    XTimePicker xTimePicker = this.mTimePicker;
                    onXTimeSetListener.onTimeSet(xTimePicker, xTimePicker.getCurrentHour().intValue(), this.mTimePicker.getCurrentMinute().intValue());
                    return;
                }
                return;
            default:
                return;
        }
    }
}
