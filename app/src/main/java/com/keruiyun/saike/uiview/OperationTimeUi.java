package com.keruiyun.saike.uiview;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.AttributeSet;

import com.keruiyun.saike.MainActivity;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.PreferencesUtil;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/14.
 */

public class OperationTimeUi extends BaseProgressBar {

    private int opTimer;
    private Context context;

    public OperationTimeUi(Context context) {
        super(context);
    }

    public OperationTimeUi(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OperationTimeUi(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void initData() {
        super.initData();
        context=getContext();
        int count = PreferencesUtil.getInstance(getContext()).getIntValue("OperationTimer", "count",
                0);
        long time = PreferencesUtil.getInstance(getContext()).getLongValue("OperationTimer", "time",
                0);

        long tempTime = new Date().getTime();
        if (0 != time && -1 != time && tempTime >= time)
        {
            opTimer = (int)(count + (tempTime-time)/1000);
        }
        else
        {
            opTimer = count;

        }
        getTimeStr(opTimer, true);
        if (0 != time){
            if (-1 == time){
                opTimerPause();
            }else{

                opTimerStart();
            }
        }


    }



    @Override
    public String setCurTimerTime(int progress) {
        int count = PreferencesUtil.getInstance(getContext()).getIntValue("OperationTimer", "count",
                0);
        long time = PreferencesUtil.getInstance(getContext()).getLongValue("OperationTimer", "time",
                0);

        long tempTime = new Date().getTime();
        if (0 != time && -1 != time && tempTime >= time)
        {
            opTimer = (int)(count + (tempTime-time)/1000);
        }
        else
        {
            opTimer = count;

        }

        String msg=getTimeStr(opTimer);

        try
        {
           getContext().sendBroadcast(new Intent("com.keruiyun.saike.clock")
                    .putExtra("command", 1).putExtra("value", msg));
        }
        catch (Exception ex)
        {
        }
        return msg;
    }



    @Override
    public synchronized void start() {
        super.start();
        PreferencesUtil.getInstance(context).setIntValue("OperationTimer", "count",
                opTimer);
        PreferencesUtil.getInstance(context).setLongValue("OperationTimer", "time",
                new Date().getTime());
    }

    // 手术定时器开始
    public void opTimerStart(){
            start();
    }
    @Override
    public synchronized void stop() {
        super.stop();
        PreferencesUtil.getInstance(context).setIntValue("OperationTimer", "count",
                opTimer);
        PreferencesUtil.getInstance(context).setLongValue("OperationTimer", "time",
                -1);

    }


    // 手术定时器暂停
    public void opTimerPause() {
        stop();
    }

    // 手术定时器复位
    public void opTimerReset() {
        stop();
        resetOpData();
        postInvalidate();
    }

    private void resetOpData() {
        opTimer = 0;// 3 * 60 * 60 + 4 * 60 + 5;
        currRotate=startRotate;
        PreferencesUtil.getInstance(context).setIntValue("OperationTimer", "count",
                opTimer);
        PreferencesUtil.getInstance(context).setLongValue("OperationTimer", "time",
                0);

    }
}
