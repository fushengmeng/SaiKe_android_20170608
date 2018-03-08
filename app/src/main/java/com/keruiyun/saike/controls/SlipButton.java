package com.keruiyun.saike.controls;

import com.keruiyun.saike.util.ImageUtil;
import com.keruiyun.saike.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SlipButton extends View implements OnTouchListener {

	private boolean NowChoose = false;
	private boolean OnSlip = false;
	private float DownX, NowX;
	private Rect Btn_On, Btn_Off;

	private boolean isChgLsnOn = false;
	private SlipButtonChangeListener ChgLsn;

	private Bitmap bg_on, bg_off, slip_btn;

	public SlipButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public SlipButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		bg_on = BitmapFactory
				.decodeResource(getResources(), R.drawable.slip_on);
		bg_on = ImageUtil.createBitmapBySize(bg_on, 120, 45);
		bg_off = BitmapFactory.decodeResource(getResources(),
				R.drawable.slip_off);
		bg_off = ImageUtil.createBitmapBySize(bg_off, 120, 45);
		slip_btn = BitmapFactory
				.decodeResource(getResources(), R.drawable.slip);
		slip_btn = ImageUtil.createBitmapBySize(slip_btn, 45, 45);
		
		Btn_On = new Rect(0, 0, slip_btn.getWidth(), slip_btn.getHeight());
		Btn_Off = new Rect(bg_off.getWidth() - slip_btn.getWidth(), 0,
				bg_off.getWidth(), slip_btn.getHeight());
		setOnTouchListener(this);

	}

	@Override
	protected void onDraw(Canvas canvas) {// 缁樺浘鍑芥暟
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		float x;

		{
			if (OnSlip) {
				if (NowX < (bg_on.getWidth() / 2))
					canvas.drawBitmap(bg_off, matrix, paint);
				else
					canvas.drawBitmap(bg_on, matrix, paint);

				if (NowX >= bg_on.getWidth())
					x = bg_on.getWidth() - slip_btn.getWidth() / 2;
				else
					x = NowX - slip_btn.getWidth() / 2;
			} else {
				if (NowChoose) {
					x = Btn_Off.left;
					canvas.drawBitmap(bg_on, matrix, paint);
				} else {
					x = Btn_On.left;
					canvas.drawBitmap(bg_off, matrix, paint);
				}
			}

			if (x < 0) {
				x = 0;
			} else if (x > bg_on.getWidth() - slip_btn.getWidth())
				x = bg_on.getWidth() - slip_btn.getWidth();
			canvas.drawBitmap(slip_btn, x, 0, paint);
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			NowX = event.getX();
			break;
		case MotionEvent.ACTION_DOWN:
			if (event.getX() > bg_on.getWidth()
					|| event.getY() > bg_on.getHeight())
				return false;
			OnSlip = true;
			DownX = event.getX();
			NowX = DownX;
			break;
		case MotionEvent.ACTION_UP:
			OnSlip = false;
			// Log.w("u Btn_Off.left:"+Btn_Off.left);
			// Log.w("u Btn_Off.right:"+Btn_Off.right);
			// Log.w("u bg_on.left:"+Btn_On.left);
			// Log.w("u bg_on.right:"+Btn_On.right);

			boolean LastChoose = NowChoose;
			SlipButton.this.measure(0, 0);
			int w = bg_on.getWidth();

			// int w =SlipButton.this.getMeasuredWidth();
			// Log.i("bg_on w锛� + w);
			// Log.e("u event.getX():" + event.getX());
			if (event.getX() >= (w / 2))
				NowChoose = true;
			else
				NowChoose = false;
			if (isChgLsnOn && (LastChoose != NowChoose))
				ChgLsn.OnChanged(NowChoose);
			break;
		case MotionEvent.ACTION_CANCEL:
			// NowX =event.getX();
			// Log.w("c Btn_Off.left:"+Btn_Off.left);
			// Log.w("c Btn_Off.right:"+Btn_Off.right);
			// Log.w("c bg_on.left:"+Btn_On.left);
			// Log.w("c bg_on.right:"+Btn_On.right);
			int m = (Btn_Off.right + Btn_On.left) / 2;

			// Log.e("middle x:"+m);
			OnSlip = false;
			LastChoose = NowChoose;
			SlipButton.this.measure(0, 0);
			w = bg_on.getWidth();
			// int w =SlipButton.this.getMeasuredWidth();
			// Log.i("cancel w锛� + w);
			// Log.e("c event.getX():" + event.getX());
			if (event.getX() >= (m))
				NowChoose = true;
			else
				NowChoose = false;
			if (isChgLsnOn && (LastChoose != NowChoose))
				ChgLsn.OnChanged(NowChoose);
			break;
		default:
		}
		invalidate();
		return true;
	}

	public void SetHistoryChosen(boolean HistoryChosen) {
		NowChoose = HistoryChosen;
		ChgLsn.OnChanged(NowChoose);
		invalidate();
	}

	public void SetOnChangedListener(SlipButtonChangeListener l) {
		isChgLsnOn = true;
		ChgLsn = l;
	}

	public interface SlipButtonChangeListener {
		abstract void OnChanged(boolean CheckState);
	}

}