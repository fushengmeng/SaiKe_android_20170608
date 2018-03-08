package com.keruiyun.saike.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class BorderScrollView extends ScrollView {
	private OnBorderListener onBorderListener;
	private View contentView;
	private boolean isIntercept = true, isHanlde = true;

	public BorderScrollView(Context context) {
		super(context);
	}

	public BorderScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BorderScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		doOnBorderListener();
	}

	public void setOnBorderListener(final OnBorderListener onBorderListener) {
		this.onBorderListener = onBorderListener;
		if (onBorderListener == null) {
			return;
		}

		if (contentView == null) {
			contentView = getChildAt(0);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (!isIntercept) {
			return isIntercept;
		}
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * OnBorderListener, Called when scroll to top or bottom
	 * 
	 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
	 *         2013-5-22
	 */
	public static interface OnBorderListener {

		/**
		 * Called when scroll to bottom
		 */
		public void onBottom();

		/**
		 * Called when scroll to top
		 */
		public void onTop();
	}

	private void doOnBorderListener() {
		if (null != contentView) {
			Log.e("BorderScrollViewMheight",
					String.valueOf(contentView.getMeasuredHeight()));
			Log.e("BorderScrollView",
					String.valueOf(getScrollY() + getHeight()));
		}
		if (contentView != null
				&& contentView.getMeasuredHeight() <= getScrollY()
						+ getHeight()) {
			Log.e("BorderScrollView", "onBottom");
			isIntercept = false;
			if (onBorderListener != null) {
				onBorderListener.onBottom();
			}
		} else if (getScrollY() == 0) {
			Log.e("BorderScrollView", "onTop");
			if (onBorderListener != null) {
				onBorderListener.onTop();
			}
		}
	}

	public void setHandleScroll(boolean isHanlde) {
		Log.e("BorderScrollView", "isHanlde:" + String.valueOf(isHanlde));
		this.isHanlde = isHanlde;
		isIntercept = !isHanlde;
	}
}
