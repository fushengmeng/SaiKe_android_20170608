package com.keruiyun.saike.controls;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class BanSlitherViewPager extends ViewPager {

	public BanSlitherViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
    public BanSlitherViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
