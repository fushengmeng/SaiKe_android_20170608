package com.keruiyun.saike.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.keruiyun.saike.AirActivity;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.FragmentAdapter;
import com.keruiyun.saike.R;
import com.keruiyun.saike.fragment.DialogFragment_Music;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements OnFragmentListener
{
	private ViewPager mPageVp;
	private FragmentAdapter mFragmentAdapter;
	private List<BaseFragment> mFragmentList=new ArrayList<>();
	private Fragment_Main fragment_main;
	private Fragment_Air fragment_air;



	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		MainApplication.getInstance()._activity = this;
		
		findById();
		init();

	}


	@Override
	public int loadContentView() {
		return R.layout.activity_home;
	}



	@Override
	public void setCurrentItem(int position) {
		switch (position){
			case 0://首页
				mPageVp.setCurrentItem(0,true);
				break;
			case 1://空调系统
				mPageVp.setCurrentItem(1,true);
				fragment_air.refreshRightBg();
//				fragment_main.setControlStatus(1);
				break;
			case -1://系统home
				backFinish();
				break;
		}
	}


	@Override
	public void backFinish() {
//        super.backFinish();
		Intent intent= new Intent(Intent.ACTION_MAIN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //如果是服务里调用，必须加入new task标识
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}




	private void findById()
	{
		mPageVp = (ViewPager)this.findViewById(R.id.id_page_vp);
	}

	private void init()
	{
		fragment_main = Fragment_Main.getInstance();
		fragment_air = Fragment_Air.getInstance();
		mFragmentList.add(fragment_main);
		mFragmentList.add(fragment_air);

		mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
		mPageVp.setAdapter(mFragmentAdapter);
		mPageVp.setCurrentItem(0);

		mPageVp.addOnPageChangeListener(new OnPageChangeListener()
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
	


	public class FragmentAdapter extends FragmentPagerAdapter{


		public FragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			return mFragmentList.get(position);
		}

		@Override
		public int getCount()
		{
			return mFragmentList.size();
		}



	}
}
