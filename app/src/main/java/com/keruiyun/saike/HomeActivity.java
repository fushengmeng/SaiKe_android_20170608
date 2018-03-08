package com.keruiyun.saike;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.keruiyun.saike.main.MainApplication;


public class HomeActivity extends BaseActivity 
{
	private ViewPager mPageVp;

	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private FragmentAdapter mFragmentAdapter;

	static public MainActivity _mainFragment;
	private AirActivity _airFragment;
	
	BroadcastReceiver _receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);


		MainApplication.getInstance()._activity = this;
		
		findById();
		init();
		
		registerBroadcast();
	}

	@Override
	public int loadContentView() {
		return R.layout.activity_home;
	}





	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		
		removeBroadcast();
	}



	private void findById()
	{
		mPageVp = (ViewPager)this.findViewById(R.id.id_page_vp);
	}

	private void init()
	{
		_mainFragment = new MainActivity();
		_airFragment = new AirActivity();
		mFragmentList.add(_mainFragment);
		mFragmentList.add(_airFragment);

		mFragmentAdapter = new FragmentAdapter(
				this.getSupportFragmentManager(), mFragmentList);
		mPageVp.setAdapter(mFragmentAdapter);
		mPageVp.setCurrentItem(0);

		mPageVp.setOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageScrollStateChanged(int state) 
			{

			}
			
			@Override
			public void onPageScrolled(int position, float offset, int offsetPixels) 
			{
			}

			@Override
			public void onPageSelected(int position)
			{
				try
				{
					if (0 == position)
					{
						//_airFragment.zoomView.zoomTo(1.0f, 0.0f, 0.0f);
					}
					else
					{
						//_mainFragment.zoomView.zoomTo(1.0f, 0.0f, 0.0f);
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}


	
	private void registerBroadcast() 
	{
		_receiver = new BroadcastReceiver() 
		{
			@Override
			public void onReceive(Context context, Intent intent)
			{
				String action = intent.getAction();
				if ("com.keruiyun.saike.gotohomepage".equals(action))
				{
					mPageVp.setCurrentItem(0, true);
				}
				else if ("com.keruiyun.saike.gotoairpage".equals(action))
				{
					mPageVp.setCurrentItem(1, true);
				}
			}
		};
		
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.keruiyun.saike.gotohomepage");
		filter.addAction("com.keruiyun.saike.gotoairpage");
		registerReceiver(_receiver, filter);
	}
	
	private void removeBroadcast() 
	{
		unregisterReceiver(_receiver);
	}
}
