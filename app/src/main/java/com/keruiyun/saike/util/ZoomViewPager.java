package com.keruiyun.saike.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class ZoomViewPager extends ViewPager 
{
	public ZoomView zoomView;
	public boolean leftOrRight;
	
    public ZoomViewPager(Context context) 
    {
        super(context);
    }
    
    public ZoomViewPager(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
    	if (zoomView.pagerCanScroll(leftOrRight))
    	{
            return super.onTouchEvent(event);
        }
    	
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
    	if (zoomView.pagerCanScroll(leftOrRight)) 
    	{
            return super.onInterceptTouchEvent(event);
        }
    	
        return false;
    }

};