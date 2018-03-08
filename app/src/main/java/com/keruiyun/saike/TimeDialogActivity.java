package com.keruiyun.saike;
import com.keruiyun.saike.util.ZoomView;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TimeDialogActivity extends BaseActivity 
{
	private TextView tvOpTime, tvOpTime1, tvOpTime3, tvOpTime4, tvOpTime5,
	                 tvAnesTime, tvAnesTime1, tvAnesTime3, tvAnesTime4, tvAnesTime5;
	private Button _okButton;
	private Button _cancelButton;
	private LinearLayout _layout;
	private ZoomView _zoomView;
	
	private BroadcastReceiver broadcastReceiver;
	
	private int _lastX;
    private int _lastY;
    private int _screenWidth;  
    private int _screenHeight;

	@Override
	public int loadContentView() {
		return R.layout.dialog_time;
	}

	@Override
	public void initView() {
		super.initView();
		DisplayMetrics dm = getResources().getDisplayMetrics();
		_screenWidth = dm.widthPixels;
		_screenHeight = dm.heightPixels;

		_zoomView = (ZoomView)this.findViewById(R.id.zoomview);

		_layout = (LinearLayout)this.findViewById(R.id.layout);
		_layout.setOnTouchListener(new OnTouchListener()
		{
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				int x = (int)event.getRawX();
				int y = (int)event.getRawY();

				switch (event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						_lastX = x;
						_lastY = y;
						break;

					case MotionEvent.ACTION_MOVE:
						float offX = (x - _lastX)/_zoomView.getZoom();
						float offY = (y - _lastY)/_zoomView.getZoom();

						float left = v.getLeft() + offX;
						float top = v.getTop() + offY;
						float right = v.getRight() + offX;
						float bottom = v.getBottom() + offY;

                      /*
                      if (left < 0)
                      {
                    	  left = 0;
                    	  right = left + v.getWidth();
                      }

                      if (top < 0)
                      {
                    	  top = 0;
                    	  bottom = top + v.getHeight();
                      }

                      if (right > _screenWidth)
                      {
                    	  right = _screenWidth;
                    	  left = right - v.getWidth();
                      }

                      if (bottom > _screenHeight)
                      {
                    	  bottom = _screenHeight;
                    	  top = bottom - v.getWidth();
                      }
                      */

						v.layout((int)left, (int)top, (int)right, (int)bottom);

						_lastX = x;
						_lastY = y;

						break;
				}

				return true;
			}
		});

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

		_okButton = (Button)this.findViewById(R.id.okButton);
		_okButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{

				Intent dialog = new Intent(TimeDialogActivity.this, CountDownDialogActivity.class);
				dialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				TimeDialogActivity.this.startActivity(dialog);
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

		Intent intent = getIntent();
		String strOpDate = intent.getStringExtra("OpDate");
		String strAnesDate = intent.getStringExtra("AnesDate");
		setOpDate(strOpDate);
		setAnesDate(strAnesDate);

		registerBroadCast();
		setTextTypeFace();
	}





	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		
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

	private void registerBroadCast()
	{
		broadcastReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context arg0, Intent arg1) 
			{
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
						//tvCurrentDay.setText(data);
					}
				}
			}
		};

		IntentFilter intentFilter = new IntentFilter("com.keruiyun.saike.order");
		intentFilter.addAction("com.keruiyun.saike.clock");
		this.registerReceiver(broadcastReceiver, intentFilter);
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
