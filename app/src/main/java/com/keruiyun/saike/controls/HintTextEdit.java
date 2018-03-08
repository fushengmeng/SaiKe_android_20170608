package com.keruiyun.saike.controls;

import com.keruiyun.saike.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

public class HintTextEdit extends EditText {
	private int mBkHintColor;
	private String mBkHintText;

	public HintTextEdit(Context context) {
		super(context);
		this.setBackgroundResource(android.R.color.transparent);
		// TODO Auto-generated constructor stub
	}

	public HintTextEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.hint_editText);
		mBkHintColor = a.getColor(R.styleable.hint_editText_bkHintColor,
				Color.BLACK);
		mBkHintText = a.getString(R.styleable.hint_editText_bkHintText);
		this.setBackgroundResource(android.R.color.transparent);
	}

	public HintTextEdit(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setBackgroundResource(android.R.color.transparent);
	}

	//���ñ���hint����
	public void setHintText(String value){
		mBkHintText = value;
	}
	
	//���ñ���hint��ɫ
	public void setHintColor(int value){
		mBkHintColor = value;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		String sValue = getText().toString();
		if (sValue != null && sValue.isEmpty() && mBkHintText != null
				&& !mBkHintText.isEmpty()) {
			Paint paint = new Paint();
			paint.setTextAlign(Align.LEFT);
			paint.setTextSize(this.getTextSize());
			paint.setColor(mBkHintColor);// Color.rgb(Color.red(0xc6c5c2),
			if (this.getGravity() == (Gravity.LEFT | Gravity.TOP)) {
				canvas.drawText(mBkHintText, 15, 30, paint);
			} else {
				//canvas.drawText(mBkHintText, 15, getHeight() / 2 + 8, paint);
				
				FontMetrics fontMetrics = paint.getFontMetrics(); 
				// �������ָ߶� 
				float fontHeight = fontMetrics.bottom - fontMetrics.top; 
				// ��������baseline 
				float textBaseY = getHeight() - (getHeight() - fontHeight) / 2 - fontMetrics.bottom; 

				canvas.drawText(mBkHintText, 15, textBaseY, paint);

			}
		}
		super.onDraw(canvas);
	}
}
