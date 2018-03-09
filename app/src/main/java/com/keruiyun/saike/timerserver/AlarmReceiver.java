package com.keruiyun.saike.timerserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.setting.ViewHolderSmartstart;
import com.keruiyun.saike.util.LogCus;

/**
 * Created by Administrator on 2018/3/9.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isStart=intent.getBooleanExtra("isStart",false);
        int hour=intent.getIntExtra("hour",-1);
        int minute=intent.getIntExtra("minute",-1);
        LogCus.msg(""+intent.getAction()+":"+isStart+":---"+hour+":"+minute);
        if (hour==-1||minute==-1)
            return;

        if (isStart){
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_POWER_KEY, 1);
        }else {
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_POWER_KEY, 0);
        }
        ViewHolderSmartstart.setAlarm(context,isStart,hour+"",minute+"");

    }
}
