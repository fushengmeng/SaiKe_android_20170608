package com.keruiyun.saike;

import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

public interface DateDialogActivityListener
{
	public void dateDialogActivityDidSave(DatePicker dp, TimePicker tp, NumberPicker np);
}
