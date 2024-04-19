package com.xiaopeng.xui.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.datepicker.XDatePicker;
import java.util.Calendar;
/* loaded from: classes.dex */
public class XDatePickerDialog extends XDialog implements XDatePicker.OnDateChangedListener, XDialogInterface.OnClickListener {
    private final XDatePicker mDatePicker;
    private OnXDateSetListener mDateSetListener;

    /* loaded from: classes.dex */
    public interface OnXDateSetListener {
        void onDateSet(XDatePicker xDatePicker, int i, int i2, int i3);
    }

    public XDatePickerDialog(Context context) {
        this(context, R.style.XDialogView_Large, null, Calendar.getInstance(), -1, -1, -1);
    }

    public XDatePickerDialog(Context context, int themeResId) {
        this(context, themeResId, null, Calendar.getInstance(), -1, -1, -1);
    }

    public XDatePickerDialog(Context context, OnXDateSetListener listener, int year, int month, int dayOfMonth) {
        this(context, R.style.XDialogView_Large, listener, null, year, month, dayOfMonth);
    }

    public XDatePickerDialog(Context context, int themeResId, OnXDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        this(context, themeResId, listener, null, year, monthOfYear, dayOfMonth);
    }

    private XDatePickerDialog(Context context, int themeResId, OnXDateSetListener listener, Calendar calendar, int year, int monthOfYear, int dayOfMonth) {
        super(context, resolveDialogTheme(context, themeResId));
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.x_date_picker, getContentView(), false);
        setCustomView(view, false);
        setPositiveButton(" ", this);
        setNegativeButton(" ", this);
        if (calendar != null) {
            year = calendar.get(1);
            monthOfYear = calendar.get(2);
            dayOfMonth = calendar.get(5);
        }
        XDatePicker xDatePicker = (XDatePicker) view.findViewById(R.id.x_date_picker);
        this.mDatePicker = xDatePicker;
        xDatePicker.init(year, monthOfYear, dayOfMonth, this);
        this.mDateSetListener = listener;
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

    public void updateDate(int year, int month, int dayOfMonth) {
        this.mDatePicker.updateDate(year, month, dayOfMonth);
    }

    public XDatePicker getXDatePicker() {
        return this.mDatePicker;
    }

    private static int resolveDialogTheme(Context context, int themeResId) {
        if (themeResId == 0) {
            return R.style.XDialogView_Large;
        }
        return themeResId;
    }

    public void setOnXDateSetListener(OnXDateSetListener dateSetListener) {
        this.mDateSetListener = dateSetListener;
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.OnDateChangedListener
    public void onDateChanged(XDatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.mDatePicker.init(year, monthOfYear, dayOfMonth, this);
    }

    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
    public void onClick(XDialog dialog, int which) {
        switch (which) {
            case -2:
                super.getDialog().cancel();
                return;
            case -1:
                if (this.mDateSetListener != null) {
                    this.mDatePicker.clearFocus();
                    OnXDateSetListener onXDateSetListener = this.mDateSetListener;
                    XDatePicker xDatePicker = this.mDatePicker;
                    onXDateSetListener.onDateSet(xDatePicker, xDatePicker.getYear(), this.mDatePicker.getMonth(), this.mDatePicker.getDayOfMonth());
                    return;
                }
                return;
            default:
                return;
        }
    }
}
