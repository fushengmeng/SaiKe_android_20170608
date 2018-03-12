package com.keruiyun.saike.timerserver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.PreferencesUtil;

public class ServiceFiveTimer extends BroadcastReceiver {

    public static final String ACTION_FiveTimer="com.keruiyun.saike.timerserver.ServiceFiveTimer";

    public static final String ACTION_FiveTimerLogout="com.keruiyun.saike.timerserver.Logout";

    public static final int TIME_logout=5;//分钟


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()!=null){
            if (intent.getAction().equals(ACTION_FiveTimer)){
                PreferencesUtil.getInstance(context).setBooleanValue("User", "login",false);
                context.sendBroadcast(new Intent(ACTION_FiveTimerLogout));
                LogCus.msg("账户登录计时：已到期");
            }
        }


    }


}
