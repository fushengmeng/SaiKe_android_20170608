package com.keruiyun.saike;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;


public class CountDownDialogActivity extends BaseActivity 
{
	private NumberPicker np1;
	private NumberPicker np3;
	private NumberPicker np4;
	private RadioButton _button01, _button02, _button03;
	private ImageButton _upButton;
	private ImageButton _downButton;
	private Button _okButton;
	private Button _cancelButton;
	
	public static CountDownDialogActivityListener _listener = null;
	


	@Override
	public int loadContentView() {
		return R.layout.dialog_anes_timer;
	}

	@Override
	public void initView() {
		super.initView();
		np1 = (NumberPicker)this.findViewById(R.id.numberPicker1);
		np3 = (NumberPicker)this.findViewById(R.id.numberPicker3);
		np4 = (NumberPicker)this.findViewById(R.id.numberPicker4);

		np1.setMaxValue(59);
		np1.setMinValue(0);
		np1.setValue(0);
		np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		np3.setMaxValue(59);
		np3.setMinValue(0);
		np3.setValue(0);
		np3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		np4.setMaxValue(59);
		np4.setMinValue(0);
		np4.setValue(0);
		np4.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		_button01 = (RadioButton)this.findViewById(R.id.button1);
		_button01.setChecked(true);
		_button01.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					_button02.setChecked(false);
					_button03.setChecked(false);
				}
			}
		});

		_button02 = (RadioButton)this.findViewById(R.id.button2);
		_button02.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					_button01.setChecked(false);
					_button03.setChecked(false);
				}
			}
		});

		_button03 = (RadioButton)this.findViewById(R.id.button3);
		_button03.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					_button01.setChecked(false);
					_button02.setChecked(false);
				}
			}
		});

		_upButton = (ImageButton)this.findViewById(R.id.main_air_ib_add);
		_upButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (_button01.isChecked())
				{
					int value = np1.getValue();
					if (value < np1.getMaxValue())
					{
						value++;
						np1.setValue(value);

						_button01.setText(String.format("%02d", np1.getValue()));
					}
					else
					{
						np1.setValue(np1.getMinValue());

						_button01.setText(String.format("%02d", np1.getValue()));
					}
				}
				else if (_button02.isChecked())
				{
					int value = np3.getValue();
					if (value < np3.getMaxValue())
					{
						value++;
						np3.setValue(value);

						_button02.setText(String.format("%02d", np3.getValue()));
					}
					else
					{
						np3.setValue(np3.getMinValue());

						_button02.setText(String.format("%02d", np3.getValue()));
					}
				}
				else if (_button03.isChecked())
				{
					int value = np4.getValue();
					if (value < np4.getMaxValue())
					{
						value++;
						np4.setValue(value);

						_button03.setText(String.format("%02d", np4.getValue()));
					}
					else
					{
						np4.setValue(np4.getMinValue());

						_button03.setText(String.format("%02d", np4.getValue()));
					}
				}
			}
		});

		_downButton = (ImageButton)this.findViewById(R.id.main_air_ib_red);
		_downButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (_button01.isChecked())
				{
					int value = np1.getValue();
					if (value > np1.getMinValue())
					{
						value--;
						np1.setValue(value);

						_button01.setText(String.format("%02d", np1.getValue()));
					}
					else
					{
						np1.setValue(np1.getMaxValue());

						_button01.setText(String.format("%02d", np1.getValue()));
					}
				}
				else if (_button02.isChecked())
				{
					int value = np3.getValue();
					if (value > np3.getMinValue())
					{
						value--;
						np3.setValue(value);

						_button02.setText(String.format("%02d", np3.getValue()));
					}
					else
					{
						np3.setValue(np3.getMaxValue());

						_button02.setText(String.format("%02d", np3.getValue()));
					}
				}
				else if (_button03.isChecked())
				{
					int value = np4.getValue();
					if (value > np4.getMinValue())
					{
						value--;
						np4.setValue(value);

						_button03.setText(String.format("%02d", np4.getValue()));
					}
					else
					{
						np4.setValue(np4.getMaxValue());

						_button03.setText(String.format("%02d", np4.getValue()));
					}
				}
			}
		});

		_okButton = (Button)this.findViewById(R.id.okButton);
		_okButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (null != _listener)
				{
					_listener.countDownDialogActivityDidSave(np1, np3, np4);
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
	protected void onDestroy() 
	{
		super.onDestroy();
	}

}
