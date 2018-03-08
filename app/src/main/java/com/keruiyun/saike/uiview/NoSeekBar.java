package com.keruiyun.saike.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;
/**
* 
* @author jingshuixian
* 继承 SeekBar 实现自己的SeekBar
*/
public class NoSeekBar extends SeekBar {

        public NoSeekBar(Context context) {
                super(context);
                // TODO Auto-generated constructor stub
        }

        public NoSeekBar(Context context, AttributeSet attrs) {
                this(context, attrs, android.R.attr.seekBarStyle);
        }

        public NoSeekBar(Context context, AttributeSet attrs, int defStyle) {
                super(context, attrs, defStyle);
        }
        /**
         * onTouchEvent 是在 SeekBar 继承的抽象类 AbsSeekBar 里
         * 你可以看下他们的继承关系
         */
        @Override
        public boolean onTouchEvent(MotionEvent event) {
                // TODO Auto-generated method stub
                //原来是要将TouchEvent传递下去的,我们不让它传递下去就行了
                //return super.onTouchEvent(event);
                
                return false ;
        }

        
        
        

}