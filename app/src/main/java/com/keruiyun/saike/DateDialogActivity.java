package com.keruiyun.saike;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;


public class DateDialogActivity extends BaseActivity 
{
	private DatePicker dp;
	private TimePicker tp;
	private NumberPicker np;
	private Button _okButton;
	private Button _cancelButton;
	
	public static DateDialogActivityListener _listener = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dialog_date);
		
		dp = (DatePicker)this.findViewById(R.id.datePicker1);
		tp = (TimePicker)this.findViewById(R.id.timePicker1);
		np = (NumberPicker)this.findViewById(R.id.numberPicker1);

		Calendar calendar = Calendar.getInstance();
		np.setMaxValue(59);
		np.setMinValue(0);
		np.setValue(calendar.get(Calendar.SECOND));

		np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		dp.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		tp.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		tp.setIs24HourView(true);
		
		_okButton = (Button)this.findViewById(R.id.okButton);
		_okButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (null != _listener)
				{
					_listener.dateDialogActivityDidSave(dp, tp, np);
				}
				
				finish();
			}
		});
		
		_cancelButton = (Button)this.findViewById(R.id.cancelButton);
		_cancelButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				finish();
			}
		});
	}

	@Override
	public int loadContentView() {
		return 0;
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
	}

}
