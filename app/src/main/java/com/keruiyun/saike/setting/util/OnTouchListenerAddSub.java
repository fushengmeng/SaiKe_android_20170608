package com.keruiyun.saike.setting.util;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.keruiyun.saike.util.LogCus;
import com.music.soundpool.SoundPlayer;

/**
 * Created by Administrator on 2018/1/26.
 */

public class OnTouchListenerAddSub implements View.OnTouchListener {



    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==2){
                mHandler.removeCallbacksAndMessages(null);
            }else{
                if (onTouchAddSub instanceof OnTouchAdd){
                    ((OnTouchAdd) onTouchAddSub).onAdd();
                }else {
                    ((OnTouchSub) onTouchAddSub).onSub();
                }
                mHandler.sendEmptyMessageDelayed(1,1000);
            }

        }
    };
    private OnTouchAddSub onTouchAddSub=null;
    public OnTouchListenerAddSub(OnTouchAddSub onTouchAddSub) {
        this.onTouchAddSub=onTouchAddSub;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        LogCus.msg("OnTouchListener:"+event.getAction());
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            mHandler.sendEmptyMessageDelayed(1,1000);
            SoundPlayer.getInstance().playerClick();
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            mHandler.sendEmptyMessage(2);
            SoundPlayer.getInstance().playerClick();
        }

        return false;
    }




}
