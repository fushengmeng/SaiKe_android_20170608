package com.keruiyun.saike;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.gongwen.marqueen.NoticeMF;
import com.keruiyun.saike.controls.VerticalSeekBar;
import com.keruiyun.saike.fragment.ChatFragment;
import com.keruiyun.saike.main.MainApplication;
import com.keruiyun.saike.model.PeerModel;
import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.util.Consts;
import com.keruiyun.saike.util.PreferencesUtil;
import com.keruiyun.saike.util.SystemDateTime;


public class MainActivity extends Fragment implements MenuFragmentListener, QuickCallFragmentListener, VideoCallFragmentListener, OnCompletionListener, CountDownDialogActivityListener, DateDialogActivityListener, TimeSettingDialogActivityListener 
{
	private TextView tvOpTime, tvOpTime1, tvOpTime3, tvOpTime4, tvOpTime5,
			tvAnesTime, tvAnesTime1, tvAnesTime3, tvAnesTime4, tvAnesTime5,
			tvCurrentTime, tvCurrentTime1, tvCurrentTime3, tvCurrentTime4,
			tvCurrentTime5, tvCurrentDay, _dustParticle01TextView, _dustParticle02TextView;
	private ImageButton btnOpStart, btnOpPause, btnOpReset;
	private ImageButton btnAnesStart, btnAnesPause, btnAnesReset;
	private TextView tvAirTemp, tvAirHum, tvAirPressure, tvMusicBack,
			tvCopyRight;
	private ImageButton btnAirTempAdd, btnAirTempReduce, btnAirHumAdd,
			btnAirHumReduce;
	private Button btnAirSwitch, btnWaiting, btnAirRunning, btnAir;
	private TextView etDial, etStatusFire, etStatusPower;
	private Button btnDial1, btnDial2, btnDial3, btnDial4, btnDial5,
			btnDial6, btnDial7, btnDial8, btnDial9, btnDial0, btnDialXing,
			btnDialJing;
	private ImageButton btnDialSlience, btnDialDial, btnDialHandUp;
	private ImageButton stopButton;
	private int opTimer, anesTimer, anesTimerValue = 0;
	private SimpleDateFormat sdf, sdf1;
	private VerticalSeekBar seekBar;
	private ImageView ivO2Status, ivCompressO2Status, ivNitrogenStatus,
			ivCarbonStatus, ivLaughStatus, ivNegStatus, ivArgonStatus;
	private ImageButton btnAirPressureAdd, btnAirPressureReduce;
	private BroadcastReceiver broadcastReceiver;

	private int isOpSwitchOpen = 0, isOpWaiting = 0, isPressRunning = 0;
	private Button llLight1, llLight2, llWind, llLook, llShaow, llDesk,
			llSpare, _clockPageButton;
	private FrameLayout llMainControl, llPower, llFire;

	private int isLight1 = 0, isLight2 = 0, isWind = 0, isLook = 0,
			isShaow = 0, isDesk = 0, isSpare = 0;
	private int currentVolume, musicBack;
	private ImageView ivMusicIcon;
	private boolean isOpStart = false, isAnesStart = false, isTikle = false;
	private ImageView ivStatusOk, ivStatusWaiting, ivStatusError, ivStatsStop,
			ivStatusRunning, ivStatusFire, ivStatusPower;
	private FrameLayout flTimer, flAnesTimer, flDate;
	private ImageButton btnVoiceAdd, btnVoiceReduce;
	private boolean isTempSetting = false, isHumSetting = false,
			isPressSetting = false;
	private int tempSetting, humSetting, pressSetting;
	private boolean isDialing = false;
	private ImageView dataStatus;
	private Button _quickCallButton;
	private Button _videoCallButton;
	private Button _localMusicButton;
	private Button _remoteMusicButton;
	private Button _bluetoothMusicButton;
	private TextView airPress01TextView, airPress02TextView, airPress03TextView,
	                 airPress04TextView, airPress05TextView, airPress06TextView,
	                 airPress07TextView;
	
	private MediaPlayer _mediaPlayer;

	//public ZoomView zoomView;
	
	private int[] _ids;
	private String[] _artists;
	private String[] _titles;
	private int _position = -1;
	private int _playOrder = 0;
	
    MarqueeView marqueeView;
	final List<String> datas = Arrays.asList("照明即将关闭！！！请注意！！");
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.activity_main, container, false);
		//zoomView = (ZoomView)view;
		
		transSetting();
		initView(view);
		initData();
		setListener();

		registerBroadCast();
		hideKeyBoard();
		setTextTypeFace();
		
		_mediaPlayer = new MediaPlayer();
		_mediaPlayer.setLooping(false);
		_mediaPlayer.setOnCompletionListener(this);
		
		SharedPreferences settings = getActivity().getPreferences(Activity.MODE_PRIVATE);
		_playOrder = settings.getInt("PlayOrder", 0);
		
		return view;
	}

	// 初始化View
	private void initView(View view)
	{
		tvOpTime = (TextView)view.findViewById(R.id.main_op_tv_timer);
		tvOpTime1 = (TextView)view.findViewById(R.id.main_op_tv_timer1);
		tvOpTime3 = (TextView)view.findViewById(R.id.main_op_tv_timer3);
		tvOpTime4 = (TextView)view.findViewById(R.id.main_op_tv_timer4);
		tvOpTime5 = (TextView)view.findViewById(R.id.main_op_tv_timer5);

		tvAnesTime = (TextView)view.findViewById(R.id.main_anes_tv_timer);
		tvAnesTime1 = (TextView)view.findViewById(R.id.main_anes_tv_timer1);
		tvAnesTime3 = (TextView)view.findViewById(R.id.main_anes_tv_timer3);
		tvAnesTime4 = (TextView)view.findViewById(R.id.main_anes_tv_timer4);
		tvAnesTime5 = (TextView)view.findViewById(R.id.main_anes_tv_timer5);

		tvCurrentTime = (TextView)view.findViewById(R.id.main_time_tv_timer);
		tvCurrentTime1 = (TextView)view.findViewById(R.id.main_time_tv_timer1);
		tvCurrentTime3 = (TextView)view.findViewById(R.id.main_time_tv_timer3);
		tvCurrentTime4 = (TextView)view.findViewById(R.id.main_time_tv_timer4);
		tvCurrentTime5 = (TextView)view.findViewById(R.id.main_time_tv_timer5);

		tvCurrentDay = (TextView)view.findViewById(R.id.main_time_tv_current);

		_dustParticle01TextView = (TextView)view.findViewById(R.id.dustParticle01TextView);
		_dustParticle02TextView = (TextView)view.findViewById(R.id.dustParticle02TextView);
		
		btnOpStart = (ImageButton)view.findViewById(R.id.main_op_ib_start);
		btnOpPause = (ImageButton)view.findViewById(R.id.main_op_ib_pasuse);
		btnOpReset = (ImageButton)view.findViewById(R.id.main_op_ib_reset);

		btnAnesStart = (ImageButton)view.findViewById(R.id.main_anes_ib_start);
		btnAnesPause = (ImageButton)view.findViewById(R.id.main_anes_ib_pasuse);
		btnAnesReset = (ImageButton)view.findViewById(R.id.main_anes_ib_reset);

		tvAirTemp = (TextView)view.findViewById(R.id.main_air_tv_temp);
		tvAirHum = (TextView)view.findViewById(R.id.main_air_tv_hum);
		btnAirTempAdd = (ImageButton)view.findViewById(R.id.main_air_ib_add);
		btnAirTempReduce = (ImageButton)view.findViewById(R.id.main_air_ib_red);
		btnAirHumAdd = (ImageButton)view.findViewById(R.id.main_hum_ib_add);
		btnAirHumReduce = (ImageButton)view.findViewById(R.id.main_hum_ib_red);

		btnAirSwitch = (Button)view.findViewById(R.id.main_air_ib_switch);
		btnWaiting = (Button)view.findViewById(R.id.main_air_ib_waiting);
		btnAirRunning = (Button)view.findViewById(R.id.main_op_ib_runing);

		etDial = (TextView)view.findViewById(R.id.main_phone_et_tel);
		etStatusFire = (TextView)view.findViewById(R.id.main_et_fire);
		etStatusPower = (TextView)view.findViewById(R.id.main_et_power);
		btnDial1 = (Button)view.findViewById(R.id.main_phone_ib_1);
		btnDial2 = (Button)view.findViewById(R.id.main_phone_ib_2);
		btnDial3 = (Button)view.findViewById(R.id.main_phone_ib_3);
		btnDial4 = (Button)view.findViewById(R.id.main_phone_ib_4);
		btnDial5 = (Button)view.findViewById(R.id.main_phone_ib_5);
		btnDial6 = (Button)view.findViewById(R.id.main_phone_ib_6);
		btnDial7 = (Button)view.findViewById(R.id.main_phone_ib_7);
		btnDial8 = (Button)view.findViewById(R.id.main_phone_ib_8);
		btnDial9 = (Button)view.findViewById(R.id.main_phone_ib_9);
		btnDial0 = (Button)view.findViewById(R.id.main_phone_ib_0);
		btnDialXing = (Button)view.findViewById(R.id.main_phone_ib_xing);
		btnDialJing = (Button)view.findViewById(R.id.main_phone_ib_jing);
		btnDialSlience = (ImageButton)view.findViewById(R.id.main_phone_ib_slince);
		btnDialDial = (ImageButton)view.findViewById(R.id.main_phone_dial);
		btnDialHandUp = (ImageButton)view.findViewById(R.id.main_phone_handup);
		seekBar = (VerticalSeekBar)view.findViewById(R.id.verticalSeekBar1);
		stopButton = (ImageButton)view.findViewById(R.id.stop_button);

		ivO2Status = (ImageView)view.findViewById(R.id.main_iv_gas_o2);
		ivCompressO2Status = (ImageView)view.findViewById(R.id.main_iv_gas_o2_conpress);
		ivNitrogenStatus = (ImageView)view.findViewById(R.id.main_iv_gas_nitogen);
		ivCarbonStatus = (ImageView)view.findViewById(R.id.main_iv_gas_carbon);
		ivLaughStatus = (ImageView)view.findViewById(R.id.main_iv_gas_laugh);
		ivNegStatus = (ImageView)view.findViewById(R.id.main_iv_gas_neg);
		ivArgonStatus = (ImageView)view.findViewById(R.id.main_iv_gas_argon);

		btnAirPressureAdd = (ImageButton)view.findViewById(R.id.main_pa_ib_add);
		btnAirPressureReduce = (ImageButton)view.findViewById(R.id.main_pa_ib_red);
		tvAirPressure = (TextView)view.findViewById(R.id.main_air_tv_pa);

		llFire = (FrameLayout)view.findViewById(R.id.main_rb_1);
		llPower = (FrameLayout)view.findViewById(R.id.main_rb_2);
		llMainControl = (FrameLayout)view.findViewById(R.id.main_rb_3);
		llLight1 = (Button)view.findViewById(R.id.main_rb_4);
		llLight2 = (Button)view.findViewById(R.id.main_rb_5);
		llWind = (Button)view.findViewById(R.id.main_rb_6);
		llLook = (Button)view.findViewById(R.id.main_rb_7);
		llShaow = (Button)view.findViewById(R.id.main_rb_8);
		llDesk = (Button)view.findViewById(R.id.main_rb_9);
		llSpare = (Button)view.findViewById(R.id.main_rb_10);

		tvMusicBack = (TextView)view.findViewById(R.id.main_tv_music_back);
		ivMusicIcon = (ImageView)view.findViewById(R.id.main_iv_music_icon);

		ivStatusOk = (ImageView)view.findViewById(R.id.radio0);
		ivStatusWaiting = (ImageView)view.findViewById(R.id.radio1);
		ivStatusError = (ImageView)view.findViewById(R.id.radio3);
		ivStatsStop = (ImageView)view.findViewById(R.id.radio4);
		ivStatusRunning = (ImageView)view.findViewById(R.id.radio5);
		ivStatusFire = (ImageView)view.findViewById(R.id.main_iv_fire);
		ivStatusPower = (ImageView)view.findViewById(R.id.main_iv_power);

		flTimer = (FrameLayout)view.findViewById(R.id.fl_timer);
		flAnesTimer = (FrameLayout)view.findViewById(R.id.fl_anes_timer);
		flDate = (FrameLayout)view.findViewById(R.id.main_time_fl);
		btnVoiceAdd = (ImageButton)view.findViewById(R.id.voice_add);
		btnVoiceReduce = (ImageButton)view.findViewById(R.id.voice_reduce);

		btnAir = (Button)view.findViewById(R.id.main_open_air);

		tvCopyRight = (TextView)view.findViewById(R.id.copyright);
		btnDialHandUp.setSelected(true);

		dataStatus = (ImageView)view.findViewById(R.id.data_status);
		
		_quickCallButton = (Button)view.findViewById(R.id.quickCallButton);
		
		_videoCallButton = (Button)view.findViewById(R.id.videoCallButton);
		if (Consts.IS_SHOW_VIDEO_CALL)
		{
			_videoCallButton.setVisibility(View.VISIBLE);
		}
		else
		{
			_videoCallButton.setVisibility(View.INVISIBLE);
		}
		
		_localMusicButton = (Button)view.findViewById(R.id.localMusicButton);
		_remoteMusicButton = (Button)view.findViewById(R.id.remoteMusicButton);
		_bluetoothMusicButton = (Button)view.findViewById(R.id.bluetoothMusicButton);
		_clockPageButton = (Button)view.findViewById(R.id.clockPageButton);
		
		airPress01TextView = (TextView)view.findViewById(R.id.air_pa_01);
		airPress02TextView = (TextView)view.findViewById(R.id.air_pa_02);
		airPress03TextView = (TextView)view.findViewById(R.id.air_pa_03);
        airPress04TextView = (TextView)view.findViewById(R.id.air_pa_04);
        airPress05TextView = (TextView)view.findViewById(R.id.air_pa_05);
        airPress06TextView = (TextView)view.findViewById(R.id.air_pa_06);
        airPress07TextView = (TextView)view.findViewById(R.id.air_pa_07);
		
		marqueeView = (MarqueeView)view.findViewById(R.id.marqueeView);
		final MarqueeFactory<TextView, String> marqueeFactory = new NoticeMF(this.getActivity());
        marqueeFactory.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() 
        {
            @Override
            public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) 
            {
            }
        });
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.setVisibility(View.INVISIBLE);
	}

	private void initData() {
		int count = PreferencesUtil.getInstance(this.getActivity()).getIntValue("OperationTimer", "count",
				0);
		long time = PreferencesUtil.getInstance(this.getActivity()).getLongValue("OperationTimer", "time",
				0);
		
		long tempTime = new Date().getTime();
		if (0 != time && -1 != time && tempTime >= time)
		{
			opTimer = (int)(count + (tempTime-time)/1000);
		}
		else
		{
			opTimer = count;
		}

		setOpDate();
		
		if (0 != time)
		{
			if (-1 == time)
			{
				opTimerPause();
			}
			else
			{
			    opTimerStart();
			}
		}
		
		int anesCount = PreferencesUtil.getInstance(this.getActivity()).getIntValue("AnesTimer", "count",
				0);
		long anesTime = PreferencesUtil.getInstance(this.getActivity()).getLongValue("AnesTimer", "time",
				0);
		anesTimerValue = PreferencesUtil.getInstance(this.getActivity()).getIntValue("AnesTimer", "countdown",
				0);
		isTikle = PreferencesUtil.getInstance(this.getActivity()).getBooleanValue("AnesTimer", "isTikle",
				false);
		
	    tempTime = new Date().getTime();
		if (0 != anesTime && -1 != anesTime && tempTime >= anesTime)
		{
			if (0 != anesTimerValue)
			{
				if (isTikle)
				{
					anesTimer = (int)(anesCount + (tempTime-anesTime)/1000);
				}
				else
				{
				    anesTimer = (int)(anesCount - (tempTime-anesTime)/1000);
				}
			}
			else
			{
			    anesTimer = (int)(anesCount + (tempTime-anesTime)/1000);
			}
		}
		else
		{
			if (0 != anesTimerValue)
			{
			    anesTimer = anesCount;
			}
			else
			{
				anesTimer = anesCount;
			}
		}
		
		setAnesDate();
		
		if (0 != anesTime)
		{
			if (-1 == anesTime)
			{
				anesTimerPause();
			}
			else
			{
				anesTimerStart();
			}
		}

		sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
		sdf1 = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
		mTimeHandler.sendEmptyMessage(1);

		initVolume();

		DisplayMetrics dm = new DisplayMetrics();
		this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		float width = dm.widthPixels * dm.density;
		float height = dm.heightPixels * dm.density;
		Log.e("DisplayMetrics",
				String.valueOf(width) + "/" + String.valueOf(height));

		airHum(Color.parseColor("#000000"), 0);
		airPress(Color.parseColor("#000000"), 0);
		airTemp(Color.parseColor("#000000"), 0);

		mDataHandler.sendEmptyMessageDelayed(1, 60000);
	}

	private void initVolume() {
		seekBar.setMax(4);
		seekBar.setProgress(currentVolume);
	}

	private void setVolume(int val) {
		SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_VOLUMN_KEY,
				val);
		_mediaPlayer.setVolume(((float)val)/4, ((float)val)/4);
	}

	private void resetOpData() {
		opTimer = 0;// 3 * 60 * 60 + 4 * 60 + 5;
		
		PreferencesUtil.getInstance(this.getActivity()).setIntValue("OperationTimer", "count",
				opTimer);
		PreferencesUtil.getInstance(this.getActivity()).setLongValue("OperationTimer", "time",
				0);
		
		setOpDate();
	}

	private void setOpDate() {
		String s = timerStr();
		tvOpTime.setText(s.substring(0, 2));
		tvOpTime3.setText(s.substring(3, 5));
		tvOpTime5.setText(s.substring(6, 8));
		
		try
		{
		    MainActivity.this.getActivity().sendBroadcast(new Intent("com.keruiyun.saike.clock")
				.putExtra("command", 1).putExtra("value", s));
		}
		catch (Exception ex)
		{
		}
	}

	private String timerStr() {
		return getTimeStr(opTimer);
	}

	private void resetAnesData() {
		anesTimer = anesTimerValue;
		
		PreferencesUtil.getInstance(this.getActivity()).setIntValue("AnesTimer", "count",
				anesTimer);
		PreferencesUtil.getInstance(this.getActivity()).setLongValue("AnesTimer", "time",
				0);
		
		setAnesDate();
	}

	private void setAnesDate() {
		String s = getTimeStr(anesTimer);
		tvAnesTime.setText(s.substring(0, 2));
		tvAnesTime3.setText(s.substring(3, 5));
		tvAnesTime5.setText(s.substring(6, 8));
		
		try
		{
		    MainActivity.this.getActivity().sendBroadcast(new Intent("com.keruiyun.saike.clock")
				.putExtra("command", 2).putExtra("value", s));
		}
		catch (Exception ex)
		{
		}
	}

	private void setListener() {
		btnOpStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				opTimerStart();
			}
		});
		btnOpPause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				opTimerPause();
			}
		});
		btnOpReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				opTimerReset();
			}
		});
		btnAnesStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				anesTimerStart();
			}
		});
		btnAnesPause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				anesTimerPause();
			}
		});
		btnAnesReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				anesTimerValue = 0;
				
				PreferencesUtil.getInstance(MainActivity.this.getActivity()).setIntValue("AnesTimer", "countdown",
						anesTimerValue);
				
				anesTimerReset();
			}
		});

		btnAirTempAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				airTempAdd();
			}
		});
		btnAirTempReduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				airTempReduce();
			}
		});
		btnAirHumAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				airHumAdd();
			}
		});
		btnAirHumReduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				airHumReduce();
			}
		});

		btnDial1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(1);
			}
		});
		btnDial2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(2);
			}
		});
		btnDial3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(3);
			}
		});
		btnDial4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(4);
			}
		});
		btnDial5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(5);
			}
		});
		btnDial6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(6);
			}
		});
		btnDial7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(7);
			}
		});
		btnDial8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(8);
			}
		});
		btnDial9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(9);
			}
		});
		btnDial0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(0);
			}
		});
		btnDialXing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(101);
			}
		});
		btnDialJing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial(102);
			}
		});
		btnDialSlience.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialSlience();
			}
		});
		btnDialDial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dial();
			}
		});
		btnDialHandUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				handUp();
			}
		});

		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				Log.e("音量", String.valueOf(arg1));
				currentVolume = arg1;
				setVolume(arg1);
			}
		});

		btnAirSwitch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				airSwitch();
			}
		});
		btnWaiting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				airWaiting();
			}
		});
		btnAirRunning.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				airRunning();
			}
		});

		btnAirPressureAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				airPressureAdd();
			}
		});

		btnAirPressureReduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				airPressureReduce();
			}
		});

		llFire.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fire();
			}
		});
		llPower.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				power();
			}
		});
		llMainControl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mainControl();
			}
		});
		llLight1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				light1();
			}
		});
		llLight2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				light2();
			}
		});
		llWind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				wind();
			}
		});
		llLook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				look();
			}
		});
		llShaow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				shaow();
			}
		});
		llDesk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				desk();
			}
		});
		llSpare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				spare();
			}
		});

		flTimer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				anesTimerDialog();
			}
		});
		
		flAnesTimer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				anesTimerDialog();
			}
		});

		btnVoiceAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				voiceAdd();
			}
		});

		btnVoiceReduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				voiceReduce();
			}
		});

		btnAir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//startActivity(new Intent(F_Main.this, DialogFragment_Air.class));
				
				MainActivity.this.getActivity().sendBroadcast(new Intent("com.keruiyun.saike.gotoairpage"));
			}
		});

		flDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dateDialog();
			}
		});
		
		ivMusicIcon.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				if (_localMusicButton.isSelected())
				{
				    showMusicMenu();
				}
			}
		});
		
		stopButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				stopMusic();
			}
		});
		
		_quickCallButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				QuickCallFragment fragment = new QuickCallFragment(MainActivity.this);
				fragment.show(MainActivity.this.getActivity().getFragmentManager(), "QuickCallFragment");
			}
		});
		
		_videoCallButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				VideoCallFragment fragment = new VideoCallFragment(MainActivity.this);
				fragment.show(MainActivity.this.getActivity().getFragmentManager(), "QuickCallFragment");
			}
		});
		
		_localMusicButton.setSelected(true);
		_localMusicButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				_localMusicButton.setSelected(true);
				_remoteMusicButton.setSelected(false);
				_bluetoothMusicButton.setSelected(false);
				
				tvMusicBack.setText("背景音乐 MUSIC");
				ivMusicIcon.setVisibility(View.VISIBLE);
				
				SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_MUSIC_KEY, 0);
			}
		});
		
		_remoteMusicButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				_localMusicButton.setSelected(false);
				_remoteMusicButton.setSelected(true);
				_bluetoothMusicButton.setSelected(false);
				
				if (1 == musicBack || 2 == musicBack)
				{
					tvMusicBack.setText("背景音乐 MUSIC");
					ivMusicIcon.setVisibility(View.VISIBLE);
				} 
		    	else
			    {
					tvMusicBack.setText("");
					ivMusicIcon.setVisibility(View.INVISIBLE);
				}
				
				SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_MUSIC_KEY, 1);
			}
		});
		
		_bluetoothMusicButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				_localMusicButton.setSelected(false);
				_remoteMusicButton.setSelected(false);
				_bluetoothMusicButton.setSelected(true);
				
				if (1 == musicBack || 2 == musicBack)
				{
					tvMusicBack.setText("背景音乐 MUSIC");
					ivMusicIcon.setVisibility(View.VISIBLE);
				} 
				else
				{
					tvMusicBack.setText("");
					ivMusicIcon.setVisibility(View.INVISIBLE);
				}
				
				SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_MUSIC_KEY, 2);
			}
		});
		
		_clockPageButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(MainActivity.this.getActivity(), ClockActivity.class);
				startActivity(intent);
			}
		});
	}

	// 手术定时器开始
	private void opTimerStart() {
		if (!isOpStart) {
			mOpHandler.sendMessageDelayed(
					mOpHandler.obtainMessage(1, opTimer, 0), 0);
		}
		isOpStart = true;

		btnOpStart.setSelected(true);
		btnOpPause.setSelected(false);
		btnOpReset.setSelected(false);
		
		PreferencesUtil.getInstance(this.getActivity()).setIntValue("OperationTimer", "count",
				opTimer);
		PreferencesUtil.getInstance(this.getActivity()).setLongValue("OperationTimer", "time",
				new Date().getTime());
	}

	// 手术定时器暂停
	private void opTimerPause() {
		isOpStart = false;
		mOpHandler.removeCallbacksAndMessages(null);

		PreferencesUtil.getInstance(this.getActivity()).setIntValue("OperationTimer", "count",
				opTimer);
		PreferencesUtil.getInstance(this.getActivity()).setLongValue("OperationTimer", "time",
				-1);
		
		btnOpStart.setSelected(false);
		btnOpPause.setSelected(true);
		btnOpReset.setSelected(false);
	}

	// 手术定时器复位
	private void opTimerReset() {
		isOpStart = false;
		mOpHandler.removeCallbacksAndMessages(null);
		resetOpData();

		btnOpStart.setSelected(false);
		btnOpPause.setSelected(false);
		btnOpReset.setSelected(false);
	}

	// 麻醉定时器开始
	private void anesTimerStart() {
		if (!isAnesStart) {
			if (isTikle) {
				mAnesHandler.removeCallbacksAndMessages(null);
				mAnesHandler.sendMessageDelayed(
						mAnesHandler.obtainMessage(3, anesTimer, 0), 0);

				mAnesTwinkleHandler.removeCallbacksAndMessages(null);
				mAnesTwinkleHandler.sendMessageDelayed(
						mAnesTwinkleHandler.obtainMessage(3, anesTimer, 0), 0);
			} else {
				mAnesHandler.sendMessageDelayed(
						mAnesHandler.obtainMessage(1, anesTimer, 0), 0);
			}
		}
		isAnesStart = true;

		btnAnesStart.setSelected(true);
		btnAnesPause.setSelected(false);
		btnAnesReset.setSelected(false);
		
		PreferencesUtil.getInstance(this.getActivity()).setIntValue("AnesTimer", "count",
				anesTimer);
		PreferencesUtil.getInstance(this.getActivity()).setLongValue("AnesTimer", "time",
				new Date().getTime());
	}

	// 麻醉定时器暂停
	private void anesTimerPause() {
		mAnesHandler.removeCallbacksAndMessages(null);
		mAnesTwinkleHandler.removeCallbacksAndMessages(null);
		tvAnesTime.setVisibility(View.VISIBLE);
		tvAnesTime3.setVisibility(View.VISIBLE);
		tvAnesTime5.setVisibility(View.VISIBLE);

		isAnesStart = false;

		btnAnesStart.setSelected(false);
		btnAnesPause.setSelected(true);
		btnAnesReset.setSelected(false);
		
		PreferencesUtil.getInstance(this.getActivity()).setIntValue("AnesTimer", "count",
				anesTimer);
		PreferencesUtil.getInstance(this.getActivity()).setLongValue("AnesTimer", "time",
				-1);
	}

	// 麻醉定时器复位
	private void anesTimerReset() {
		isAnesStart = false;
		mAnesHandler.removeCallbacksAndMessages(null);
		mAnesTwinkleHandler.removeCallbacksAndMessages(null);

		isTikle = false;
		PreferencesUtil.getInstance(this.getActivity()).setBooleanValue("AnesTimer", "isTikle",
				isTikle);
		
		tvAnesTime.setVisibility(View.VISIBLE);
		tvAnesTime3.setVisibility(View.VISIBLE);
		tvAnesTime5.setVisibility(View.VISIBLE);

		resetAnesData();

		btnAnesStart.setSelected(false);
		btnAnesPause.setSelected(false);
		btnAnesReset.setSelected(false);
	}

	// 空调温度增加
	private void airTempAdd() {
		if (tempSetting < 50) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_SET_TEMPERATURE, tempSetting + 1);

			airTemp(Color.parseColor("#3f48cc"), ++tempSetting);
			isTempSetting = true;
			mAnesHandlerSetting.removeMessages(1);
			mAnesHandlerSetting.sendEmptyMessageDelayed(1, 5000);
		}
	}

	// 空调温度减少
	private void airTempReduce() {
		if (tempSetting > 0) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_SET_TEMPERATURE, tempSetting - 1);

			airTemp(Color.parseColor("#3f48cc"), --tempSetting);
			isTempSetting = true;
			mAnesHandlerSetting.removeMessages(1);
			mAnesHandlerSetting.sendEmptyMessageDelayed(1, 5000);
		}
	}

	// 空调湿度增加
	private void airHumAdd() {
		if (humSetting < 99) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_SET_HUMIDITY, humSetting + 1);

			airHum(Color.parseColor("#3f48cc"), ++humSetting);
			isHumSetting = true;
			mAnesHandlerSetting.removeMessages(3);
			mAnesHandlerSetting.sendEmptyMessageDelayed(3, 5000);
		}
	}

	// 空调湿度减少
	private void airHumReduce() {
		if (humSetting > 0) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_SET_HUMIDITY, humSetting - 1);

			airHum(Color.parseColor("#3f48cc"), --humSetting);
			isHumSetting = true;
			mAnesHandlerSetting.removeMessages(3);
			mAnesHandlerSetting.sendEmptyMessageDelayed(3, 5000);
		}
	}

	private void dial() {
		isDialing = true;
		SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_HANDFREE_KEY,
				1);

		btnDialDial.setSelected(true);
		btnDialHandUp.setSelected(false);
		etDial.setBackgroundResource(R.drawable.et_login_bg);
		
		pauseMusic();
	}

	private void handUp() {
		isDialing = false;
		etDial.setText("");
		SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_HANDFREE_KEY,
				0);

		btnDialDial.setSelected(false);
		btnDialHandUp.setSelected(true);
		etDial.setBackgroundResource(0);
		
		playMusic();
	}

	private void dialSlience() {
		if (musicBack == 1) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_MUSIC_KEY, 0);
		} else if (musicBack == 0) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_MUSIC_KEY, 1);
		}
	}

	private void dial(int i) {
		if (!isDialing) {
			return;
		}
		switch (i) {
		case 0:
			etDial.setText(etDial.getText() + String.valueOf(0));
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 0);
			break;
		case 1:
			etDial.setText(etDial.getText() + String.valueOf(1));
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 1);
			break;
		case 2:
			etDial.setText(etDial.getText() + String.valueOf(2));
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 2);
			break;
		case 3:
			etDial.setText(etDial.getText() + String.valueOf(3));
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 3);
			break;
		case 4:
			etDial.setText(etDial.getText() + String.valueOf(4));
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 4);
			break;
		case 5:
			etDial.setText(etDial.getText() + String.valueOf(5));
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 5);
			break;
		case 6:
			etDial.setText(etDial.getText() + String.valueOf(6));
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 6);
			break;
		case 7:
			etDial.setText(etDial.getText() + String.valueOf(7));
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 7);
			break;
		case 8:
			etDial.setText(etDial.getText() + String.valueOf(8));
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 8);
			break;
		case 9:
			etDial.setText(etDial.getText() + String.valueOf(9));
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 9);
			break;
		case 101:
			// *
			etDial.setText(etDial.getText() + "*");
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 10);
			break;
		case 102:
			// #
			etDial.setText(etDial.getText() + "#");
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PHONE_KEY, 11);
			break;
		default:
			break;
		}
	}

	private Handler mOpHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				long time = new Date().getTime();
				long oldTime = PreferencesUtil.getInstance(MainActivity.this.getActivity()).getLongValue("OperationTimer", "time", 0);
				int count = PreferencesUtil.getInstance(MainActivity.this.getActivity()).getIntValue("OperationTimer", "count", 0);
						
				opTimer = count + (int)((time - oldTime) / 1000);
				
				setOpDate();
				
				mOpHandler.sendMessageDelayed(
						mOpHandler.obtainMessage(1, 0, 0), 500);
				break;

			default:
				break;
			}
			return false;
		}
	});

	private Handler mAnesHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (anesTimerValue > 0) {
					if (anesTimer > 0) {
						long time = new Date().getTime();
						long oldTime = PreferencesUtil.getInstance(MainActivity.this.getActivity()).getLongValue("AnesTimer", "time", 0);
						int count = PreferencesUtil.getInstance(MainActivity.this.getActivity()).getIntValue("AnesTimer", "count", 0);
								
						anesTimer = count - (int)((time - oldTime) / 1000);
						
						mAnesHandler.sendMessageDelayed(
								mAnesHandler.obtainMessage(1, 0, 0), 500);
					} else {
						isTikle = true;
						PreferencesUtil.getInstance(MainActivity.this.getActivity()).setBooleanValue("AnesTimer", "isTikle",
								isTikle);
						
						mAnesTwinkleHandler.removeCallbacksAndMessages(null);
						
						PreferencesUtil.getInstance(MainActivity.this.getActivity()).setIntValue("AnesTimer", "count",
								0);
						PreferencesUtil.getInstance(MainActivity.this.getActivity()).setLongValue("AnesTimer", "time",
								new Date().getTime());
						
						mAnesHandler.sendEmptyMessage(3);
						mAnesTwinkleHandler.sendEmptyMessageDelayed(3, 500);
					}
				} else {
					long time = new Date().getTime();
					long oldTime = PreferencesUtil.getInstance(MainActivity.this.getActivity()).getLongValue("AnesTimer", "time", 0);
					int count = PreferencesUtil.getInstance(MainActivity.this.getActivity()).getIntValue("AnesTimer", "count", 0);
							
					anesTimer = count + (int)((time - oldTime) / 1000);
					
					mAnesHandler.sendMessageDelayed(
							mAnesHandler.obtainMessage(1, 0, 0), 500);

				}
				
				setAnesDate();
				
				break;
			case 3:
				long time = new Date().getTime();
				long oldTime = PreferencesUtil.getInstance(MainActivity.this.getActivity()).getLongValue("AnesTimer", "time", 0);
				int count = PreferencesUtil.getInstance(MainActivity.this.getActivity()).getIntValue("AnesTimer", "count", 0);
						
				anesTimer = count + (int)((time - oldTime) / 1000);
				
				mAnesHandler.sendMessageDelayed(
						mAnesHandler.obtainMessage(3, 0, 0), 500);
				break;
			default:
				break;
			}
			return false;
		}
	});

	private Handler mAnesTwinkleHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			setAnesDate();
			switch (msg.what) {
			case 1:
				tvAnesTime.setVisibility(View.INVISIBLE);
				mAnesTwinkleHandler.sendEmptyMessageDelayed(3, 500);
				break;
			case 3:
				tvAnesTime.setVisibility(View.VISIBLE);
				mAnesTwinkleHandler.sendEmptyMessageDelayed(1, 500);
				break;
			default:
				break;
			}
			return false;
		}
	});

	private Handler mTimeHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (1 == MainApplication.getInstance().enabled)
			{
				tvCurrentTime.setText(String.format("%02d", MainApplication.getInstance().hour));
				tvCurrentTime3.setText(String.format("%02d", MainApplication.getInstance().minute));
				tvCurrentTime5.setText(String.format("%02d", MainApplication.getInstance().second));
				
				tvCurrentDay.setText(String.format("%04d年%02d月%02d日",
						MainApplication.getInstance().year,
						MainApplication.getInstance().month,
						MainApplication.getInstance().day));
			}
			else
			{
				String s = sdf.format(new Date());
				tvCurrentTime.setText(s.substring(0, 2));
				tvCurrentTime3.setText(s.substring(3, 5));
				tvCurrentTime5.setText(s.substring(6, 8));
				
				tvCurrentDay.setText(sdf1.format(new Date()));
			}
			
			tvCopyRight.setText("www.keruiyun.com 测试版本 "
					+ sdf1.format(new Date()));
			mTimeHandler.sendMessageDelayed(mTimeHandler.obtainMessage(), 1000);
			
			try
			{
			    MainActivity.this.getActivity().sendBroadcast(new Intent("com.keruiyun.saike.clock")
					.putExtra("command", 3).putExtra("value", tvCurrentDay.getText()));
			}
			catch (Exception ex)
			{
			}
			
			return false;
		}
	});

	// 空调开关
	private void airSwitch() {
		if (isOpSwitchOpen == 0) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_POWER_KEY, 1);
		} else if (isOpSwitchOpen == 1) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_POWER_KEY, 0);
		}
		isOpSwitchOpen = (isOpSwitchOpen == 0) ? 1 : 0;
		opSwitchChange();
	}

	// 空调值班
	private void airWaiting() {
		if (isOpWaiting == 0) {
			SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_DUTY_KEY,
					1);
		} else if (isOpWaiting == 1) {
			SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_DUTY_KEY,
					0);
		}
		isOpWaiting = (isOpWaiting == 0) ? 1 : 0;
		opWaitingChange();
	}

	// 空调运行
	private void airRunning() {
		if (isPressRunning == 0) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PRESSURE_KEY, 1);
		} else if (isPressRunning == 1) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_PRESSURE_KEY, 0);
		}
		isPressRunning = (isPressRunning == 0) ? 1 : 0;
		opPressChange();
	}

	private void hideKeyBoard() {
		InputMethodManager imm = (InputMethodManager)this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etDial.getWindowToken(), 0);
	}

	// 气压增加
	private void airPressureAdd() {
		if (pressSetting < 100) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_SET_PRESSURE, pressSetting + 1);

			airPress(Color.parseColor("#3f48cc"), ++pressSetting);
			isPressSetting = true;
			mAnesHandlerSetting.removeMessages(4);
			mAnesHandlerSetting.sendEmptyMessageDelayed(4, 5000);
		}
	}

	// 气压减少
	private void airPressureReduce() {
		if (pressSetting > -100) {
			SerialSaunaThread.writeCmdQueue(1,
					SerialSaunaThread.ADDR_SET_PRESSURE, pressSetting - 1);

			airPress(Color.parseColor("#3f48cc"), --pressSetting);
			isPressSetting = true;
			mAnesHandlerSetting.removeMessages(4);
			mAnesHandlerSetting.sendEmptyMessageDelayed(4, 5000);
		}
	}

	private void registerBroadCast() {
		broadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				dataStatus();
				int addr = arg1.getIntExtra("addr", -1);
				
				if (SerialSaunaThread.ADDR_DUST_PARTICLE_01 == addr)
				{
					long data1 = arg1.getLongExtra("data1", 0);
					long data2 = arg1.getLongExtra("data2", 0);
					
					_dustParticle01TextView.setText(String.valueOf(data1));
					_dustParticle02TextView.setText(String.valueOf(data2));
				}
				else
				{
					short data = arg1.getShortExtra("data", (short) -1);
					switch (addr) {
					case SerialSaunaThread.ADDR_AIR_TEMPERATURE:
						if (!isTempSetting) {
							airTemp(Color.parseColor("#000000"), data);
	
							Log.e("回风温度：", String.valueOf(data));
						}
						break;
					case SerialSaunaThread.ADDR_AIR_HUMIDITY:
						if (!isHumSetting) {
							airHum(Color.parseColor("#000000"), data);
							Log.e("回风湿度：", String.valueOf(data));
						}
						break;
					case SerialSaunaThread.ADDR_ROOM_PRESSURE:
						if (!isPressSetting) {
							airPress(Color.parseColor("#000000"), data);
							Log.e("回风压差：", String.valueOf(data));
						}
						break;
					case SerialSaunaThread.ADDR_SET_TEMPERATURE:
						tempSetting = data;
						if (isTempSetting) {
							airTemp(Color.parseColor("#3f48cc"), data);
							Log.e("温度设定：", String.valueOf(data));
						}
						break;
					case SerialSaunaThread.ADDR_SET_HUMIDITY:
						humSetting = data;
						if (isHumSetting) {
							airHum(Color.parseColor("#3f48cc"), data);
							Log.e("湿度设定：", String.valueOf(data));
						}
						break;
					case SerialSaunaThread.ADDR_SET_PRESSURE:
						pressSetting = data;
						if (isPressSetting) {
							airPress(Color.parseColor("#3f48cc"), data);
							Log.e("压差设定：", String.valueOf(data));
						}
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
					case SerialSaunaThread.ADDR_CONTROL_KEY:
						isLight1 = data & 0x01;
						isLight2 = (data >> 1) & 0x01;
						isWind = (data >> 2) & 0x01;
						isLook = (data >> 3) & 0x01;
						isShaow = (data >> 4) & 0x01;
						isDesk = (data >> 5) & 0x01;
						isSpare = (data >> 6) & 0x01;
						light1Change();
						light2Change();
						windChange();
						lookChange();
						shaowChange();
						deskChange();
						spareChange();
						break;
					case SerialSaunaThread.ADDR_AIR_ALARM:
						// 氧气
						int isO2High = data & 0x01;
						int isO2Low = (data >> 1) & 0x01;
						o2Status(isO2High, isO2Low);
						// 氮气
						int isNitrogenHigh = (data >> 2) & 0x01;
						int isNitrogenLow = (data >> 3) & 0x01;
						nitrogenStatus(isNitrogenHigh, isNitrogenLow);
						// 笑气
						int isLaughHigh = (data >> 4) & 0x01;
						int isLaughLow = (data >> 5) & 0x01;
						laughStatus(isLaughHigh, isLaughLow);
						// 氩气
						int isArgonHigh = (data >> 6) & 0x01;
						int isArgonLow = (data >> 7) & 0x01;
						argonStatus(isArgonHigh, isArgonLow);
						// 压缩空气
						int isCompressO2High = (data >> 8) & 0x01;
						int isCompressO2Low = (data >> 9) & 0x01;
						compressStatus(isCompressO2High, isCompressO2Low);
						break;
					case SerialSaunaThread.ADDR_OTHER_ALARM:
						int isCarbonHigh = data & 0x01;
						int isCarbonLow = (data >> 1) & 0x01;
						int isNegHigh = (data >> 2) & 0x01;
						int isNegLow = (data >> 3) & 0x01;
						carbonStatus(isCarbonHigh, isCarbonLow);
						negStatus(isNegHigh, isNegLow);
						break;
					case SerialSaunaThread.ADDR_VOLUMN_KEY:
						currentVolume = data;
						if (data != seekBar.getProgress()) {
							seekBar.setProgress(currentVolume);
						}
						break;
					case SerialSaunaThread.ADDR_MUSIC_KEY:
						musicBack = data;
						musicBackChange();
						break;
					case SerialSaunaThread.ADDR_CALL_KEY:
						if (data == 0) {
							// handUpChange();
						}
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
						
						int alarmFire = (data >> 12) & 0x01;
						alarmFire(alarmFire);
						
						int alarmPower = (data >> 13) & 0x01;
						alarmPower(alarmPower);
						
						break;
					case SerialSaunaThread.ADDR_LIGHT_OFF_TINT:
						if (0 == data)
						{
							marqueeView.setVisibility(View.INVISIBLE);
							marqueeView.stopFlipping();
						}
						else
						{
							marqueeView.setVisibility(View.VISIBLE);
							marqueeView.startFlipping();
						}
						break;
						
					case SerialSaunaThread.ADDR_AIR_PRESS_01:
						airPress01TextView.setText(String.format("%.2f", data/100.0f));
						break;
						
					case SerialSaunaThread.ADDR_AIR_PRESS_02:
						airPress02TextView.setText(String.format("%.2f", data/100.0f));
						break;
						
					case SerialSaunaThread.ADDR_AIR_PRESS_03:
						airPress03TextView.setText(String.format("%.2f", data/100.0f));
						break;
						
					case SerialSaunaThread.ADDR_AIR_PRESS_04:
						airPress04TextView.setText(String.format("%.2f", data/100.0f));
						break;
						
					case SerialSaunaThread.ADDR_AIR_PRESS_05:
						airPress05TextView.setText(String.format("%.2f", data/100.0f));
						break;
						
					case SerialSaunaThread.ADDR_AIR_PRESS_06:
						airPress06TextView.setText(String.format("%.2f", data/100.0f));
						break;
						
					case SerialSaunaThread.ADDR_AIR_PRESS_07:
						airPress07TextView.setText(String.valueOf(data));
						break;
						
					default:
						break;
					}
				}
			}
		};

		IntentFilter intentFilter = new IntentFilter("com.keruiyun.saike.order");
		this.getActivity().registerReceiver(broadcastReceiver, intentFilter);
	}

	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != broadcastReceiver)
		{
			try 
			{
				this.getActivity().unregisterReceiver(broadcastReceiver);
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	private void opSwitchChange() {
		if (isOpSwitchOpen == 0) {
			btnAirSwitch.setSelected(false);
		} else if (isOpSwitchOpen == 1) {
			btnAirSwitch.setSelected(true);
		}
	}

	private void opWaitingChange() {
		if (isOpWaiting == 0) {
			btnWaiting.setSelected(false);
		} else if (isOpWaiting == 1) {
			btnWaiting.setSelected(true);
		}
	}

	private void opPressChange() {
		if (isPressRunning == 0) {
			btnAirRunning.setSelected(false);
			ivStatusRunning.setImageResource(R.drawable.st_off);
		} else if (isPressRunning == 1) {
			btnAirRunning.setSelected(true);
			ivStatusRunning.setImageResource(R.drawable.st_on);
		}
	}

	private void fire() {

	}

	private void power() {

	}

	private void mainControl() {

	}

	private void light1() {
		int val = 0;

		val = val | (isLight2 << 1);
		val = val | (isWind << 2);
		val = val | (isLook << 3);
		val = val | (isShaow << 4);
		val = val | (isDesk << 5);
		val = val | (isSpare << 6);

		int temp = isLight1 == 0 ? 1 : 0;
		val = val | temp;

		SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
				val);

		isLight1 = temp;
		light1Change();
	}

	private void light2() {
		int val = 0;

		val = val | (isLight1);
		val = val | (isWind << 2);
		val = val | (isLook << 3);
		val = val | (isShaow << 4);
		val = val | (isDesk << 5);
		val = val | (isSpare << 6);

		int temp = isLight2 == 0 ? 1 : 0;
		val = val | (temp << 1);

		SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
				val);

		isLight2 = temp;
		light2Change();
	}

	private void wind() {
		int val = 0;

		val = val | (isLight1);
		val = val | (isLight2 << 1);
		val = val | (isLook << 3);
		val = val | (isShaow << 4);
		val = val | (isDesk << 5);
		val = val | (isSpare << 6);

		int temp = isWind == 0 ? 1 : 0;
		val = val | (temp << 2);

		SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
				val);

		isWind = temp;
		windChange();
	}

	private void look() {
		int val = 0;

		val = val | (isLight1);
		val = val | (isLight2 << 1);
		val = val | (isWind << 2);
		val = val | (isShaow << 4);
		val = val | (isDesk << 5);
		val = val | (isSpare << 6);

		int temp = isLook == 0 ? 1 : 0;
		val = val | (temp << 3);

		SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
				val);

		isLook = temp;
		lookChange();
	}

	private void shaow() {
		int val = 0;

		val = val | (isLight1);
		val = val | (isLight2 << 1);
		val = val | (isWind << 2);
		val = val | (isLook << 3);
		val = val | (isDesk << 5);
		val = val | (isSpare << 6);

		int temp = isShaow == 0 ? 1 : 0;
		val = val | (temp << 4);

		SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
				val);

		isShaow = temp;
		shaowChange();
	}

	private void desk() {
		int val = 0;

		val = val | (isLight1);
		val = val | (isLight2 << 1);
		val = val | (isWind << 2);
		val = val | (isLook << 3);
		val = val | (isShaow << 4);
		val = val | (isSpare << 6);

		int temp = isDesk == 0 ? 1 : 0;
		val = val | (temp << 5);

		SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
				val);

		isDesk = temp;
		deskChange();
	}

	private void spare() {
		int val = 0;

		val = val | (isLight1);
		val = val | (isLight2 << 1);
		val = val | (isWind << 2);
		val = val | (isLook << 3);
		val = val | (isShaow << 4);
		val = val | (isDesk << 5);

		int temp = isSpare == 0 ? 1 : 0;
		val = val | (temp << 6);

		SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
				val);

		isSpare = temp;
		spareChange();
	}

	// 照明1
	private void light1Change() {
		if (isLight1 == 0) {
			// ivLight1.setImageResource(R.drawable.c4_normal);
			llLight1.setSelected(false);
		} else if (isLight1 == 1) {
			// ivLight1.setImageResource(R.drawable.c4);
			llLight1.setSelected(true);
		}
	}

	// 照明2
	private void light2Change() {
		if (isLight2 == 0) {
			// ivLight2.setImageResource(R.drawable.c4_normal);
			llLight2.setSelected(false);
		} else if (isLight2 == 1) {
			// ivLight2.setImageResource(R.drawable.c4);
			llLight2.setSelected(true);
		}
	}

	// 排风机
	private void windChange() {
		if (isWind == 0) {
			// ivWind.setImageResource(R.drawable.c5_normal);
			llWind.setSelected(false);
		} else if (isWind == 1) {
			// ivWind.setImageResource(R.drawable.c5);
			llWind.setSelected(true);
		}
	}

	// 观片灯
	private void lookChange() {
		if (isLook == 0) {
			// ivLook.setImageResource(R.drawable.c6_normal);
			llLook.setSelected(false);
		} else if (isLook == 1) {
			// ivLook.setImageResource(R.drawable.c6);
			llLook.setSelected(true);
		}
	}

	// 无影灯灯
	private void shaowChange() {
		if (isShaow == 0) {
			// ivShaow.setImageResource(R.drawable.c6_normal);
			llShaow.setSelected(false);
		} else if (isShaow == 1) {
			// ivShaow.setImageResource(R.drawable.c6);
			llShaow.setSelected(true);
		}
	}

	// 书写台
	private void deskChange() {
		if (isDesk == 0) {
			// ivDesk.setImageResource(R.drawable.c6_normal);
			llDesk.setSelected(false);
		} else if (isDesk == 1) {
			// ivDesk.setImageResource(R.drawable.c6);
			llDesk.setSelected(true);
		}
	}

	private void spareChange() {
		if (isSpare == 0) {
			// ivSpare.setImageResource(R.drawable.c7_normal);
			llSpare.setSelected(false);
		} else if (isSpare == 1) {
			// ivSpare.setImageResource(R.drawable.c7);
			llSpare.setSelected(true);
		}
	}

	private void o2Status(int high, int low) {
		ivO2Status.setImageResource(R.drawable.gas_yello);
		if (high == 1) {
			ivO2Status.setImageResource(R.drawable.gas_red);
		}
		if (low == 1) {
			ivO2Status.setImageResource(R.drawable.gas_alarm);
		}
	}

	private void nitrogenStatus(int high, int low) {
		ivNitrogenStatus.setImageResource(R.drawable.gas_yello);
		if (high == 1) {
			ivNitrogenStatus.setImageResource(R.drawable.gas_red);
		}
		if (low == 1) {
			ivNitrogenStatus.setImageResource(R.drawable.gas_alarm);
		}
	}

	private void laughStatus(int high, int low) {
		ivLaughStatus.setImageResource(R.drawable.gas_yello);
		if (high == 1) {
			ivLaughStatus.setImageResource(R.drawable.gas_red);
		}
		if (low == 1) {
			ivLaughStatus.setImageResource(R.drawable.gas_alarm);
		}
	}

	private void argonStatus(int high, int low) {
		ivArgonStatus.setImageResource(R.drawable.gas_yello);
		if (high == 1) {
			ivArgonStatus.setImageResource(R.drawable.gas_red);
		}
		if (low == 1) {
			ivArgonStatus.setImageResource(R.drawable.gas_alarm);
		}
	}

	private void compressStatus(int high, int low) {
		ivCompressO2Status.setImageResource(R.drawable.gas_yello);
		if (high == 1) {
			ivCompressO2Status.setImageResource(R.drawable.gas_red);
		}
		if (low == 1) {
			ivCompressO2Status.setImageResource(R.drawable.gas_alarm);
		}
	}

	private void carbonStatus(int high, int low) {
		ivCarbonStatus.setImageResource(R.drawable.gas_yello);
		if (high == 1) {
			ivCarbonStatus.setImageResource(R.drawable.gas_red);
		}
		if (low == 1) {
			ivCarbonStatus.setImageResource(R.drawable.gas_alarm);
		}
	}

	private void negStatus(int high, int low) {
		ivNegStatus.setImageResource(R.drawable.gas_yello);
		if (high == 1) {
			ivNegStatus.setImageResource(R.drawable.gas_red);
		}
		if (low == 1) {
			ivNegStatus.setImageResource(R.drawable.gas_alarm);
		}
	}

	private void musicBackChange() 
	{
		if (_remoteMusicButton.isSelected() || _bluetoothMusicButton.isSelected())
		{
			if (1 == musicBack || 2 == musicBack)
			{
				tvMusicBack.setText("背景音乐 MUSIC");
				ivMusicIcon.setVisibility(View.VISIBLE);
			} 
			else
			{
				tvMusicBack.setText("");
				ivMusicIcon.setVisibility(View.INVISIBLE);
			}
		}
	}

	private void handUpChange() {
		etDial.setText("");
		etDial.setBackgroundResource(0);
	}

	private void transSetting() {
		// SerialSaunaThread
		// .writeCmdQueue(1, SerialSaunaThread.ADDR_HOST_SLAVE, 1);
	}

	private void anesTimerDialog() 
	{
		Intent dialog = new Intent(this.getActivity(), TimeDialogActivity.class); 
		dialog.putExtra("OpDate", timerStr());
		dialog.putExtra("AnesDate", getTimeStr(anesTimer));
		dialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        this.getActivity().startActivity(dialog);
        
		/*
		CountDownDialogActivity._listener = this;
		Intent dialog = new Intent(this.getActivity(), CountDownDialogActivity.class);  
		dialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        this.getActivity().startActivity(dialog);  
        */
		
		/*
		final AlertDialog.Builder dialog = new AlertDialog.Builder(
				this.getActivity());
		View contentView = LayoutInflater.from(this.getActivity()).inflate(
				R.layout.dialog_anes_timer, null);
		final NumberPicker np1 = (NumberPicker) contentView
				.findViewById(R.id.numberPicker1);
		final NumberPicker np3 = (NumberPicker) contentView
				.findViewById(R.id.numberPicker3);
		final NumberPicker np4 = (NumberPicker) contentView
				.findViewById(R.id.numberPicker4);
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

		dialog.setTitle("倒计时设定");
		dialog.setView(contentView);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.dismiss();

				anesTimerValue = np1.getValue() * 60 * 60 + np3.getValue() * 60
						+ np4.getValue();
				
				PreferencesUtil.getInstance(F_Main.this.getActivity()).setIntValue("AnesTimer", "countdown",
						anesTimerValue);

				anesTimerReset();
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.create().show();
		*/
	}

	private void voiceAdd() {
		int progress = seekBar.getProgress();
		int max = seekBar.getMax();

		if (progress < max) {
			seekBar.setProgress(progress + 1);
		}
	}

	private void voiceReduce() {
		int progress = seekBar.getProgress();

		if (progress > 0) {
			seekBar.setProgress(progress - 1);
		}
	}

	private void systemRunning(int running) {
		if (1 == running) {
			ivStatusOk.setImageResource(R.drawable.st_on);
		} else {
			ivStatusOk.setImageResource(R.drawable.st_off);
		}
	}

	private void waitRunning(int running) {
		if (1 == running) {
			ivStatusWaiting.setImageResource(R.drawable.st_on);
		} else {
			ivStatusWaiting.setImageResource(R.drawable.st_off);
		}
	}

	private void alarmSystem(int running) {
		if (1 == running) {
			ivStatusError.setImageResource(R.drawable.st_error);
		} else {
			ivStatusError.setImageResource(R.drawable.st_off);
		}
	}

	private void alarm3(int running) {
		if (1 == running) {
			ivStatsStop.setImageResource(R.drawable.st_error);
		} else {
			ivStatsStop.setImageResource(R.drawable.st_off);
		}
	}

	private void alarmFire(int data)
	{
		if (1 == data) 
		{
			etStatusFire.setText("消防\n报警");
			etStatusFire.setTextColor(Color.RED);
			ivStatusFire.setImageResource(R.drawable.c1);
		} 
		else 
		{
			etStatusFire.setText("消防\n正常");
			etStatusFire.setTextColor(Color.GREEN);
			ivStatusFire.setImageResource(R.drawable.c1_normal);
		}
	}
	
	private void alarmPower(int data) 
	{
		if (1 == data) 
		{
			etStatusPower.setText("IT电源\n报警");
			etStatusPower.setTextColor(Color.RED);
			ivStatusPower.setImageResource(R.drawable.c2);
		}
		else
		{
			etStatusPower.setText("IT电源\n正常");
			etStatusPower.setTextColor(Color.GREEN);
			ivStatusPower.setImageResource(R.drawable.c2_normal);
		}
	}
	
	private Handler mAnesHandlerSetting = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				isTempSetting = false;
				break;
			case 3:
				isHumSetting = false;
				break;
			case 4:
				isPressSetting = false;
				break;
			default:
				break;
			}
			return false;
		}
	});

	private void airTemp(int color, int data) {
		String text = "";
		String rep = "";
		if (color == Color.parseColor("#000000")) {
			// if ((data / 10.0f) % 1.0 == 0) {
			// text = String.format("%d°C", (int) (data / 10.0f));
			// rep = String.format("%d", (int) (data / 10.0f));
			// } else {
			text = String.format("%.1f°C", data / 10.0f);
			rep = String.format("%.1f", data / 10.0f);
			// }
		} else {
			// text = String.format("%d°C", data);
			// rep = String.format("%d", data);
			text = String.format("%.1f°C", data * 1.0f);
			rep = String.format("%.1f", data * 1.0f);
		}

		// SpannableString spannableString = new SpannableString(text);
		// ForegroundColorSpan bgColorSpan = new ForegroundColorSpan(color);
		// spannableString.setSpan(bgColorSpan, 0, rep.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0,
		// rep.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// spannableString.setSpan(new AbsoluteSizeSpan(50), text.indexOf("°C"),
		// text.indexOf("°C") + "°C".length(),
		// Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tvAirTemp.setText(rep);
		tvAirTemp.setTextColor(color);
		// tvAirTemp.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void airHum(int color, int data) {
		String text = "";
		String rep = "";

		if (color == Color.parseColor("#000000")) {
			// if ((data / 10.0f) % 1.0 == 0) {
			// text = String.format("%dRH", (int) (data / 10.0f)) + "%";
			// rep = String.format("%d", (int) (data / 10.0f));
			// } else {
			text = String.format("%.1fRH", data / 10.0f) + "%";
			rep = String.format("%.1f", data / 10.0f);
			// }
		} else {
			// text = String.format("%dRH", data) + "%";
			// rep = String.format("%d", data);

			text = String.format("%.1fRH", data * 1.0f) + "%";
			rep = String.format("%.1f", data * 1.0f);
		}

		// SpannableString spannableString = new SpannableString(text);
		// ForegroundColorSpan bgColorSpan = new ForegroundColorSpan(color);
		// spannableString.setSpan(bgColorSpan, 0, rep.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0,
		// rep.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// spannableString.setSpan(new AbsoluteSizeSpan(50),
		// text.indexOf("RH%"),
		// text.indexOf("RH%") + "RH%".length(),
		// Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tvAirHum.setText(rep);
		tvAirHum.setTextColor(color);
		// tvAirHum.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void airPress(int color, int data) {
		String text = "";
		String rep = "";
		if (color == Color.parseColor("#000000")) {
			// if ((data / 10.0f) % 1.0 == 0) {
			// text = String.format("%dPA", (int) (data / 10.0f));
			// rep = String.format("%d", (int) (data / 10.0f));
			// } else {
			text = String.format("%.1fPA", data / 10.0f);
			rep = String.format("%.1f", data / 10.0f);
			// }
		} else {
			// text = String.format("%dPA", data);
			// rep = String.format("%d", data);

			text = String.format("%.1fPA", data * 1.0f);
			rep = String.format("%.1f", data * 1.0f);
		}

		// SpannableString spannableString = new SpannableString(text);
		// ForegroundColorSpan bgColorSpan = new ForegroundColorSpan(color);
		// spannableString.setSpan(bgColorSpan, 0, rep.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0,
		// rep.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// spannableString.setSpan(new AbsoluteSizeSpan(50), text.indexOf("PA"),
		// text.indexOf("PA") + "PA".length(),
		// Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tvAirPressure.setText(rep);
		tvAirPressure.setTextColor(color);
		// tvAirPressure.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void setTextTypeFace() {
		Typeface typeFace = Typeface.createFromAsset(this.getActivity().getAssets(),
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
		tvCurrentTime.setTypeface(typeFace);
		tvCurrentTime1.setTypeface(typeFace);
		tvCurrentTime3.setTypeface(typeFace);
		tvCurrentTime4.setTypeface(typeFace);
		tvCurrentTime5.setTypeface(typeFace);
	}

	private void dateDialog()
	{
		/*
		DateDialogActivity._listener = this;
		Intent dialog = new Intent(this.getActivity(), DateDialogActivity.class);  
		dialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        this.getActivity().startActivity(dialog);  
        */
		
		TimeSettingDialogActivity._listener = this;
		Intent dialog = new Intent(this.getActivity(), TimeSettingDialogActivity.class);  
		dialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		this.getActivity().startActivity(dialog); 
        
        /*
		final AlertDialog.Builder dialog = new AlertDialog.Builder(
				this.getActivity());
		View contentView = LayoutInflater.from(this.getActivity()).inflate(
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
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.dismiss();
				int y = dp.getYear();
				int m = dp.getMonth() + 1;
				int d = dp.getDayOfMonth();

				int h = tp.getCurrentHour();
				int mi = tp.getCurrentMinute();

				try {
					SystemDateTime.setDateTime(y, m, d, h, mi, np.getValue());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.create().show();
		*/
	}

	private Handler mDataHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dataStatus.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
			return false;
		}
	});

	private void dataStatus() {
		// TODO Auto-generated method stub
		mDataHandler.removeCallbacksAndMessages(null);
		if (dataStatus.getVisibility() != View.GONE) {
			dataStatus.setVisibility(View.GONE);
		}
		mDataHandler.sendEmptyMessageDelayed(1, 60000);
	}
	
	private void showMusicMenu() 
	{
		MenuFragment fragment = new MenuFragment(this);
		fragment.show(this.getActivity().getFragmentManager(), "MenuFragment");
	}

	@Override
	public void onMusicSelect(int[] ids, String[] titles, String[] artists,
			int position) 
	{
		if (-1 != position && position < ids.length)
		{
			if (-1 != ids[position]) 
			{
				boolean isPlaying = _mediaPlayer.isPlaying();
				
				_ids = ids;
				_titles = titles;
				_artists = artists;
				_position = position;
				
				Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + ids[position]);
	
				try
				{
					_mediaPlayer.reset();
					_mediaPlayer.setDataSource(this.getActivity(), uri);
					_mediaPlayer.prepare();
				}
				catch (Exception ex)
				{
				    ex.printStackTrace();
				}
				
				if (isPlaying)
				{
					playMusic();
					
					tvMusicBack.setText(_titles[_position] + " - " + _artists[_position]);
					stopButton.setVisibility(View.INVISIBLE);
				}
			}
		}
	}
	
	@Override
	public void onMusicPlay() 
	{
		if (-1 != _position && _position < _ids.length)
		{
			playMusic();
			
			tvMusicBack.setText(_titles[_position] + " - " + _artists[_position]);
			stopButton.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	public void onMusicStop() 
	{
		stopMusic();
	}
	
	@Override
	public void onMusicPlayOrder(int playOrder)
	{
		_playOrder = playOrder;
		
		SharedPreferences settings = getActivity().getPreferences(Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("PlayOrder", _playOrder);
		editor.commit();
	}
	
	protected void playMusic()
	{
		_mediaPlayer.start();
	}

	protected void pauseMusic() 
	{
		_mediaPlayer.pause();
	}
	
	protected void stopMusic() 
	{
		_mediaPlayer.pause();
		
		tvMusicBack.setText("背景音乐  MUSIC");
		stopButton.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onCompletion(MediaPlayer arg0)
	{
		if (1 == _playOrder)
		{
			if (-1 != _position && _position < _ids.length)
			{
		        _position = new Random().nextInt(_ids.length);
				
				if (-1 != _ids[_position]) 
				{
					Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + _ids[_position]);
		
					try
					{
						_mediaPlayer.reset();
						_mediaPlayer.setDataSource(this.getActivity(), uri);
						_mediaPlayer.prepare();
					}
					catch (Exception ex)
					{
					    ex.printStackTrace();
					}
					
					playMusic();
						
					tvMusicBack.setText(_titles[_position] + " - " + _artists[_position]);
					stopButton.setVisibility(View.INVISIBLE);
				}
			}
		}
		else if (2 == _playOrder)
		{
			if (-1 != _position && _position < _ids.length)
			{
				_position++;
				if (_position >= _ids.length)
				{
					_position = 0;
				}
				
				if (-1 != _ids[_position]) 
				{
					Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + _ids[_position]);
		
					try
					{
						_mediaPlayer.reset();
						_mediaPlayer.setDataSource(this.getActivity(), uri);
						_mediaPlayer.prepare();
					}
					catch (Exception ex)
					{
					    ex.printStackTrace();
					}
					
					playMusic();
						
					tvMusicBack.setText(_titles[_position] + " - " + _artists[_position]);
					stopButton.setVisibility(View.INVISIBLE);
				}
			}
		}
		else
		{
		    playMusic();
		}
	}
	
	protected String getTimeStr(int length) 
	{
		String str = "";
		
		int sec = length % 60;
		int minute = (length / 60) % 60;
		int hour = length / 3600;
		
		if (hour > 9)
		{
			str += String.valueOf(hour);
		} 
		else
		{
			str += ("0" + String.valueOf(hour));
		}

		if (minute > 9)
		{
			str += (":" + String.valueOf(minute));
		}
		else 
		{
			str += (":0" + String.valueOf(minute));
		}

		if (sec > 9) 
		{
			str += (":" + String.valueOf(sec));
		} 
		else 
		{
			str += (":0" + String.valueOf(sec));
		}
		
		return str;
	}

	@Override
	public void onQuickCallSelect(String number) 
	{
		if (number.length() <= 0)
		{
			return;
		}
		
		handUp();
		dial();
		
		for (int i = 0; i < number.length(); i++)
		{
			if ('0' == number.charAt(i))
			{
				dial(0);
			}
			else if ('1' == number.charAt(i))
			{
				dial(1);
			}
			else if ('2' == number.charAt(i))
			{
				dial(2);
			}
			else if ('3' == number.charAt(i))
			{
				dial(3);
			}
			else if ('4' == number.charAt(i))
			{
				dial(4);
			}
			else if ('5' == number.charAt(i))
			{
				dial(5);
			}
			else if ('6' == number.charAt(i))
			{
				dial(6);
			}
			else if ('7' == number.charAt(i))
			{
				dial(7);
			}
			else if ('8' == number.charAt(i))
			{
				dial(8);
			}
			else if ('9' == number.charAt(i))
			{
				dial(9);
			}
			else if ('*' == number.charAt(i))
			{
				dial(101);
			}
			else if ('#' == number.charAt(i))
			{
				dial(102);
			}
		}
	}

	@Override
	public void countDownDialogActivityDidSave(NumberPicker np1, NumberPicker np3, NumberPicker np4) 
	{
		anesTimerValue = np1.getValue() * 60 * 60 + np3.getValue() * 60
				+ np4.getValue();
		
		PreferencesUtil.getInstance(MainActivity.this.getActivity()).setIntValue("AnesTimer", "countdown",
				anesTimerValue);

		anesTimerReset();
	}

	@Override
	public void dateDialogActivityDidSave(DatePicker dp, TimePicker tp, NumberPicker np)
	{
		int y = dp.getYear();
		int m = dp.getMonth() + 1;
		int d = dp.getDayOfMonth();

		int h = tp.getCurrentHour();
		int mi = tp.getCurrentMinute();

		try {
			SystemDateTime.setDateTime(y, m, d, h, mi, np.getValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void timeSettingDialogActivityDidSave(DatePicker dp, NumberPicker np1, NumberPicker np3, NumberPicker np4) {
		// TODO Auto-generated method stub
		try {
			Calendar c = Calendar.getInstance();
			
			int y = dp.getYear();
			int m = dp.getMonth() + 1;
			int d = dp.getDayOfMonth();
			
			SystemDateTime.setDateTime(y, m, d, np1.getValue(), np3.getValue(), np4.getValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onVideoCallSelect(String name, String number)
	{
		PeerModel peer = new PeerModel();
		peer.name = name;
		peer.ipAddress = number;
		
		ChatFragment fragment = new ChatFragment(peer, false);
		fragment.show(this.getActivity().getSupportFragmentManager(), "ChatFragment");
	}
	
}
