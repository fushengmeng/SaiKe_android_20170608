package com.keruiyun.saike;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.util.SystemDateTime;


public class ClockActivity extends BaseActivity
{
	private TextView tvOpTime, tvOpTime1, tvOpTime3, tvOpTime4, tvOpTime5,
			tvAnesTime, tvAnesTime1, tvAnesTime3, tvAnesTime4, tvAnesTime5;
	private TextView tvAirTemp, tvAirHum, tvCurrentDay;
	private Button btnAirSwitch, btnWaiting, btnAirRunning;
	private BroadcastReceiver broadcastReceiver;

	private int isOpSwitchOpen = 0, isOpWaiting = 0, isPressRunning = 0;

	private ImageView ivStatusOk, ivStatusWaiting, ivStatusError, ivStatsStop,
			ivStatusRunning;
	private FrameLayout flDate;
	private ImageView dataStatus;
	private Button _mainPageButton;
	private AnalogClockView _clockView;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clock);
		
		initView();
		initData();
		setListener();

		registerBroadCast();
		setTextTypeFace();
	}

	@Override
	public int loadContentView() {
		return 0;
	}

	// 初始化View
	public void initView()
	{
		tvOpTime = (TextView)findViewById(R.id.main_op_tv_timer);
		tvOpTime1 = (TextView)findViewById(R.id.main_op_tv_timer1);
		tvOpTime3 = (TextView)findViewById(R.id.main_op_tv_timer3);
		tvOpTime4 = (TextView)findViewById(R.id.main_op_tv_timer4);
		tvOpTime5 = (TextView)findViewById(R.id.main_op_tv_timer5);

		tvAnesTime = (TextView)findViewById(R.id.main_anes_tv_timer);
		tvAnesTime1 = (TextView)findViewById(R.id.main_anes_tv_timer1);
		tvAnesTime3 = (TextView)findViewById(R.id.main_anes_tv_timer3);
		tvAnesTime4 = (TextView)findViewById(R.id.main_anes_tv_timer4);
		tvAnesTime5 = (TextView)findViewById(R.id.main_anes_tv_timer5);

		tvAirTemp = (TextView)findViewById(R.id.main_air_tv_temp);
		tvAirHum = (TextView)findViewById(R.id.main_air_tv_hum);

		btnAirSwitch = (Button)findViewById(R.id.main_air_ib_switch);
		btnWaiting = (Button)findViewById(R.id.main_air_ib_waiting);
		btnAirRunning = (Button)findViewById(R.id.main_op_ib_runing);

		ivStatusOk = (ImageView)findViewById(R.id.radio0);
		ivStatusWaiting = (ImageView)findViewById(R.id.radio1);
		ivStatusError = (ImageView)findViewById(R.id.radio3);
		ivStatsStop = (ImageView)findViewById(R.id.radio4);
		ivStatusRunning = (ImageView)findViewById(R.id.radio5);

		flDate = (FrameLayout)findViewById(R.id.main_time_fl);

		dataStatus = (ImageView)findViewById(R.id.data_status);
		
		_mainPageButton = (Button)findViewById(R.id.mainPageButton);
		
		tvCurrentDay = (TextView)findViewById(R.id.main_time_tv_current);
		
		_clockView = (AnalogClockView)findViewById(R.id.clock_view);
		_clockView.start();
	}

	private void initData()
	{
		airHum(Color.parseColor("#000000"), 0);
		airTemp(Color.parseColor("#000000"), 0);

		mDataHandler.sendEmptyMessageDelayed(1, 60000);
	}

	private void setListener() 
	{
		btnAirSwitch.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				airSwitch();
			}
		});
		
		btnWaiting.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				airWaiting();
			}
		});
		
		btnAirRunning.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				airRunning();
			}
		});

		flDate.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				//dateDialog();
			}
		});
		
		_mainPageButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				finish();
			}
		});
	}

	// 空调开关
	private void airSwitch() 
	{
		if (isOpSwitchOpen == 0) 
		{
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_POWER_KEY, 1);
		} 
		else if (isOpSwitchOpen == 1)
		{
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_POWER_KEY, 0);
		}
		
		isOpSwitchOpen = (isOpSwitchOpen == 0) ? 1 : 0;
		
		opSwitchChange();
	}

	// 空调值班
	private void airWaiting() 
	{
		if (isOpWaiting == 0)
		{
			SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_DUTY_KEY,
					1);
		} 
		else if (isOpWaiting == 1) 
		{
			SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_DUTY_KEY,
					0);
		}
		
		isOpWaiting = (isOpWaiting == 0) ? 1 : 0;
		
		opWaitingChange();
	}

	// 空调运行
	private void airRunning()
	{
		if (isPressRunning == 0) 
		{
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PRESSURE_KEY, 1);
		} 
		else if (isPressRunning == 1) 
		{
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PRESSURE_KEY, 0);
		}
		
		isPressRunning = (isPressRunning == 0) ? 1 : 0;
		
		opPressChange();
	}

	private void registerBroadCast()
	{
		broadcastReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context arg0, Intent arg1) 
			{
				dataStatus();
				
				String action = arg1.getAction();
				if ("com.keruiyun.saike.clock".equals(action))
				{
					int command = arg1.getIntExtra("command", 0);
					String data = arg1.getStringExtra("value");
					
					if (1 == command)
					{
						setOpDate(data);
					}
					else if (2 == command)
					{
						setAnesDate(data);
					}
					else if (3 == command)
					{
						tvCurrentDay.setText(data);
					}
				}
				else
				{
					int addr = arg1.getIntExtra("addr", -1);
					short data = arg1.getShortExtra("data", (short) -1);
					switch (addr)
					{
						case SerialSaunaThread.ADDR_AIR_TEMPERATURE:
							airTemp(Color.parseColor("#000000"), data);
							break;
						case SerialSaunaThread.ADDR_AIR_HUMIDITY:
							airHum(Color.parseColor("#000000"), data);
							break;
						case SerialSaunaThread.ADDR_POWER_KEY:
							isOpSwitchOpen = data;
							opSwitchChange();
							break;
						case SerialSaunaThread.ADDR_DUTY_KEY:
							isOpWaiting = data;
							opWaitingChange();
							break;
						case SerialSaunaThread.ADDR_PRESSURE_KEY:
							isPressRunning = data;
							opPressChange();
							break;
						case SerialSaunaThread.ADDR_STATUS:
							// 机组状态-系统运行
							int systemRunning = data & 0x01;
							systemRunning(systemRunning);
							
							// 机组状态-值班运行
							int waitRunning = (data >> 1) & 0x01;
							waitRunning(waitRunning);
							
							// 机组状态-系统故障
							int alarmSystem = (data >> 2) & 0x01;
							alarmSystem(alarmSystem);
							
							// 机组状态-高效报警
							int alarm3 = (data >> 5) & 0x01;
							alarm3(alarm3);
							
							break;
						default:
							break;
					}
				}
			}
		};

		IntentFilter intentFilter = new IntentFilter("com.keruiyun.saike.order");
		intentFilter.addAction("com.keruiyun.saike.clock");
		this.registerReceiver(broadcastReceiver, intentFilter);
	}

	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		
		_clockView.stop();
		
		if (null != broadcastReceiver)
		{
			try 
			{
				this.unregisterReceiver(broadcastReceiver);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	private void opSwitchChange()
	{
		if (isOpSwitchOpen == 0)
		{
			btnAirSwitch.setSelected(false);
		} 
		else if (isOpSwitchOpen == 1) 
		{
			btnAirSwitch.setSelected(true);
		}
	}

	private void opWaitingChange()
	{
		if (isOpWaiting == 0)
		{
			btnWaiting.setSelected(false);
		}
		else if (isOpWaiting == 1) 
		{
			btnWaiting.setSelected(true);
		}
	}

	private void opPressChange()
	{
		if (isPressRunning == 0) 
		{
			btnAirRunning.setSelected(false);
			ivStatusRunning.setImageResource(R.drawable.st_off);
		}
		else if (isPressRunning == 1)
		{
			btnAirRunning.setSelected(true);
			ivStatusRunning.setImageResource(R.drawable.st_on);
		}
	}

	private void systemRunning(int running) 
	{
		if (1 == running)
		{
			ivStatusOk.setImageResource(R.drawable.st_on);
		} 
		else 
		{
			ivStatusOk.setImageResource(R.drawable.st_off);
		}
	}

	private void waitRunning(int running) 
	{
		if (1 == running) 
		{
			ivStatusWaiting.setImageResource(R.drawable.st_on);
		} 
		else 
		{
			ivStatusWaiting.setImageResource(R.drawable.st_off);
		}
	}

	private void alarmSystem(int running) 
	{
		if (1 == running)
		{
			ivStatusError.setImageResource(R.drawable.st_error);
		} 
		else
		{
			ivStatusError.setImageResource(R.drawable.st_off);
		}
	}

	private void alarm3(int running)
	{
		if (1 == running) 
		{
			ivStatsStop.setImageResource(R.drawable.st_error);
		} 
		else 
		{
			ivStatsStop.setImageResource(R.drawable.st_off);
		}
	}

	private void airTemp(int color, int data)
	{
		String rep = "";
		if (color == Color.parseColor("#000000"))
		{
			rep = String.format("%.1f", data / 10.0f);
		}
		else 
		{
			rep = String.format("%.1f", data * 1.0f);
		}

		tvAirTemp.setText(rep);
		tvAirTemp.setTextColor(color);
	}

	private void airHum(int color, int data)
	{
		String rep = "";

		if (color == Color.parseColor("#000000"))
		{
			rep = String.format("%.1f", data / 10.0f);
		} 
		else
		{
			rep = String.format("%.1f", data * 1.0f);
		}

		tvAirHum.setText(rep);
		tvAirHum.setTextColor(color);
	}

	private void setTextTypeFace() 
	{
		Typeface typeFace = Typeface.createFromAsset(this.getAssets(),
				"fonts/ziti.TTF");
		
		tvOpTime.setTypeface(typeFace);
		tvOpTime1.setTypeface(typeFace);
		tvOpTime3.setTypeface(typeFace);
		tvOpTime4.setTypeface(typeFace);
		tvOpTime5.setTypeface(typeFace);
		tvAnesTime.setTypeface(typeFace);
		tvAnesTime1.setTypeface(typeFace);
		tvAnesTime3.setTypeface(typeFace);
		tvAnesTime4.setTypeface(typeFace);
		tvAnesTime5.setTypeface(typeFace);
	}

	private void dateDialog() 
	{
		final AlertDialog.Builder dialog = new AlertDialog.Builder(
				this);
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.dialog_date, null);
		final DatePicker dp = (DatePicker) contentView
				.findViewById(R.id.datePicker1);
		final TimePicker tp = (TimePicker) contentView
				.findViewById(R.id.timePicker1);
		final NumberPicker np = (NumberPicker) contentView
				.findViewById(R.id.numberPicker1);

		Calendar calendar = Calendar.getInstance();
		np.setMaxValue(59);
		np.setMinValue(0);
		np.setValue(calendar.get(Calendar.SECOND));

		np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		dp.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		tp.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		tp.setIs24HourView(true);
		dialog.setTitle("时间设定");
		dialog.setView(contentView);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				arg0.dismiss();
				int y = dp.getYear();
				int m = dp.getMonth() + 1;
				int d = dp.getDayOfMonth();

				int h = tp.getCurrentHour();
				int mi = tp.getCurrentMinute();

				try 
				{
					SystemDateTime.setDateTime(y, m, d, h, mi, np.getValue());
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.create().show();
	}

	private Handler mDataHandler = new Handler(new Handler.Callback()
	{
		@Override
		public boolean handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 1:
					dataStatus.setVisibility(View.VISIBLE);
					break;
				default:
					break;
			}
			
			return false;
		}
	});

	private void dataStatus()
	{
		// TODO Auto-generated method stub
		mDataHandler.removeCallbacksAndMessages(null);
		if (dataStatus.getVisibility() != View.GONE)
		{
			dataStatus.setVisibility(View.GONE);
		}
		
		mDataHandler.sendEmptyMessageDelayed(1, 60000);
	}
	
	private void setOpDate(String time) 
	{
		tvOpTime.setText(time.substring(0, 2));
		tvOpTime3.setText(time.substring(3, 5));
		tvOpTime5.setText(time.substring(6, 8));
	}
	
	private void setAnesDate(String time) 
	{
		tvAnesTime.setText(time.substring(0, 2));
		tvAnesTime3.setText(time.substring(3, 5));
		tvAnesTime5.setText(time.substring(6, 8));
	}
	
}
