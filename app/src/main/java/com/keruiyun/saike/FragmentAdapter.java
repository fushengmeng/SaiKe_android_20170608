package com.keruiyun.saike;

import java.util.ArrayList;
import java.util.List;

import com.keruiyun.saike.util.ZoomView;
import com.keruiyun.saike.util.ZoomViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;


public class FragmentAdapter extends FragmentPagerAdapter 
{
	List<Fragment> fragmentList = new ArrayList<Fragment>();
	
	public FragmentAdapter(FragmentManager fm,List<Fragment> fragmentList) 
	{
		super(fm);
		
		this.fragmentList = fragmentList;
	}

	@Override
	public Fragment getItem(int position)
	{
		return fragmentList.get(position);
	}

	@Override
	public int getCount() 
	{
		return fragmentList.size();
	}

	@Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) 
	{
        super.setPrimaryItem(container, position, object);

        if (object instanceof MainActivity)
        {
            //((ZoomViewPager)container).zoomView = ((F_Main)object).zoomView;
            //((ZoomViewPager)container).leftOrRight = false;
        }
        else if (object instanceof AirActivity)
        {
            //((ZoomViewPager)container).zoomView = ((DialogFragment_Air)object).zoomView;
            //((ZoomViewPager)container).leftOrRight = true;
        }
    }
	
}
